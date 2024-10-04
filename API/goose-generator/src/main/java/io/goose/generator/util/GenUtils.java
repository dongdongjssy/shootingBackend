package io.goose.generator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.goose.common.config.Global;
import io.goose.common.constant.Constants;
import io.goose.common.utils.DateUtils;
import io.goose.common.utils.StringUtils;
import io.goose.generator.domain.ColumnConfigInfo;
import io.goose.generator.domain.ColumnInfo;
import io.goose.generator.domain.ForeignKeyInfo;
import io.goose.generator.domain.TableInfo;

/**
 * 代码生成器 工具类
 * 
 * @author goose
 */
@Component
public class GenUtils
{
    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** html空间路径 */
    private static final String TEMPLATES_PATH = "main/resources/templates";

    /** 类型转换 */
    public static Map<String, String> javaTypeMap = new HashMap<String, String>();
    
    @Value("${gen.author:goose}")
    String author;
    
    @Value("${gen.packageName:io.goose}")
	private String packageName;
    
    @Value("${gen.commonPackage:io.goose}")
	private String commonPackage;    
    
    @Value("${gen.autoRemovePre:true}")
	private String autoRemovePre;
    
    @Value("${gen.tablePrefix:sys_}")
	private String tablePrefix;
    
    @Value("${gen.restPackageName:io.goose.api}")
	private String restPackageName;    
    
    @Value("${gen.adminOwnerColumn:city_id}")
	private String adminOwnerColumn;
    
    @Value("${gen.adminOwnerTable:loc_city}")
	private String adminOwnerTable;    
    
    @Value("${gen.adminOwnerBaseClass:CityBaseController}")
	private String adminOwnerBaseClass;
    
    @Value("${gen.adminOwnerRestBaseClass:CityRestBaseController}")
	private String adminOwnerRestBaseClass;
    
    

    /**
     * 设置列信息
     */
    public List<ColumnInfo> transColums(List<ColumnInfo> columns)
    {
        // 列信息
        List<ColumnInfo> columsList = new ArrayList<>();
        for (ColumnInfo column : columns)
        {
            // 列名转换成Java属性名
            String attrName = StringUtils.convertToCamelCase(column.getColumnName());
            column.setAttrName(attrName);
            column.setAttrname(StringUtils.uncapitalize(attrName));

            // 列的数据类型，转换成Java类型
            String attrType = javaTypeMap.get(column.getDataType());
            if(column.getAttrname().equals("userId")) {
            	attrType = "Long";
            }
            column.setAttrType(attrType);

            columsList.add(column);
        }
        return columsList;
    }
    

