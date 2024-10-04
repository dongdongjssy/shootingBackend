package io.goose.web.controller.common;

import com.google.common.base.Stopwatch;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * 功能说明：导出模板(待重构)
 * 典型用法：无
 * 特殊用法：无
 * 创建者：phil
 * 创建时间： 2017年10月13日
 * 修改人：phil
 * 修改时间：2017年10月18日
 * 修改原因： 升级poi3.17 重写 修改内容： 版本号：2.0
 */
public class ExportExcel {
    private static final Logger log = LoggerFactory.getLogger(ExportExcel.class);


    /**
     * 导出 xlsx格式Excel XSSF
     *
     * @param title 标题
     * @param headers 列名
     * @param dataset 数据集
     * @param out     输出流
     * @param pattern 日期模式
     */
    public void exportXSExcelByColumn(String title,
                                      String[] headers,
                                      String[] columns,
                                      Collection<Map<String, Object>> dataset,
                                      OutputStream out,
                                      String pattern) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        Workbook workbook = new SXSSFWorkbook();

        // 生成一个表格
        Sheet sheet = createSheet(title, workbook);
        CellStyle headerStyle = createStyle(workbook, IndexedColors.GREY_25_PERCENT);

        Font headerFont = createFont(workbook);
        // 把字体应用到当前的样式
        headerStyle.setFont(headerFont);

        // 生成并设置另一个样式 内容的背景
        CellStyle cellStyle = createStyle(workbook, IndexedColors.WHITE);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 生成另一个字体
        Font rowFont = workbook.createFont();
        rowFont.setBold(true);
        // 把字体应用到当前的样式
        cellStyle.setFont(rowFont);


        // 声明一个画图的顶级管理器
        Drawing<?> patriarch = createDrawing(sheet);

        // 产生表格标题行
        drawHeaders(headers, headerStyle, sheet);

        FastDateFormat dateFormat = createDateFormat(pattern);

        // 遍历集合数据，产生数据行
        Iterator<Map<String, Object>> it = dataset.iterator(); // 多个Map集合
        int index = 0;

        try {
            while (it.hasNext()) {
                Row row = sheet.createRow(++index);
                Map<String, Object> map = it.next();
                IntStream.range(0, headers.length)
                        .forEach(colIdx -> createCell(workbook, sheet, cellStyle, patriarch, dateFormat, row, map.get(columns[colIdx]), colIdx));
            }
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            log.error("导出excel异常:", e);
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(out);
            log.debug("导出执行 {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
            stopwatch.stop();
        }
    }

    private FastDateFormat createDateFormat(String pattern) {
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy/MM/dd";
        }
        return FastDateFormat.getInstance(pattern);
    }

    private void createCell(
            Workbook workbook,
            Sheet sheet,
            CellStyle cellStyle,
            Drawing<?> patriarch,
            FastDateFormat dataFormat,
            Row row,
            Object columnValue,
            int columnIndex) {
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        // 判断值的类型后进行强制类型转换
        Optional.ofNullable(columnValue)
                .ifPresent(v -> {
                    String textValue = "";
                    // 设置日期
                    if (columnValue instanceof Date) {
                        Date date = (Date) columnValue;
                        textValue = dataFormat.format(date);
                    }

                    if (columnValue instanceof byte[]) {
                        // 设置图片
                        createImage(workbook, sheet, patriarch, row, (byte[]) columnValue, columnIndex);
                    } else {
                        // 当字符串处理
                        textValue = columnValue.toString();
                    }
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {
                        // 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else {
                        RichTextString richString = new XSSFRichTextString(textValue);
                        Font font3 = workbook.createFont();
                        font3.setColor(IndexedColors.BLACK.index); // 内容
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                });
    }

    private void createImage(Workbook workbook, Sheet sheet, Drawing<?> patriarch, Row row, byte[] columnValue, int columnIndex) {
        row.setHeightInPoints(60);
        // 设置图片所在列宽度为80px,注意这里单位的一个换算
        sheet.setColumnWidth(columnIndex, (short) (35.7 * 80));
        int rowIndex = row.getRowNum();
        ClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short) 6, rowIndex, (short) 6, rowIndex);
        anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
        patriarch.createPicture(anchor, workbook.addPicture(columnValue, Workbook.PICTURE_TYPE_JPEG));
    }

    private void drawHeaders(String[] headers, CellStyle style, Sheet sheet) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            RichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
    }

    @NotNull
    private Drawing<?> createDrawing(Sheet sheet) {
        Drawing<?> patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置
        Comment comment = patriarch.createCellComment(new XSSFClientAnchor(0, 0, 0,
                0, (short) 4, 2, (short) 6, 5));
        //设置注释内容
        comment.setString(new XSSFRichTextString("Created By Phil"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("phil");
        return patriarch;
    }

    @NotNull
    private Sheet createSheet(String title, Workbook workbook) {
        Sheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth(20);
        sheet.setDefaultRowHeightInPoints(24);
        return sheet;
    }

    private CellStyle createStyle(Workbook workbook, IndexedColors grey25Percent) {
        // 生成一个 表格标题行样式
        CellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(grey25Percent.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }

    @NotNull
    private Font createFont(Workbook workbook) {
        // 生成一个字体
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);
        return font;
    }
}