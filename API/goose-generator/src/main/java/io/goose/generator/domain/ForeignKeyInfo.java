package io.goose.generator.domain;

import java.util.ArrayList;
import java.util.List;

public class ForeignKeyInfo {	
	
	//TODO: 目前只支持1个外键,多个外键需要进一步修改代码
	private String tableName;
	private String columnName;
	private String referencedTableName;
	private String referencedColumnName;
	private List<ColumnInfo> referencedTableColumns;
	private String alias;
	
    /** Java属性类型 */
    private String attrType;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName */
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName */
    private String attrname;
    
    private int override ;
    
    public ForeignKeyInfo() {
    }

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getReferencedTableName() {
		return referencedTableName;
	}
	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}
	public String getReferencedColumnName() {
		return referencedColumnName;
	}
	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}
		
	public List<ColumnInfo> getReferencedTableColumns() {
		return referencedTableColumns;
	}
	public void setReferencedTableColumns(List<ColumnInfo> referencedTableColumns) {
		this.referencedTableColumns = referencedTableColumns;
	}
	
    public String getAttrName()
    {
        return attrName;
    }

    public void setAttrName(String attrName)
    {
        this.attrName = attrName;
    }

    public String getAttrname()
    {
        return attrname;
    }

    public void setAttrname(String attrname)
    {
        this.attrname = attrname;
    }

    public String getAttrType()
    {
        return attrType;
    }

    public void setAttrType(String attrType)
    {
        this.attrType = attrType;
    }
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getOverride() {
		return override;
	}

	public void setOverride(int override) {
		this.override = override;
	}


}
