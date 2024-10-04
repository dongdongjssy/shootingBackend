package io.goose.web.controller.tool;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.goose.common.annotation.Log;
import io.goose.common.enums.BusinessType;
import io.goose.common.page.TableDataInfo;
import io.goose.common.support.Convert;
import io.goose.framework.web.base.BaseController;
import io.goose.generator.domain.ColumnConfigInfo;
import io.goose.generator.domain.TableInfo;
import io.goose.generator.service.IGenService;

/**
 * 代码生成 操作处理
 * 
 * @author goose
 */
@Controller
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(GenController.class);

    private String prefix = "tool/gen";

    @Autowired
    private IGenService genService;

    @RequiresPermissions("tool:gen:view")
    @GetMapping()
    public String gen()
    {
        return prefix + "/gen";
    }

    @RequiresPermissions("tool:gen:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TableInfo tableInfo)
    {
        startPage();
        List<TableInfo> list = genService.selectTableList(tableInfo);
        return getDataTable(list);
    }
    
    @RequiresPermissions("tool:gen:code")
	@GetMapping("/edit/{tableName}")
	public String edit(@PathVariable("tableName") String tableName, ModelMap mmap)
	{
		TableInfo table = genService.selectTable(tableName);
		mmap.put("table", table);
		mmap.put("tableName", tableName);
	    return prefix + "/edit";
	} 
    
    /**
     * 生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @PostMapping("/genCode/{tableName}")
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName, 
    		@RequestBody List<ColumnConfigInfo> columnOptions) throws IOException
    {
    	File tmp = File.createTempFile(tableName, ".zip");    	
        byte[] data = genService.generatorCode(tableName, columnOptions);
        FileUtils.writeByteArrayToFile(tmp, data);
        response.setContentType("application/octet-stream; charset=UTF-8");    
        
        
        IOUtils.write(tmp.getName().getBytes(), response.getOutputStream());
    }    
    
    /**
      * 下载代码文件
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/download/{fileName}")
    public void genCode(HttpServletResponse response, @PathVariable("fileName") String fileName) throws IOException
    {
    	String tmpdir = System.getProperty("java.io.tmpdir");
    	String filePath = tmpdir+fileName;
    	File tmp = new File(filePath);
		byte[] data = Files.readAllBytes(tmp.toPath());
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
        tmp.delete();
    }

    /**
     * 生成代码
     */
/*    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/genCode/{tableName}")
    @ResponseBody
    public void genCode(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException
    {
        byte[] data = genService.generatorCode(tableName);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"goose.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }*/

    /**
     * 批量生成代码
     */
    @RequiresPermissions("tool:gen:code")
    @Log(title = "代码生成", businessType = BusinessType.GENCODE)
    @GetMapping("/batchGenCode")
    @ResponseBody
    public void batchGenCode(HttpServletResponse response, String tables) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        byte[] data = genService.generatorCode(tableNames);
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"goose.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
    }
}
