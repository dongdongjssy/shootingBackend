package io.goose.shooting.service.impl.ext;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.Comment;
import io.goose.shooting.domain.CommentFeedback;
import io.goose.shooting.service.impl.CommentFeedbackServiceImpl;


@Service
public class CommentFeedbackServiceImplExt extends CommentFeedbackServiceImpl {

   private static final int MAX_TITLE_LENGTH = 30;


   @Override
   public List<CommentFeedback>
         selectCommentFeedbackListAssoc( CommentFeedback commentFeedbackInput ) {

      List<CommentFeedback> commentFeedbackList =
            commentFeedbackMapper.selectCommentFeedbackListAssoc( commentFeedbackInput );

      commentFeedbackList.forEach( commentFeedback -> {
         setContentToRemark( commentFeedback.getComment(), true );
         ServiceUtils.setUserFullDisplay( commentFeedback.getClientUser() );
      } );

      return commentFeedbackList;
   }


   @Override
   public CommentFeedback selectCommentFeedbackByIdAssoc( Long id ) {

      CommentFeedback commentFeedback = commentFeedbackMapper.selectCommentFeedbackByIdAssoc( id );
      setContentToRemark( commentFeedback.getComment(), false );

      return commentFeedback;
   }


   private void setContentToRemark( Comment comment, boolean isToTrim ) {

      String content = Jsoup.parse( comment.getContent() ).text();
      content = isToTrim ? trimString( content ) : content;

      comment.setRemark( content );
   }


   private String trimString( String contentText ) {

      return contentText.length() > MAX_TITLE_LENGTH
            ? contentText.substring( 0, MAX_TITLE_LENGTH ) + "..." : contentText;
   }

}
