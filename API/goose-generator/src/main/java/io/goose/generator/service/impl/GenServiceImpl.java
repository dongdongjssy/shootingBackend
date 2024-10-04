package io.goose.generator.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.config.Global;
import io.goose.common.constant.Constants;
import io.goose.common.utils.StringUtils;
import io.goose.generator.domain.ColumnInfo;
import io.goose.generator.domain.ForeignKeyInfo;
import io.goose.generator.domain.ColumnConfigInfo;
import io.goose.generator.domain.TableInfo;
import io.goose.generator.mapper.GenMapper;
import io.goose.generator.service.IGenService;
import io.goose.generator.util.GenUtils;
import io.goose.generator.util.VelocityInitializer;

/**
 * 代码生成 服务层处理
 * 
 * @author goose
 */
@Service
public class GenServiceImpl implements IGenService
{
    private static final Logger log = LoggerFactory.getLogger(GenServiceImpl.class);

    @Autowired
    private GenMapper genMapper;
    
    @Autowired
    private GenUtils genUtils;
    
    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 查询数据库表信息
     * 
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    @Override
    public List<TableInfo> selectTableList(TableInfo tableInfo)
    {
        return genMapper.selectTableList(tableInfo);
    }
    
    private List<ForeignKeyInfo> loadForeignKeyInfo(String tableName, List<ColumnInfo> columns) {
    	
        // 查询表定义外键
        List<ForeignKeyInfo> tableDefinedFkColumns = genMapper.selectForeignKeyByName(tableName);        
        for(ForeignKeyInfo fk : tableDefinedFkColumns ) {
    		List<ColumnInfo> fkColumns = genMapper.selectTableColumnsByName(fk.getReferencedTableName());
    		fk.setReferencedTableColumns(fkColumns);

        	for(ColumnInfo c: columns) {
        		if(fk.getColumnName().equals(c.getColumnName())) {
        			c.setRefColumnName(fk.getReferencedColumnName());
        			c.setRefTableName(fk.getReferencedTableName());
        			String refClassName = genUtils.tableToJava(fk.getReferencedTableName());
        			c.setRefClassName(refClassName);
        			c.setRefClassname(StringUtils.uncapitalize(refClassName));   
        			String attrName = StringUtils.convertToCamelCase(c.getRefColumnName());
        			c.setRefAttrname(StringUtils.uncapitalize(attrName));
        			attrName = StringUtils.convertToCamelCase(c.getConfigInfo().getFkDisplayColumn());
        			c.setRefDisplayAttrname(StringUtils.uncapitalize(attrName));
        		}
        	}
        }    

        // 查询注释定义外键
        List<ForeignKeyInfo> commentDefinedFkColumns = new ArrayList<ForeignKeyInfo>();
        for(ColumnInfo column : columns) {
        	ColumnConfigInfo config = column.getConfigInfo();
        	if(config != null && config.getFkTable() != null && config.getFkColumn() != null) {
        		//表定义外键已经存在
        		ForeignKeyInfo result = tableDefinedFkColumns.stream().filter(fk->fk.getReferencedTableName().equals(config.getFkTable()) &&
						fk.getReferencedColumnName().equals(config.getFkColumn())).findAny().orElse(null);
        		if(result != null) {
        			log.debug("Table {} column {} is defined with foreign key on reference table {} column {}. Foreign key defined in comment on same column will be ignored",
        					tableName, column.getColumnName(), config.getFkTable(), config.getFkColumn());
        			continue;
        		}

        		//表定义外键重复
        		result = commentDefinedFkColumns.stream().filter(fk->fk.getReferencedTableName().equals(config.getFkTable()) &&
						fk.getReferencedColumnName().equals(config.getFkColumn())).findAny().orElse(null);
        		if(result != null) {
        			log.error("Duplicate fkTable {}  or fkColumn {}  configuration in table {} comment foreign key define, later ones will be ignored", 
        					config.getFkTable(), config.getFkColumn(), tableName);
        			continue;
        		}  
        		
        		//表定义外键不存在,继续处理注释定义外键    			
        		List<ColumnInfo> fkColumns = genMapper.selectTableColumnsByName(config.getFkTable());
        		//表定义外键里的表或字段不存在，重置配置选项
        		if(fkColumns.isEmpty() || !fkColumns.stream().anyMatch(c->c.getColumnName().equals(config.getFkColumn()))) {   
        			log.error("Discard incorrect fkTable {}  or fkColumn {}  configuration in table {}  comment foreign key define", 
        					config.getFkTable(), config.getFkColumn(), tableName);
        			config.setFkCreateAssoc((short)0);
        			config.setFkTable("");
        			config.setFkColumn("");
        		}
        		else {
        			ForeignKeyInfo fk = new ForeignKeyInfo();
        			fk.setTableName(tableName);
        			fk.setColumnName(column.getColumnName());
        			fk.setReferencedTableName(config.getFkTable());
        			fk.setReferencedColumnName(config.getFkColumn());
        			fk.setReferencedTableColumns(fkColumns);
        			commentDefinedFkColumns.add(fk);
        			
        			column.setRefColumnName(fk.getReferencedColumnName());
        			column.setRefTableName(fk.getReferencedTableName());
        			String refClassName = genUtils.tableToJava(fk.getReferencedTableName());
        			column.setRefClassName(refClassName);
        			column.setRefClassname(StringUtils.uncapitalize(refClassName));
        			String attrName = StringUtils.convertToCamelCase(column.getRefColumnName());
        			column.setRefAttrname(StringUtils.uncapitalize(attrName));
        			attrName = StringUtils.convertToCamelCase(column.getConfigInfo().getFkDisplayColumn());
        			column.setRefDisplayAttrname(StringUtils.uncapitalize(attrName));
        		}
        	}
        } 
  
		List<ForeignKeyInfo> tmpFkColumns = new ArrayList<ForeignKeyInfo>(tableDefinedFkColumns);
		tmpFkColumns.addAll(commentDefinedFkColumns);
        List<ForeignKeyInfo> foreignKeyColumns = new ArrayList<ForeignKeyInfo>();
        //查询同一个外表上有多个字段的外键的配置，并删除
        for(ForeignKeyInfo fk : tmpFkColumns ) {
        	if(tmpFkColumns.stream().filter(c->c.getReferencedTableName().equals(fk.getReferencedTableName())).count() > 1) {
        		log.error("Only support one foreign key on each foreign table, but multiple keys found for table {}  on table {} column {} . Remove all.", tableName,
        				fk.getReferencedTableName(), fk.getColumnName());
        	}
        	else {
        		foreignKeyColumns.add(fk);
        	}
        }
        

        return foreignKeyColumns;        
    }

