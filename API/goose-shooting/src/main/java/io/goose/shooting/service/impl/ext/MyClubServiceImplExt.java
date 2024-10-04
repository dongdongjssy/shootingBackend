package io.goose.shooting.service.impl.ext;

import io.goose.shooting.domain.*;
import io.goose.shooting.service.*;
import io.goose.shooting.service.impl.MyClubServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyClubServiceImplExt extends MyClubServiceImpl {

   @Autowired
   public ClubPostServiceImplExt clubPostService;

    @Autowired
    private ClubActivityServiceImplExt clubActivityService;

   @Autowired
   private IUserFollowService userFollowService;

    @Autowired
    private ICarouselService carouselService;

    @Autowired
    private IScheduleService scheduleService;

    @Autowired
    private IRegisterConfigService registerConfigService;

    @Autowired
    private IRegisterConfigCustomizedInfoService registerConfigCustomizedInfoService;

    @Autowired
    private IRegisterConfigPersonalInfoService registerConfigPersonalInfoService;

    @Autowired
    private IRegisterConfigRoleInfoService registerConfigRoleInfoService;

   public List<?> selectClubPostDetailsListByUserId(Long clientUserId) {

      UserFollow myFollow = new UserFollow();
      myFollow.setClientUserId(clientUserId);
      myFollow.setFollowType("club");

       List<UserFollow> myClubList = userFollowService.selectUserFollowList(myFollow);

       List<ClubPost> postList= clubPostService.selectClubPostListAssoc(new ClubPost());
       List<ClubActivity> clubActivityList=clubActivityService.selectClubActivityAllAssoc();
       List list = new ArrayList() ;
       postList.forEach(clubPost -> {
           myClubList.forEach(club->{
               if(clubPost.getClubId()==club.getFollowId()){
                   clubPost.setRemark("已关注");
               }
           });
           list.add(clubPost);
       });
       clubActivityList.forEach(clubActivity -> {
           myClubList.forEach(club->{
               if(clubActivity.getClubId()==club.getFollowId()){
                   clubActivity.setRemark("已关注");
               }
           });
           List<Schedule> contestScheduels = scheduleService.selectScheduleByFkIdAndType(new Schedule(clubActivity.getId(), "clubActivity"));
           if (contestScheduels != null) {
               clubActivity.setSchedules(contestScheduels);
           }

           RegisterConfig registerConfig = registerConfigService.selectRegisterConfigByFkIdAndType(new RegisterConfig(clubActivity.getId(), "clubActivity"));

           if(registerConfig != null) {
               List<RegisterConfigRoleInfo> registerConfigRoleInfos = registerConfigRoleInfoService.selectRegisterConfigRoleInfoByConfigId(registerConfig.getId());
               if (registerConfigRoleInfos != null) registerConfig.setRoleInfos(registerConfigRoleInfos);

               List<RegisterConfigPersonalInfo> registerConfigPersonalInfos = registerConfigPersonalInfoService.selectRegisterConfigPersonalInfoByconfigId(registerConfig.getId());
               if (registerConfigPersonalInfos != null) registerConfig.setPersonalInfos(registerConfigPersonalInfos);

               List<RegisterConfigCustomizedInfo> registerConfigCustomizedInfos = registerConfigCustomizedInfoService.selectRegisterConfigCustomizedInfoByConfigId(registerConfig.getId());
               if (registerConfigCustomizedInfos != null) registerConfig.setCustomizedInfos(registerConfigCustomizedInfos);

               clubActivity.setRegisterConfig(registerConfig);
           }

           list.add(clubActivity);
       });
       ComparatorTimeExt comparator=new ComparatorTimeExt();
       Collections.sort(list, comparator);
       Collections.reverse(list);
       Carousel carousel=new Carousel();
       carousel.setOnPage(4);
       List<Carousel> carouselList = carouselService.selectCarouselList( carousel );
       int j=0;
       List list1=new ArrayList(16);
       for(int i=0;i<list.size();i++){
           list1.add(list.get(i));
           if(!carouselList.isEmpty()){
               if((i+1)%3==0){
                   if(j<carouselList.size()){
                       list1.add(carouselList.get(j));
                   }else {
                       j=j-carouselList.size();
                       list1.add(carouselList.get(j));
                   }
                   j++;
               }
           }


       }
       return list1;





      /* List<ClubPost> clubPostList = new ArrayList<>();
      myClubList.forEach(
            myClub -> {
               List<ClubPost> posts = clubPostService.selectClubPostListByClubId(myClub.getFollowId());
               if (posts.size() > 0) {
                  clubPostList.add(posts.get(0));
               }
            });

      
      List<ClubPost> newClubPostList = clubPostList;

      newClubPostList = newClubPostList.stream().sorted(Comparator.comparing(ClubPost::getCreateTime,Comparator.reverseOrder())).collect(Collectors.toList());
      //      List<ClubPostDetails> clubPostDetailsList =
      //            clubPostService.convertClubPostListToClubPostDetailsList(clubPostList);
      return newClubPostList;*/
   }
}
