package io.goose.web.controller.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.multipart.MultipartFile;

import io.goose.common.base.AjaxResult;
import io.goose.common.exception.ExcelUtilException;
import io.goose.common.utils.ExcelImpExpUtil;
import io.goose.common.utils.StringUtils;


public abstract class AExcelImportExportService {

   private Logger logger = LoggerFactory.getLogger( AExcelImportExportService.class );

   private static final int IDX_DATA_START = 1;

   private List<String> fieldNames = new ArrayList<>();


   public <T> AjaxResult importExcel( MultipartFile excelFile, Class<T> domainClass ) {

      try {

         Workbook workbook = WorkbookFactory.create( excelFile.getInputStream() );
         String msg = parseSheetToDomainObjectList( workbook.getSheetAt( 0 ), domainClass );
         return AjaxResult.success( msg );

      } catch ( Exception e ) {

         String err = "无法解析输入文件为excel表格文件, " + e.getMessage();
         logger.error( err );

         return AjaxResult.error( err );
      }

   }


   private <T> String parseSheetToDomainObjectList( Sheet sheet, Class<T> domainClass )
         throws ExcelUtilException {

      // for physical row num is sheetRowNum + 1
      int sheetRowNum = sheet.getPhysicalNumberOfRows();
      int totalImportedCount = 0;

      try {
         fieldNames = ExcelImpExpUtil.parseHeaderFieldNames( sheet.getRow( 0 ) );

         int idx = IDX_DATA_START;
         List<T> domainObjectList = new ArrayList<>();
         AjaxResult result = new AjaxResult();

         for ( idx = IDX_DATA_START; idx < sheetRowNum; idx++ ) {

            try {
               domainObjectList.add( parseRowToDomainObject( sheet.getRow( idx ), domainClass ) );

            } catch ( Exception e ) {
               String err = "无法转换行到域对象，行号: " + ( idx + 1 );
               result.put( Integer.toString( idx + 1 ), err );

               throw new ExcelUtilException( err + "   " + sheet.getRow( idx ).toString() );
            }

            // show processing log every 100 lines, until the end of the rows
            if ( domainObjectList.size() == 100 || idx == sheetRowNum - 1 ) {
               totalImportedCount += saveToDatabase( domainObjectList, idx + 1 );
               domainObjectList.clear();
            }
         }
      } catch ( ExcelUtilException e ) {
         String err = "表单头部(第1行)解析失败, " + e.getMessage() + ". 跳过处理表单, " + sheet.getSheetName();
         logger.error( e.getMessage() );
         throw new ExcelUtilException( err );
      }

      int totalCount = sheetRowNum - IDX_DATA_START;
      int totalIgnoredCount = totalCount - totalImportedCount;
      String msg = "导入完成： 应导入数据" + totalCount + "条，成功导入" + totalImportedCount + "条，忽略"
            + totalIgnoredCount + "条有坏数据的记录。";
      logger.info( msg );

      return msg;
   }


   private <T> T parseRowToDomainObject( Row row, Class<T> objClazz ) throws ExcelUtilException {

      Map<String, String> nameValue = new HashMap<>();
      int columnIndex = 0;

      for ( String fieldName : fieldNames ) {

         Cell cell = row.getCell( columnIndex++ );
         if ( cell == null || CellType.BLANK.equals( cell.getCellTypeEnum() ) ) {
            continue;
         }

         if ( handleSpecialCells( fieldName, cell, nameValue ) ) {
            continue;
         }

         String stringValue = ExcelImpExpUtil.parseCell( cell );

         if ( StringUtils.isNotBlank( stringValue ) ) {
            stringValue = stringValue.trim();
         }

         nameValue.put( fieldName, stringValue );
      }

      nameValue = transferNameValueToDoaminNameValue( nameValue );

      return ExcelImpExpUtil.readValue( objClazz, nameValue );
   }


   private <T> int saveToDatabase( List<T> objectListLoadFromExcel, int importedToExcelRow ) {

      int importedCount = 0;

      for ( Object objectLoadFromExcel : objectListLoadFromExcel ) {

         try {

            if ( saveDomainObject( objectLoadFromExcel ) ) {
               ++importedCount;
            }
         } catch ( CannotGetJdbcConnectionException e ) {
            throw e;
         } catch ( Exception e ) {
            // if there is an exception to handle the data, ignore it and move on to the next
            // one.
            logger.info( e.getMessage() );
            continue;
         }
      }

      int totalCount = objectListLoadFromExcel.size();
      int ignoredCount = totalCount - importedCount;
      String msg = "导入到第" + importedToExcelRow + "行。应导入数据" + totalCount + "条，成功导入" + importedCount
            + "条，忽略" + ignoredCount + "条有坏数据的记录。";
      logger.info( msg );

      return importedCount;
   }


   // return false directly if there is no special cells to handle
   protected abstract boolean handleSpecialCells( String fieldName, Cell cell,
         Map<String, String> nameValue );


   // map excel headers to domain fields
   protected abstract Map<String, String>
         transferNameValueToDoaminNameValue( Map<String, String> nameValue );


   protected abstract boolean saveDomainObject( Object domainObject ) throws Exception;
}
