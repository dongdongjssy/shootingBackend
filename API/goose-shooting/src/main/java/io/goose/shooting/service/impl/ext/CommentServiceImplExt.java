package io.goose.shooting.service.impl.ext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.goose.shooting.domain.*;
import io.goose.shooting.service.impl.*;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.details.CommentDetails;


@Service
public class CommentServiceImplExt extends CommentServiceImpl {

   private static final int MAX_TITLE_LENGTH = 30;

   @Autowired
   CommentFeedbackServiceImpl commentFeedbackService;

   @Autowired
   RecommendServiceImpl recommendService;

   @Autowired
   PostServiceImpl postService;

   @Autowired
   ClubPostServiceImpl clubPostService;

   @Autowired
   RecommendCoachServiceImpl recommendCoachService;
   @Autowired
   RecommendJudgeServiceImpl recommendJudgeService;


   public static final int FK_TABLE_ID_RECOMMEND = 1;
   public static final int FK_TABLE_ID_POST = 2;
   public static final int FK_TABLE_ID_CLUB_POST = 3;
   public static final int FK_TABLE_ID_COACH = 4;
   public static final int FK_TABLE_ID_JUDGE = 5;


   @Override
   public int insertComment( Comment comment ) {
      int result = commentMapper.insertComment( comment );

      if ( result > 0 ) {
         if ( comment.getFkId() != null && comment.getFkTable() != null ) {
            if ( CommentServiceImplExt.FK_TABLE_ID_RECOMMEND == comment.getFkTable() ) {
               Recommend recommend = recommendService.selectRecommendById( comment.getFkId() );
               if ( recommend != null ) {
                  recommend.setCommentCount(
                        recommend.getCommentCount() != null ? recommend.getCommentCount() + 1 : 1 );
                  recommendService.updateRecommend( recommend );
               }
            }

            if ( CommentServiceImplExt.FK_TABLE_ID_POST == comment.getFkTable() ) {
               Post post = postService.selectPostById( comment.getFkId() );
               if ( post != null ) {
                  post.setCommentCount(
                        post.getCommentCount() != null ? post.getCommentCount() + 1 : 1 );
                  postService.updatePost( post );
               }
            }

            if ( CommentServiceImplExt.FK_TABLE_ID_CLUB_POST == comment.getFkTable() ) {
               ClubPost clubPost = clubPostService.selectClubPostById( comment.getFkId() );
               if ( clubPost != null ) {
                  clubPost.setCommentCount(
                        clubPost.getCommentCount() != null ? clubPost.getCommentCount() + 1 : 1 );
                  clubPostService.updateClubPost( clubPost );
               }
            }

            if ( CommentServiceImplExt.FK_TABLE_ID_COACH == comment.getFkTable() ) {
               RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById( comment.getFkId() );
               if ( recommendCoach != null ) {
                  recommendCoach.setCommentCount(
                          recommendCoach.getCommentCount() != null ? recommendCoach.getCommentCount() + 1 : 1 );
                  recommendCoachService.updateRecommendCoach( recommendCoach );
               }
            }

            if ( CommentServiceImplExt.FK_TABLE_ID_JUDGE == comment.getFkTable() ) {
               RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById( comment.getFkId() );
               if ( recommendJudge != null ) {
                  recommendJudge.setCommentCount(
                          recommendJudge.getCommentCount() != null ? recommendJudge.getCommentCount() + 1 : 1 );
                  recommendJudgeService.updateRecommendJudge( recommendJudge );
               }
            }
         }
      }

      return result;
   }


   @Override
   public List<Comment> selectCommentListAssoc( Comment commentInput ) {

      List<Comment> commentList = commentMapper.selectCommentListAssoc( commentInput );
      commentList.forEach( comment -> {
         setTitleToRemark( comment, true );
         ServiceUtils.setUserFullDisplay( comment.getClientUser() );
      } );

      return commentList;
   }


   @Override
   public Comment selectCommentByIdAssoc( Long id ) {

      Comment comment = commentMapper.selectCommentByIdAssoc( id );
      setTitleToRemark( comment, false );

      return comment;
   }


   public List<CommentDetails> selectCommentDetailsList( Long fkId, int fkTableId ) {

      Comment commentForSearch = new Comment();
      commentForSearch.setFkId( fkId );
      commentForSearch.setFkTable( fkTableId );

      List<Comment> commentList = selectCommentList( commentForSearch );

      List<CommentDetails> commentDetailsList = commentList.stream()
            .map( comment -> getCommentDetails( comment ) ).collect( Collectors.toList() );

      return commentDetailsList;
   }


   public List<CommentDetails> selectCommentDetailsListPage( Comment commentForSearch ) {

      List<Comment> commentList = selectCommentList( commentForSearch );

      List<CommentDetails> commentDetailsList = commentList.stream()
            .map( comment -> getCommentDetails( comment ) ).collect( Collectors.toList() );

      return commentDetailsList;
   }


   private CommentDetails getCommentDetails( Comment comment ) {

      CommentFeedback commentFeedback = new CommentFeedback();
      commentFeedback.setCommentId( comment.getId() );
      List<CommentFeedback> commentFeedbackList =
            commentFeedbackService.selectCommentFeedbackList( commentFeedback );

      CommentDetails commentDetails = new CommentDetails();
      commentDetails.setComment( comment );
      commentDetails.setCommentFeedbackList( commentFeedbackList.stream()
            .sorted( Comparator.comparing( CommentFeedback::getCreateTime ).reversed() )
            .collect( Collectors.toList() ) );

      return commentDetails;
   }


   private void setTitleToRemark( Comment comment, boolean isToTrim ) {

      Long fkId = comment.getFkId();

      String title = "";

      String content = "";
      switch ( comment.getFkTable() ) {
         case FK_TABLE_ID_RECOMMEND:
            Recommend recommend = recommendService.selectRecommendById( fkId );
            title = recommend == null ? "" : recommend.getTitle();
            break;
         case FK_TABLE_ID_POST:
            // post has no title, so use content
            Post post = postService.selectPostById( fkId );
            content = post == null ? "" : post.getContent();
            title = Jsoup.parse( content ).text();
            break;
         case FK_TABLE_ID_CLUB_POST:
            // post has no title, so use content
            ClubPost clubPost = clubPostService.selectClubPostById( fkId );
            content = clubPost == null ? "" : clubPost.getContent();
            title = Jsoup.parse( content ).text();
            break;
         case FK_TABLE_ID_COACH:
            // post has no title, so use content
            RecommendCoach recommendCoach = recommendCoachService.selectRecommendCoachById( fkId );
            content = recommendCoach == null ? "" : recommendCoach.getContent();
            title = Jsoup.parse( content ).text();
            break;
         case FK_TABLE_ID_JUDGE:
            // post has no title, so use content
            RecommendJudge recommendJudge = recommendJudgeService.selectRecommendJudgeById(fkId);
            content = recommendJudge == null ? "" : recommendJudge.getContent();
            title = Jsoup.parse( content ).text();
            break;


      }

      title = isToTrim ? trimTitle( title ) : title;

      comment.setContent( Jsoup.parse( comment.getContent() ).text() );
      comment.setRemark( title );
   }


   private String trimTitle( String contentText ) {

      return contentText.length() > MAX_TITLE_LENGTH
            ? contentText.substring( 0, MAX_TITLE_LENGTH ) + "..." : contentText;
   }

}
