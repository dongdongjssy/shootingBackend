package io.goose.generator.domain;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 字段类型配置 由数据库字段的注释解析而来
 * 注释结构示例:{"title": "状态", "type": "dict", "value": "sys_common_status"} {"title": "登录时间", "type": "date"}
 * 
 * @author goose
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnConfigInfo
{
    /**
     * 属性标题
     */
    private String title;

    /**
     * 属性类型 dict(字典，value对应字典管理的字典类型), date(包括date)
     */
    private String type;  //"date", "dict"

    /**
     * 属性值，参考数据类型，可为空
     */
    private String value;
    
	private String columnName;      //not option

    
	private short searchable; 		//0 or 1
	private short textSearchOpt;	// 1 匹配
	private short checkUnique;		//0 or 1
	private short uploadPic;		//0 or 1
	private short richEditor;		//0 or 1 or 2
	private short ellipsisText;      //0 or 1
	private short cachable;
	
	private short systemField;         //0 or 1 系统插入的，如创建者，创建时间，更新时间等
	
	private String fkTable;			//foreign key table name
	private String fkColumn;		//foreign key column
	private String fkDisplayColumn;	//foreign key table's column to display
	private String fkInputMethod;	//"text" or "select"
	private short  fkCreateAssoc;	//0 or 1
	
	private String packageName;
	private String className;
	
	private LinkedHashMap<Integer, String> data;
	//private LinkedHashMap<String, String> child;


	public String getFkTable() {
		return fkTable;
	}

	public void setFkTable(String fkTable) {
		this.fkTable = fkTable;
	}

	public String getFkColumn() {
		return fkColumn;
	}

	public void setFkColumn(String fkColumn) {
		this.fkColumn = fkColumn;
	}

	public String getFkDisplayColumn() {
		return fkDisplayColumn;
	}

	public void setFkDisplayColumn(String fkDisplayColumn) {
		this.fkDisplayColumn = fkDisplayColumn;
	}

	public String getFkInputMethod() {
		return fkInputMethod;
	}

	public void setFkInputMethod(String fkInputMethod) {
		this.fkInputMethod = fkInputMethod;
	}

	public ColumnConfigInfo()
    {
        super();
    }

    public ColumnConfigInfo(String title, String type, String value)
    {
        super();
        this.title = title;
        this.type = type;
        this.value = value;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

	public short getSearchable() {
		return searchable;
	}

	public void setSearchable(short searchable) {
		this.searchable = searchable;
	}

	public short getTextSearchOpt() {
		return textSearchOpt;
	}

	public void setTextSearchOpt(short textSearchOpt) {
		this.textSearchOpt = textSearchOpt;
	}

	public short getCheckUnique() {
		return checkUnique;
	}

	public void setCheckUnique(short checkUnique) {
		this.checkUnique = checkUnique;
	}

	public short getUploadPic() {
		return uploadPic;
	}

	public void setUploadPic(short uploadPic) {
		this.uploadPic = uploadPic;
	}

	public short getRichEditor() {
		return richEditor;
	}

	public void setRichEditor(short richEditor) {
		this.richEditor = richEditor;
	}
    
    public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public short getFkCreateAssoc() {
		return fkCreateAssoc;
	}

	public void setFkCreateAssoc(short fkCreateAssoc) {
		this.fkCreateAssoc = fkCreateAssoc;
	}

	public short getSystemField() {
		return systemField;
	}

	public void setSystemField(short systemField) {
		this.systemField = systemField;
	}

	public short getEllipsisText() {
		return ellipsisText;
	}

	public void setEllipsisText(short ellipsisText) {
		this.ellipsisText = ellipsisText;
	}

	public short getCachable() {
		return cachable;
	}

	public String getPackageName() {
		return packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setCachable(short cachable) {
		this.cachable = cachable;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public LinkedHashMap<Integer, String> getData() {
		return data;
	}

	public void setData(LinkedHashMap<Integer, String> data) {
		this.data = data;
	}

//	public LinkedHashMap<String, String> getChild() {
//		return child;
//	}
//
//	public void setChild(LinkedHashMap<String, String> child) {
//		this.child = child;
//	}


	
}
