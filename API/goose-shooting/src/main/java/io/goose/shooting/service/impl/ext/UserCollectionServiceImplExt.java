package io.goose.shooting.service.impl.ext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.ClubActivity;
import io.goose.shooting.domain.Contest;
import io.goose.shooting.domain.Post;
import io.goose.shooting.domain.Recommend;
import io.goose.shooting.domain.Training;
import io.goose.shooting.domain.UserCollection;
import io.goose.shooting.domain.details.PostDetails;
import io.goose.shooting.domain.details.RecommendDetails;
import io.goose.shooting.service.IClubActivityService;
import io.goose.shooting.service.IContestService;
import io.goose.shooting.service.ITrainingService;
import io.goose.shooting.service.impl.UserCollectionServiceImpl;


@Service
public class UserCollectionServiceImplExt extends UserCollectionServiceImpl {

   private static final String CLUB_ACTIVITY = "clubActivity";

   private static final String CONTEST = "contest";

   private static final String TRAINING = "training";

   private static final String CLUB_POST = "clubPost";

   private static final String POST = "post";

   private static final String RECOMMEND = "recommend";

   @Autowired
   ITrainingService trainingService;

   @Autowired
   IContestService contestService;

   @Autowired
   IClubActivityService clubActivityService;

   @Autowired
   RecommendServiceImplExt recommendService;

   @Autowired
   PostServiceImplExt postService;

   @Autowired
   ClubPostServiceImplExt clubPostService;


   public List<Object> selectUserCollectionDetailsList( Long clientUserId ) {

      List<Object> result = new ArrayList<>();

      UserCollection userCollectionSearch = new UserCollection();
      userCollectionSearch.setClientUserId( clientUserId );
      List<UserCollection> initialList = selectUserCollectionList( userCollectionSearch );
      List<UserCollection> userCollectionList = initialList.stream()
            .sorted( Comparator.comparingLong( UserCollection::getId ).reversed() )
            .collect( Collectors.toList() );

      userCollectionList.forEach( userCollection -> {
         addCollectionDetails( result, userCollection );
      } );

      return result;
   }


   public List<Object> selectPostCollectionDetailsList( Long clientUserId ) {

      return getUserCollectionsByUserIdAndValidTypes( clientUserId, RECOMMEND, POST, CLUB_POST );
   }


   public List<Object> selectActivityCollectionDetailsList( Long clientUserId ) {

      return getUserCollectionsByUserIdAndValidTypes( clientUserId, TRAINING, CONTEST,
            CLUB_ACTIVITY );
   }


   private List<Object> getUserCollectionsByUserIdAndValidTypes( Long clientUserId,
         String... validCollectionTypes ) {

      List<Object> result = new ArrayList<>();

      UserCollection userCollectionSearch = new UserCollection();
      userCollectionSearch.setClientUserId( clientUserId );
      List<UserCollection> initialList = selectUserCollectionList( userCollectionSearch );

      initialList = initialList.stream()
            .filter( userCollection -> filter( userCollection, validCollectionTypes ) )
            .collect( Collectors.toList() );

      List<UserCollection> userCollectionList = initialList.stream()
            .sorted( Comparator.comparingLong( UserCollection::getId ).reversed() )
            .collect( Collectors.toList() );

      userCollectionList.forEach( userCollection -> {
         addCollectionDetails( result, userCollection );
      } );

      return result;
   }


   private boolean filter( UserCollection userCollection, String... validCollectionTypes ) {

      String collectionType = userCollection.getCollectionType();

      for ( String validCollectionType : validCollectionTypes ) {
         if ( validCollectionType.equals( collectionType ) ) {
            return true;
         }
      }

      return false;
   }


   private void addCollectionDetails( List<Object> result, UserCollection userCollection ) {

      String collectionType = userCollection.getCollectionType();
      Long collectionId = userCollection.getCollectionId();

      if ( RECOMMEND.equals( collectionType ) ) {

         Recommend item = new Recommend();
         item.setId( collectionId );
         item.setUserId(userCollection.getClientUserId());
         List<RecommendDetails> itemList = recommendService.selectRecommendDetailsList( item );

         if ( !itemList.isEmpty() ) {
            result.add( itemList.get( 0 ) );
         }

         return;
      }

      if ( POST.equals( collectionType ) ) {

         Post item = new Post();
         item.setId( collectionId );
         List<PostDetails> itemList = postService.selectPostDetailsList( item );

         if ( !itemList.isEmpty() ) {
            result.add( itemList.get( 0 ) );
         }

         return;
      }

      if ( CLUB_POST.equals( collectionType ) ) {
         result.add( clubPostService.selectClubPostDetailsById( collectionId ) );
         return;
      }

      if ( TRAINING.equals( collectionType ) ) {
         Training item = trainingService.selectTrainingByIdAssoc( collectionId );
         item.setRemark( TRAINING );
         result.add( item );
         return;
      }

      if ( CONTEST.equals( collectionType ) ) {
         Contest item = contestService.selectContestByIdAssoc( collectionId );
         item.setRemark( CONTEST );
         result.add( item );
         return;
      }

      if ( CLUB_ACTIVITY.equals( collectionType ) ) {
         ClubActivity item = clubActivityService.selectClubActivityByIdAssoc( collectionId );
         item.setRemark( CLUB_ACTIVITY );
         result.add( item );
         return;
      }
   }

}
