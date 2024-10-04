package io.goose.shooting.service.impl.ext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.goose.shooting.domain.ClubActivity;
import io.goose.shooting.service.impl.ClubActivityServiceImpl;


@Service
public class ClubActivityServiceImplExt extends ClubActivityServiceImpl {

   public List<ClubActivity> selectClubActivityListByClubId( Long clubId ) {

      List<ClubActivity> clubActivityList = selectClubActivityAllAssoc();
      clubActivityList =
            clubActivityList.stream().filter( clubActivity -> clubActivity.getClubId() == clubId && clubActivity.getStatus() == 0)
                  .sorted( Comparator.comparing( ClubActivity::getCreateTime ).reversed() )
                  .collect( Collectors.toList() );

      return clubActivityList;
   }
}
