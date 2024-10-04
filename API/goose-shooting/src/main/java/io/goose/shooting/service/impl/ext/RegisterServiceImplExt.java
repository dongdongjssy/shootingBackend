package io.goose.shooting.service.impl.ext;

import java.util.List;

import io.goose.shooting.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.service.impl.ClubActivityServiceImpl;
import io.goose.shooting.service.impl.ContestServiceImpl;
import io.goose.shooting.service.impl.PostServiceImpl;
import io.goose.shooting.service.impl.RegisterServiceImpl;
import io.goose.shooting.service.impl.TrainingServiceImpl;


@Service
public class RegisterServiceImplExt extends RegisterServiceImpl {

   @Autowired
   TrainingServiceImpl trainingService;

   @Autowired
   ContestServiceImpl contestService;

   @Autowired
   ClubActivityServiceImpl clubActivityService;

   @Autowired
   PostServiceImpl postService;

   private static final int FK_TABLE_ID_TRAINING = 1;
   private static final int FK_TABLE_ID_CONTEST = 2;
   private static final int FK_TABLE_ID_CLUB_ACTIVITY = 3;


   @Override
   public Register selectRegisterByIdAssoc( Long id ) {

      Register register = registerMapper.selectRegisterByIdAssoc( id );

      RegisterFormItem registerFormItem=new RegisterFormItem();
      registerFormItem.setRegisterId(id);
      List<RegisterFormItem> registerFormItemList=registerFormItemMapper.selectRegisterFormItemList(registerFormItem);
      register.setRegisterFormItems(registerFormItemList);
      setTitleToRemark( register );

      return register;
   }


   @Override
   public List<Register> selectRegisterListAssoc( Register registerInput ) {

      List<Register> registerList = registerMapper.selectRegisterListAssoc( registerInput );
      registerList.forEach( register -> {
         setTitleToRemark( register );
         ServiceUtils.setUserFullDisplay( register.getClientUser() );
      } );

      return registerList;
   }


   private void setTitleToRemark( Register register ) {

      Long fkId = register.getFkId();

      String title = "";

      switch ( register.getFkTable() ) {
         case FK_TABLE_ID_TRAINING:
            Training training = trainingService.selectTrainingById( fkId );
            title = training == null ? "" : training.getTitle();
            break;
         case FK_TABLE_ID_CONTEST:
            Contest contest = contestService.selectContestById( fkId );
            title = contest == null ? "" : contest.getTitle();
            break;
         case FK_TABLE_ID_CLUB_ACTIVITY:
            ClubActivity clubActivity = clubActivityService.selectClubActivityById( fkId );
            title = clubActivity == null ? "" : clubActivity.getTitle();
            break;
      }

      register.setRemark( title );
   }

}
