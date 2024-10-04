package io.goose.shooting.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.VisitorMapper;
import io.goose.shooting.domain.Visitor;

import io.goose.shooting.service.IVisitorService;
import io.goose.common.support.Convert;

/**
 * 访客 服务层实现
 *
 * @author goose
 * @date 2020-12-24
 */
@Service
public class VisitorServiceImpl implements IVisitorService {
    @Autowired
    private VisitorMapper visitorMapper;


    /**
     * 查询访客信息
     *
     * @param id 访客ID
     * @return 访客信息
     */
    @Override
    public Visitor selectVisitorById(Long id) {
        return visitorMapper.selectVisitorById(id);
    }

    @Override
    public Visitor selectVisitorByEmail(String email) {
        return visitorMapper.selectVisitorByEmail(email);
    }

    /**
     * 查询访客信息 外键关联
     *
     * @param id 访客ID
     * @return 访客信息
     */
    @Override
    public Visitor selectVisitorByIdAssoc(Long id) {
        return visitorMapper.selectVisitorByIdAssoc(id);
    }

    /**
     * 查询所有访客列表
     *
     * @param
     * @return 访客集合
     */
    @Override
    public List<Visitor> selectVisitorAll() {
        return visitorMapper.selectVisitorAll();
    }

    /**
     * 查询所有访客列表 外键关联
     *
     * @param
     * @return 访客集合
     */
    @Override
    public List<Visitor> selectVisitorAllAssoc() {
        return visitorMapper.selectVisitorAllAssoc();
    }

    /**
     * 查询访客列表
     *
     * @param visitor 访客信息
     * @return 访客集合
     */
    @Override
    public List<Visitor> selectVisitorList(Visitor visitor) {
        return visitorMapper.selectVisitorList(visitor);
    }

    /**
     * 查询访客列表 外键关联
     *
     * @param visitor 访客信息
     * @return 访客集合
     */
    @Override
    public List<Visitor> selectVisitorListAssoc(Visitor visitor) {
        return visitorMapper.selectVisitorListAssoc(visitor);
    }

    /**
     * 新增访客
     *
     * @param visitor 访客信息
     * @return 结果
     */
    @Override
    public int insertVisitor(Visitor visitor) {
        return visitorMapper.insertVisitor(visitor);
    }

    /**
     * 修改访客
     *
     * @param visitor 访客信息
     * @return 结果
     */
    @Override
    public int updateVisitor(Visitor visitor) {
        return visitorMapper.updateVisitor(visitor);
    }

    /**
     * 删除访客对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVisitorByIds(String ids) {
        return visitorMapper.deleteVisitorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除访客对象
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteVisitorById(Long id) {
        return visitorMapper.deleteVisitorById(id);
    }


}
