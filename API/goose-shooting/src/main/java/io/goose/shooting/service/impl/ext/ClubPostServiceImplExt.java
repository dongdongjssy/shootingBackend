package io.goose.shooting.service.impl.ext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.ClubPost;
import io.goose.shooting.domain.details.ClubPostDetails;
import io.goose.shooting.domain.details.CommentDetails;
import io.goose.shooting.service.impl.ClubPostServiceImpl;


@Service
public class ClubPostServiceImplExt extends ClubPostServiceImpl {

   @Autowired
   public CommentServiceImplExt commentService;


   public List<ClubPost> selectClubPostListByClubId( Long clubId ) {
      ClubPost clubPostSearch = new ClubPost();
      clubPostSearch.setClubId( clubId );

      return selectClubPostListAssoc( clubPostSearch );
   }


   public List<ClubPostDetails> selectClubPostDetailsListByClubId( Long clubId ) {

      List<ClubPost> clubPostList = selectClubPostAllAssoc();

      clubPostList = clubPostList.stream().filter( clubPost -> clubPost.getClubId() == clubId )
            .collect( Collectors.toList() );

      List<ClubPostDetails> clubPostDetailsList =
            convertClubPostListToClubPostDetailsList( clubPostList );

      return clubPostDetailsList;
   }


   public List<ClubPostDetails>
         convertClubPostListToClubPostDetailsList( List<ClubPost> clubPostList ) {

      List<ClubPostDetails> clubPostDetailsList = clubPostList.stream()
            .sorted( Comparator.comparing( ClubPost::getCreateTime ).reversed() ).map( clubPost -> {
               return getClubPostDetails( clubPost );
            } ).collect( Collectors.toList() );

      return clubPostDetailsList;
   }


   public ClubPostDetails selectClubPostDetailsById( Long clubPostId ) {
      ClubPost clubPost = selectClubPostById( clubPostId );
      return getClubPostDetails( clubPost );
   }


   private ClubPostDetails getClubPostDetails( ClubPost clubPost ) {

      List<CommentDetails> commentDetailsList = commentService.selectCommentDetailsList(
            clubPost.getId(), CommentServiceImplExt.FK_TABLE_ID_CLUB_POST );

      ClubPostDetails clubPostDetails = new ClubPostDetails();
      clubPostDetails.setClubPost( clubPost );
      clubPostDetails.setCommentDetailsList( commentDetailsList );

      return clubPostDetails;
   }
}
