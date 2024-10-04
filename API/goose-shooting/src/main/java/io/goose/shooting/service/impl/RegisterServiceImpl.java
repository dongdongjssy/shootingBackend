package io.goose.shooting.service.impl;

import java.util.List;

import io.goose.shooting.domain.RegisterFormItem;
import io.goose.shooting.mapper.RegisterFormItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import io.goose.common.support.Convert;
import io.goose.shooting.domain.Register;
import io.goose.shooting.mapper.RegisterMapper;
import io.goose.shooting.service.IRegisterService;


/**
 * 报名 服务层实现
 * 
 * @author goose
 * @date 2020-05-19
 */
@Service
@Primary
public class RegisterServiceImpl implements IRegisterService {

   @Autowired
   protected RegisterMapper registerMapper;
   @Autowired
   protected RegisterFormItemMapper registerFormItemMapper;


   /**
    * 查询报名信息
    * 
    * @param id
    *           报名ID
    * @return 报名信息
    */
   @Override
   public Register selectRegisterById( Long id ) {
      Register register= registerMapper.selectRegisterById( id );
      RegisterFormItem registerFormItem=new RegisterFormItem();
      registerFormItem.setRegisterId(register.getId());
      List<RegisterFormItem> registerFormItemList= registerFormItemMapper.selectRegisterFormItemList(registerFormItem);
      register.setRegisterFormItems(registerFormItemList);
      return register;
   }


   /**
    * 查询报名信息 外键关联
    * 
    * @param id
    *           报名ID
    * @return 报名信息
    */
   @Override
   public Register selectRegisterByIdAssoc( Long id ) {
      return registerMapper.selectRegisterByIdAssoc( id );
   }


   /**
    * 查询所有报名列表
    * 
    * @param
    * @return 报名集合
    */
   @Override
   public List<Register> selectRegisterAll() {
      return registerMapper.selectRegisterAll();
   }


   /**
    * 查询所有报名列表 外键关联
    * 
    * @param
    * @return 报名集合
    */
   @Override
   public List<Register> selectRegisterAllAssoc() {
      return registerMapper.selectRegisterAllAssoc();
   }


   /**
    * 查询报名列表
    * 
    * @param register
    *           报名信息
    * @return 报名集合
    */
   @Override
   public List<Register> selectRegisterList( Register register ) {
      return registerMapper.selectRegisterList( register );
   }


   /**
    * 查询报名列表 外键关联
    * 
    * @param register
    *           报名信息
    * @return 报名集合
    */
   @Override
   public List<Register> selectRegisterListAssoc( Register register ) {
      return registerMapper.selectRegisterListAssoc( register );
   }


   /**
    * 新增报名
    * 
    * @param register
    *           报名信息
    * @return 结果
    */
   @Override
   public int insertRegister( Register register ) {
      return registerMapper.insertRegister( register );
   }


   /**
    * 修改报名
    * 
    * @param register
    *           报名信息
    * @return 结果
    */
   @Override
   public int updateRegister( Register register ) {
      return registerMapper.updateRegister( register );
   }


   /**
    * 删除报名对象
    * 
    * @param ids
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRegisterByIds( String ids ) {
      return registerMapper.deleteRegisterByIds( Convert.toStrArray( ids ) );
   }


   /**
    * 删除报名对象
    * 
    * @param id
    *           需要删除的数据ID
    * @return 结果
    */
   @Override
   public int deleteRegisterById( Long id ) {
      return registerMapper.deleteRegisterById( id );
   }

}