    @Override
    public TableInfo selectTable(String tableName) {
        // 查询表信息
        TableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
        
        columns.stream().forEach(c->{
        	if(c.getColumnName().equalsIgnoreCase("client_user_id")) {
        		ColumnConfigInfo config = c.getConfigInfo();
        		if(config == null)
        			config = new ColumnConfigInfo();
        		config.setFkCreateAssoc((short) 1);
        		config.setFkTable("CLIENT_USER");
        		config.setFkColumn("id");
        		config.setFkDisplayColumn("user_name");
        		config.setFkInputMethod("select");
        		c.setConfigInfo(config);
        	}
        	else if(c.getColumnName().equalsIgnoreCase("sys_user_id")) {
        		ColumnConfigInfo config = c.getConfigInfo();
        		if(config == null)
        			config = new ColumnConfigInfo();
        		config.setFkCreateAssoc((short) 1);
        		config.setFkTable("SYS_USER");
        		config.setFkColumn("user_id");
        		config.setFkDisplayColumn("user_name");
        		config.setFkInputMethod("select");
        		c.setConfigInfo(config);
        	}
        });
        
        //查询外键配置
        List<ForeignKeyInfo> foreignKeyColumns = loadForeignKeyInfo(tableName, columns);        		

        String className = genUtils.tableToJava(table);
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));
        // 列信息
        table.setColumns(genUtils.transColums(columns));
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());
        // 设置外键
        table.setForeignKeyColumns(genUtils.transFkColumns(foreignKeyColumns));
                
        return table;
    }

    @Override
    public byte[] generatorCode(String tableName, List<ColumnConfigInfo> columnOptions) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        // 查询表信息
        TableInfo table = genMapper.selectTableByName(tableName);
        // 查询列信息
        List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);
      
        // 设置配置信息
    	for(ColumnInfo c: columns) {
    		for(ColumnConfigInfo opt : columnOptions ) {    			
    			if(opt.getColumnName().equals(c.getColumnName())) {
    				if(c.getConfigInfo() != null) { 
    					//表注释定义的字段，不支持界面修改
    					opt.setTitle(c.getConfigInfo().getTitle());
    					opt.setValue(c.getConfigInfo().getValue());
    					opt.setType(c.getConfigInfo().getType());
    				}
    				c.setConfigInfo(opt);
        		}
            } 
    		//avoid null check 
    		if(c.getConfigInfo() == null) {
    			c.setConfigInfo(new ColumnConfigInfo());
    		}
    	}

        //查询外键配置
        List<ForeignKeyInfo> allForeignKeyColumns = loadForeignKeyInfo(tableName, columns); 
        List<ForeignKeyInfo> foreignKeyColumns = new ArrayList<ForeignKeyInfo>();
        int i = 0;
    	for(ColumnInfo c: columns) {
    		if(c.getConfigInfo().getFkCreateAssoc() == 1) {
	    		for(ForeignKeyInfo k : allForeignKeyColumns) {
	    			if(k.getColumnName().equals(c.getColumnName())) {
	    	        	List<ColumnInfo> refColumns = genMapper.selectTableColumnsByName(k.getReferencedTableName());
	    	        	k.setReferencedTableColumns(genUtils.transColums(refColumns));
	    	        	k.setAlias(Character.toString(alphabet[i++]));
	    				foreignKeyColumns.add(k);
	    			}
	    		}
    		}
    	}
        
    	// 生成代码  
    	//TODO:no support any longer
        //generatorCode(table, columns, foreignKeyColumns, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();    	
    }


   
    /**
     * 批量生成代码
     * 
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] generatorCode(String[] tableNames)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        StringWriter sqlSw = new StringWriter();
        for (String tableName : tableNames)
        {
            // 查询表信息
            TableInfo table = genMapper.selectTableByName(tableName);
            // 查询列信息
            List<ColumnInfo> columns = genMapper.selectTableColumnsByName(tableName);  
        	for(ColumnInfo c: columns) {        		
        		if(c.getConfigInfo() == null) {
        			c.setConfigInfo(new ColumnConfigInfo());
        		}
        	}
            columns.stream().forEach(c->{
            	if(c.getColumnName().equalsIgnoreCase("client_user_id")) {
            		ColumnConfigInfo config = c.getConfigInfo();
            		config.setFkCreateAssoc((short) 1);
            		config.setFkTable("CLIENT_USER");
            		config.setFkColumn("id");
            		config.setFkDisplayColumn("user_name");
            		config.setFkInputMethod("select");
            		//config.setSystemField((short) 1);
            		c.setConfigInfo(config);
            	}
            	else if(c.getColumnName().equalsIgnoreCase("sys_user_id")) {
            		ColumnConfigInfo config = c.getConfigInfo();
            		config.setFkCreateAssoc((short) 1);
            		config.setFkTable("SYS_USER");
            		config.setFkColumn("user_id");
            		config.setFkDisplayColumn("user_name");
            		config.setFkInputMethod("select");
            		c.setConfigInfo(config);
            	}
            });
                    	
            
            //查询外键配置
            List<ForeignKeyInfo> allForeignKeyColumns = loadForeignKeyInfo(tableName, columns); 
            List<ForeignKeyInfo> foreignKeyColumns = new ArrayList<ForeignKeyInfo>();
            int i = 0;
        	for(ColumnInfo c: columns) {
        		if(c.getConfigInfo().getFkCreateAssoc() == 1) {
    	    		for(ForeignKeyInfo k : allForeignKeyColumns) {
    	    			if(k.getColumnName().equals(c.getColumnName())) {
    	    	        	List<ColumnInfo> refColumns = genMapper.selectTableColumnsByName(k.getReferencedTableName());
    	    	        	k.setReferencedTableColumns(genUtils.transColums(refColumns));
    	    	        	k.setAlias(Character.toString(alphabet[i++]));
    	    	        	if(c.getColumnName().equalsIgnoreCase("sys_user_id")) {
        	            		k.setAttrType("User");
        	            		k.setAttrname("user");
        	            		k.setAttrName("User");
        	            		k.setOverride(1);

        	            	}    	    			    	    	        	
    	    				foreignKeyColumns.add(k);
    	    			}
    	            	
    	    		}
        		}
        	}          
        	
            // 生成代码
            generatorCode(table, columns, foreignKeyColumns, zip, sqlSw);
        }

        try
        {
            // 添加到zip
            zip.putNextEntry(new ZipEntry("Init.sql"));
            IOUtils.write(sqlSw.toString(), zip, Constants.UTF8);
            IOUtils.closeQuietly(sqlSw);
            zip.closeEntry();
        }
        catch (IOException e)
        {
            log.error("渲染sql模板失败", e);
        }
                
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码
     * @param sqlSw 
     */
    public void generatorCode(TableInfo table, List<ColumnInfo> columns, List<ForeignKeyInfo> foreignKeyColumns, ZipOutputStream zip, StringWriter sqlSw)
    {
    	 // 列信息
        table.setColumns(genUtils.transColums(columns));
        // 表名转换成Java属性名
        String className = genUtils.tableToJava(table);
        table.setClassName(className);
        table.setClassname(StringUtils.uncapitalize(className));
       
        // 设置主键
        table.setPrimaryKey(table.getColumnsLast());
        // 设置外键
        table.setForeignKeyColumns(genUtils.transFkColumns(foreignKeyColumns));

        VelocityInitializer.initVelocity();

        //String packageName = Global.getPackageName();
        String packageName = genUtils.getPackageName(table);
        String moduleName = genUtils.getModuleName(packageName);

        VelocityContext context = genUtils.getVelocityContext(table);

        // 获取模板列表
        List<String> templates = genUtils.getTemplates();
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(genUtils.getFileName(template, table, moduleName)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
            if(template.equals("vm/sql/sql.vm")) {
                Template sqlTpl = Velocity.getTemplate(template, Constants.UTF8);
                sqlTpl.merge(context, sqlSw);
            }
        }
    }
    
    
}
