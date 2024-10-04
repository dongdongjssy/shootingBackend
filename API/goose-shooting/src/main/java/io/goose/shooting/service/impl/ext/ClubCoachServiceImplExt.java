package io.goose.shooting.service.impl.ext;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.ClubCoach;
import io.goose.shooting.domain.Coach;
import io.goose.shooting.service.impl.ClubCoachServiceImpl;


@Service
public class ClubCoachServiceImplExt extends ClubCoachServiceImpl {

   @Autowired
   CoachServiceImplExt coachService;


   public List<Coach> selectCoachListByClubId( Long clubId ) {

      List<ClubCoach> clubCoachList = selectClubCoachAllAssoc();
      List<Coach> coachList =
            clubCoachList.stream().filter( clubCoach -> clubCoach.getClubId() == clubId )
                  .map( clubCoach -> clubCoach.getCoach() ).collect( Collectors.toList() );

      coachList.forEach( coach -> coachService.setCategoryNameToRemark( coach ) );

      return coachList;
   }
}