    public List<ForeignKeyInfo> transFkColumns(List<ForeignKeyInfo> columns)
    {
        // 列信息
        List<ForeignKeyInfo> columsList = new ArrayList<>();
        for (ForeignKeyInfo column : columns)
        {
        	if(column.getOverride() == 0) {
	            // 列的数据类型，转换成java Domain类型
	            String attrType = StringUtils.convertToCamelCase(tableToJava(column.getReferencedTableName()));
	            column.setAttrType(attrType);
	            
	            // 列名转换成Java属性名
	            String attrName = StringUtils.convertToCamelCase(attrType);
	            column.setAttrName(attrName);
	            column.setAttrname(StringUtils.uncapitalize(attrName));
        	}

            columsList.add(column);
        }
        return columsList;
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public VelocityContext getVelocityContext(TableInfo table)
    {
        // java对象数据传递到模板文件vm
        VelocityContext velocityContext = new VelocityContext();
        //String packageName = Global.getPackageName();
        String packageName = getPackageName(table);
        velocityContext.put("tableName", table.getTableName());
        velocityContext.put("tableComment", replaceKeyword(table.getTableComment()));
        velocityContext.put("primaryKey", table.getPrimaryKey());
        velocityContext.put("className", table.getClassName());
        velocityContext.put("classname", table.getClassname());
        velocityContext.put("moduleName", getModuleName(packageName));
        velocityContext.put("columns", table.getColumns());
        velocityContext.put("basePackage", getBasePackage(table));
        velocityContext.put("commonPackage", this.commonPackage);
        velocityContext.put("package", packageName);
        velocityContext.put("author", author);
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("foreignKeyColumns", table.getForeignKeyColumns());
        for(ColumnInfo c:table.getColumns()) {
        	ColumnConfigInfo config = c.getConfigInfo();
        	
        	if(config.getUploadPic() > 0) {
        		velocityContext.put("uploadPic", 1);
        		 velocityContext.put("ModuleName", StringUtils.capitalize(getModuleName(packageName)));
        	}
        	if(config.getRichEditor() > 0) {
        		velocityContext.put("richEditor", 1); 
        	}
        	if(!table.getTableName().equalsIgnoreCase(adminOwnerTable)) {
        		if(c.getColumnName().equalsIgnoreCase(adminOwnerColumn)) {
        			String adminOwnerId = StringUtils.uncapitalize(StringUtils.convertToCamelCase(adminOwnerColumn));
	        		velocityContext.put("adminOwner", 1);
	        		velocityContext.put("adminOwnerId", adminOwnerId);
	        		velocityContext.put("adminOwnerBaseClass", adminOwnerBaseClass);
	        		velocityContext.put("adminOwnerRestBaseClass", adminOwnerRestBaseClass);
	        		//velocityContext.put("adminOwnerList", ADMIN_OWNER_ID);
	        		velocityContext.put("adminOwnerSetMethod", "set"+StringUtils.capitalize(adminOwnerId));
	        		velocityContext.put("adminOwnerGetMethod", "get"+StringUtils.capitalize(adminOwnerId));
	        		velocityContext.put("adminOwnerGetListMethod", "get"+StringUtils.capitalize(adminOwnerId)+"list");
	        	}
        	}
        	if(config.getCachable() == 1) {
        		velocityContext.put("cachable", 1);
        	}
//        	if(config.getChild() != null && config.getChild().size() > 0) {
//        		LinkedHashMap<String, String> override = new LinkedHashMap<String, String>();
//				config.getChild().forEach((k,v)->{
//					k = tableToJava(k);
//					String childClassName = StringUtils.capitalize(StringUtils.convertToCamelCase(k));
//					String childFieldname = StringUtils.uncapitalize(StringUtils.convertToCamelCase(v));
//					override.put(childClassName, childFieldname);
//				});
//				config.setChild(override);
//        	}
        }        

        return velocityContext;
    }

    /**
     * 获取模板信息
     * 
     * @return 模板列表
     */
    public List<String> getTemplates()
    {
        List<String> templates = new ArrayList<String>();
        templates.add("vm/java/domain.java.vm");
        templates.add("vm/java/Mapper.java.vm");
        templates.add("vm/java/Service.java.vm");
        templates.add("vm/java/ServiceImpl.java.vm");
        templates.add("vm/java/Controller.java.vm");
        templates.add("vm/java/RestController.java.vm");
        templates.add("vm/xml/Mapper.xml.vm");
        templates.add("vm/html/list.html.vm");
        templates.add("vm/html/add.html.vm");
        templates.add("vm/html/edit.html.vm");
        templates.add("vm/sql/sql.vm");
        return templates;
    }

    /**
     * 表名转换成Java类名
     */
    public String tableToJava(TableInfo table)
    {
    	String tableName = table.getTableName();
    	
    	Optional<ColumnInfo> pkgNmOverride = table.getColumns().stream().filter(c->c != null && StringUtils.isNotBlank(c.getConfigInfo().getClassName())).findFirst();
    	if(pkgNmOverride.isPresent())
    		tableName = pkgNmOverride.get().getConfigInfo().getClassName();
    	
       return tableToJava(tableName);
    }
    
    public String tableToJava(String tableName)
    {
    	if(!tableName.equalsIgnoreCase("client_user") && !tableName.equalsIgnoreCase("sys_user"))
    	{
	        if (Constants.AUTO_REOMVE_PRE.equals(this.autoRemovePre))
	        {
	            tableName = tableName.substring(tableName.indexOf("_") + 1);
	        }
	        if (StringUtils.isNotEmpty(this.tablePrefix))
	        {
	            tableName = tableName.replace(this.tablePrefix, "");
	        }
    	}
        return StringUtils.convertToCamelCase(tableName);
    }    

    /**
     * 获取文件名
     */
    public String getFileName(String template, TableInfo table, String moduleName)
    {
        // 小写类名
        String classname = table.getClassname();
        // 大写类名
        String className = table.getClassName();
        String javaPath = getProjectPath(table)+ moduleName+ "/";
        String mybatisPath = MYBATIS_PATH + "/" + moduleName + "/" + className;
        String htmlPath = TEMPLATES_PATH + "/" + moduleName + "/" + classname;

        if (template.contains("domain.java.vm"))
        {
            return javaPath + "domain" + "/" + className + ".java";
        }

        if (template.contains("Mapper.java.vm"))
        {
            return javaPath + "mapper" + "/" + className + "Mapper.java";
        }

        if (template.contains("Service.java.vm"))
        {
            return javaPath + "service" + "/" + "I" + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm"))
        {
            return javaPath + "service" + "/impl/" + className + "ServiceImpl.java";
        }

        if (template.contains("/Controller.java.vm"))
        {
            return getProjectPath(table) + "web/controller/" +moduleName+ "/" + className + "Controller.java";
        }
        
        if (template.contains("/RestController.java.vm"))
        {
            return getProjectPath(table) + "api/controller/" +moduleName+ "/" + className + "RestController.java";
        }

        if (template.contains("Mapper.xml.vm"))
        {
            return mybatisPath + "Mapper.xml";
        }

        if (template.contains("list.html.vm"))
        {
            return htmlPath + "/" + classname + ".html";
        }
        if (template.contains("add.html.vm"))
        {
            return htmlPath + "/" + "add.html";
        }
        if (template.contains("edit.html.vm"))
        {
            return htmlPath + "/" + "edit.html";
        }
        if (template.contains("sql.vm"))
        {
            return classname + "Init.sql";
        	//return "init.sql";
        }
        return null;
    }
    
    public String getPackageName(TableInfo table) {
    	Optional<ColumnInfo> pkgNmOverride = table.getColumns().stream().filter(c->StringUtils.isNotBlank(c.getConfigInfo().getPackageName())).findFirst();
    	if(pkgNmOverride.isPresent())
    		return pkgNmOverride.get().getConfigInfo().getPackageName();
    	
    	String packageName = this.packageName;
    	String defaultModuleName = getModuleName(packageName);
    	String tableName = table.getTableName();
    	tableName = tableName.toLowerCase();
    	if(tableName.indexOf("_") > 0 && !tableName.startsWith("sys")) {
    		String tablePrefix = tableName.substring(0, tableName.indexOf("_"));
    		packageName = packageName.replace(defaultModuleName, tablePrefix);
    	}
    	return packageName;    	
    }

    /**
     * 获取模块名
     * 
     * @param packageName 包名
     * @return 模块名
     */
    public String getModuleName(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        String moduleName = StringUtils.substring(packageName, lastIndex + 1, nameLength);
        return moduleName;
    }

    public String getBasePackage(TableInfo table)
    {
    	String packageName = getPackageName(table);
        int lastIndex = packageName.lastIndexOf(".");
        String basePackage = StringUtils.substring(packageName, 0, lastIndex);
        return basePackage;
    }

    public String getProjectPath(TableInfo table)
    {
        String basePackageName = getBasePackage(table);
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(basePackageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }
    
    public String getRestProjectPath(TableInfo table)
    {
        String packageName = this.restPackageName;
        StringBuffer projectPath = new StringBuffer();
        projectPath.append("main/java/");
        projectPath.append(packageName.replace(".", "/"));
        projectPath.append("/");
        return projectPath.toString();
    }

    public static String replaceKeyword(String keyword)
    {
        String keyName = keyword.replaceAll("(?:表|信息)", "");
        return keyName;
    }

    static
    {
        javaTypeMap.put("tinyint", "Integer");
        javaTypeMap.put("smallint", "Integer");
        javaTypeMap.put("mediumint", "Integer");
        javaTypeMap.put("int", "Integer");
        javaTypeMap.put("integer", "integer");
        javaTypeMap.put("bigint", "Long");
        javaTypeMap.put("float", "Float");
        javaTypeMap.put("double", "Double");
        javaTypeMap.put("decimal", "BigDecimal");
        javaTypeMap.put("bit", "Boolean");
        javaTypeMap.put("char", "String");
        javaTypeMap.put("varchar", "String");
        javaTypeMap.put("tinytext", "String");
        javaTypeMap.put("text", "String");
        javaTypeMap.put("mediumtext", "String");
        javaTypeMap.put("longtext", "String");
        javaTypeMap.put("time", "Date");
        javaTypeMap.put("date", "Date");
        javaTypeMap.put("datetime", "Date");
        javaTypeMap.put("timestamp", "Date");
    }
}
