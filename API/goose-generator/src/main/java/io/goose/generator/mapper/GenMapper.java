package io.goose.generator.mapper;

import java.util.List;

import io.goose.generator.domain.ColumnInfo;
import io.goose.generator.domain.ForeignKeyInfo;
import io.goose.generator.domain.TableInfo;

/**
 * 代码生成 数据层
 * 
 * @author goose
 */
public interface GenMapper
{
    /**
     * 查询数据库表信息
     * 
     * @param tableInfo 表信息
     * @return 数据库表列表
     */
    public List<TableInfo> selectTableList(TableInfo tableInfo);

    /**
     * 根据表名称查询信息
     * 
     * @param tableName 表名称
     * @return 表信息
     */
    public TableInfo selectTableByName(String tableName);

    /**
     * 根据表名称查询列信息
     * 
     * @param tableName 表名称
     * @return 列信息
     */
    public List<ColumnInfo> selectTableColumnsByName(String tableName);
    

    /**
     * 根据表外键
     * 
     * @param tableName 表名称
     * @return 外键信息
     */
    public List<ForeignKeyInfo> selectForeignKeyByName(String tableName);
}
