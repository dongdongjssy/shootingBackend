package io.goose.generator.domain;

import io.goose.common.json.JSON;
import io.goose.common.utils.StringUtils;

/**
 * 数据库表列信息
 * 
 * @author goose
 */
public class ColumnInfo
{
    /** 字段名称 */
    private String columnName;

    /** 字段类型 */
    private String dataType;

    /** 列描述 */
    private String columnComment;
    
    /** 列配置 */
    private ColumnConfigInfo configInfo;

    /** Java属性类型 */
    private String attrType;

    /** Java属性名称(第一个字母大写)，如：user_name => UserName */
    private String attrName;

    /** Java属性名称(第一个字母小写)，如：user_name => userName */
    private String attrname;    
   
    private String refTableName;

	private String refColumnName;
	
	private String refClassName;
	private String refClassname;
	private String refAttrname;
	private String refDisplayAttrname;


	public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getDataType()
    {
        return dataType;
    }

    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getColumnComment()
    {
        return columnComment;
    }

    public void setColumnComment(String columnComment) throws Exception
    {
        // 根据列描述解析列的配置信息
        if (StringUtils.isNotEmpty(columnComment) && columnComment.startsWith("{"))
        {
            this.configInfo = JSON.unmarshal(columnComment, ColumnConfigInfo.class);
            this.configInfo.setColumnName(columnName);
            this.columnComment = configInfo.getTitle();
        }
        else
        {
            this.columnComment = columnComment;
        }
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
    
    public ColumnConfigInfo getConfigInfo()
    {
        return configInfo;
    }

    public void setConfigInfo(ColumnConfigInfo configInfo)
    {
        this.configInfo = configInfo;
    }


    public String getRefTableName() {
		return refTableName;
	}

	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}

	public String getRefColumnName() {
		return refColumnName;
	}

	public void setRefColumnName(String refColumnName) {
		this.refColumnName = refColumnName;
	}

	public String getRefClassName() {
		return refClassName;
	}

	public String getRefClassname() {
		return refClassname;
	}

	public void setRefClassname(String refClassname) {
		this.refClassname = refClassname;
	}
	
	public void setRefClassName(String refClassName) {
		this.refClassName = refClassName;
	}

	public String getRefAttrname() {
		return refAttrname;
	}

	public void setRefAttrname(String refAttrname) {
		this.refAttrname = refAttrname;
	}

	public String getRefDisplayAttrname() {
		return refDisplayAttrname;
	}

	public void setRefDisplayAttrname(String refDisplayAttrname) {
		this.refDisplayAttrname = refDisplayAttrname;
	}
	
	
}
