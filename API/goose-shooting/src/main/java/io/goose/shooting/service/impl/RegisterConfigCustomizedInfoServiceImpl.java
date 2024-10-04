package io.goose.shooting.service.impl;

import java.util.List;

import io.goose.shooting.domain.RegisterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.goose.shooting.mapper.RegisterConfigCustomizedInfoMapper;
import io.goose.shooting.domain.RegisterConfigCustomizedInfo;

import io.goose.shooting.service.IRegisterConfigCustomizedInfoService;
import io.goose.common.support.Convert;

/**
 * 报名配置-自定义项目 服务层实现
 *
 * @author goose
 * @date 2021-01-14
 */
@Service
public class RegisterConfigCustomizedInfoServiceImpl implements IRegisterConfigCustomizedInfoService {
    @Autowired
    private RegisterConfigCustomizedInfoMapper registerConfigCustomizedInfoMapper;


    /**
     * 查询报名配置-自定义项目信息
     *
     * @param id 报名配置-自定义项目ID
     * @return 报名配置-自定义项目信息
     */
    @Override
    public RegisterConfigCustomizedInfo selectRegisterConfigCustomizedInfoById(Long id) {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoById(id);
    }

    /**
     * 查询报名配置-自定义项目信息 外键关联
     *
     * @param id 报名配置-自定义项目ID
     * @return 报名配置-自定义项目信息
     */
    @Override
    public RegisterConfigCustomizedInfo selectRegisterConfigCustomizedInfoByIdAssoc(Long id) {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoByIdAssoc(id);
    }

    /**
     * 查询所有报名配置-自定义项目列表
     *
     * @param
     * @return 报名配置-自定义项目集合
     */
    @Override
    public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoAll() {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoAll();
    }

    /**
     * 查询所有报名配置-自定义项目列表 外键关联
     *
     * @param
     * @return 报名配置-自定义项目集合
     */
    @Override
    public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoAllAssoc() {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoAllAssoc();
    }

    /**
     * 查询报名配置-自定义项目列表
     *
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 报名配置-自定义项目集合
     */
    @Override
    public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoList(RegisterConfigCustomizedInfo registerConfigCustomizedInfo) {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoList(registerConfigCustomizedInfo);
    }

    /**
     * 查询报名配置-自定义项目列表 外键关联
     *
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 报名配置-自定义项目集合
     */
    @Override
    public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoListAssoc(RegisterConfigCustomizedInfo registerConfigCustomizedInfo) {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoListAssoc(registerConfigCustomizedInfo);
    }

    /**
     * 新增报名配置-自定义项目
     *
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 结果
     */
    @Override
    public int insertRegisterConfigCustomizedInfo(RegisterConfigCustomizedInfo registerConfigCustomizedInfo) {
        return registerConfigCustomizedInfoMapper.insertRegisterConfigCustomizedInfo(registerConfigCustomizedInfo);
    }

    /**
     * 修改报名配置-自定义项目
     *
     * @param registerConfigCustomizedInfo 报名配置-自定义项目信息
     * @return 结果
     */
    @Override
    public int updateRegisterConfigCustomizedInfo(RegisterConfigCustomizedInfo registerConfigCustomizedInfo) {
        return registerConfigCustomizedInfoMapper.updateRegisterConfigCustomizedInfo(registerConfigCustomizedInfo);
    }

    /**
     * 删除报名配置-自定义项目对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRegisterConfigCustomizedInfoByIds(String ids) {
        return registerConfigCustomizedInfoMapper.deleteRegisterConfigCustomizedInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除报名配置-自定义项目对象
     *
     * @param id 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteRegisterConfigCustomizedInfoById(Long id) {
        return registerConfigCustomizedInfoMapper.deleteRegisterConfigCustomizedInfoById(id);
    }

    @Override
    public int deleteRegisterConfigCustomizedInfoByConfigId(Long id) {
        return registerConfigCustomizedInfoMapper.deleteRegisterConfigCustomizedInfoByConfigId(id);
    }

    @Override
    public List<RegisterConfigCustomizedInfo> selectRegisterConfigCustomizedInfoByConfigId(Long id) {
        return registerConfigCustomizedInfoMapper.selectRegisterConfigCustomizedInfoByConfigId(id);
    }


}
