package io.goose.shooting.service.impl.ext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.goose.shooting.domain.Coach;
import io.goose.shooting.service.impl.CoachServiceImpl;


@Service
public class CoachServiceImplExt extends CoachServiceImpl {

   private static final String[] CATEGORY = { "IPSC", "IDPA" };


   public List<CoachGroup> selectCoachGroupList() {

      List<Coach> coachList = selectCoachListAssoc( new Coach() );
      coachList.forEach( coach -> setCategoryNameToRemark( coach ) );

      List<CoachGroup> coachGroupList = new ArrayList<>();

      int index = 1;
      for ( String group : CATEGORY ) {

         CoachGroup coachGroup = new CoachGroup();
         coachGroup.setGroup( group );
         coachGroup.setCoachList( filterJudge( coachList, index++ ) );
         coachGroupList.add( coachGroup );
      }

      return coachGroupList;
   }


   private List<Coach> filterJudge( List<Coach> coachList, int categoryIndex ) {
      return coachList.stream().filter( coach -> coach.getCategory() == categoryIndex )
            .collect( Collectors.toList() );
   }


   public void setCategoryNameToRemark( Coach coach ) {
      coach.setRemark( CATEGORY[coach.getCategory() - 1] );
   }


   private class CoachGroup {

      private String group = null;
      private List<Coach> coachList = null;


      private void setCoachList( List<Coach> coachList ) {
         this.coachList = coachList;
      }


      public String getGroup() {
         return group;
      }


      public List<Coach> getCoachList() {
         return coachList;
      }


      private void setGroup( String group ) {
         this.group = group;
      }

   }
}
