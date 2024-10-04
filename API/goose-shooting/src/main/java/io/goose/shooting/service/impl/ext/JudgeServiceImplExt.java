package io.goose.shooting.service.impl.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.goose.shooting.domain.Judge;
import io.goose.shooting.service.impl.JudgeServiceImpl;


@Service
public class JudgeServiceImplExt extends JudgeServiceImpl {

   private static final String[] NATIONALITY = { "国内", "国际" };


   public List<JudgeGroup> selectJudgeGroupList() {

      List<Judge> judgeList = selectJudgeListAssoc( new Judge() );
      judgeList.forEach( judge -> setCategoryNameToRemark( judge ) );

      List<JudgeGroup> judgeGroupList = new ArrayList<>();

      int index = 1;
      for ( String group : NATIONALITY ) {

         JudgeGroup judgeGroup = new JudgeGroup();
         judgeGroup.setGroup( group );
         judgeGroup.setJudgeList( filterJudge( judgeList, index++ ) );
         judgeGroupList.add( judgeGroup );
      }

      return judgeGroupList;
   }


   private List<Judge> filterJudge( List<Judge> judgeList, int nationalityIndex ) {
      return judgeList.stream().filter( judge -> judge.getNationality() == nationalityIndex )
            .collect( Collectors.toList() );
   }


   public void setCategoryNameToRemark( Judge judge ) {

      final String[] categoryName = { "IPSC", "IDPA" };

      judge.setRemark( categoryName[judge.getCategory() - 1] );
   }


   private class JudgeGroup {

      private String group = null;
      private List<Judge> judgeList = null;


      private void setJudgeList( List<Judge> judgeList ) {
         this.judgeList = judgeList;
      }


      public String getGroup() {
         return group;
      }


      public List<Judge> getJudgeList() {
         return judgeList;
      }


      private void setGroup( String group ) {
         this.group = group;
      }

   }
}
