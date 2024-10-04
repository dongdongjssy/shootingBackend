package io.goose.shooting.domain.details;

import io.goose.shooting.domain.Comment;
import io.goose.shooting.domain.CommentFeedback;

import java.util.List;


public class JudgeDetails {

   private Comment comment;
   private List<CommentFeedback> commentFeedbackList;


   public void setComment( Comment comment ) {
      this.comment = comment;
   }


   public Comment getComment() {
      return comment;
   }


   public List<CommentFeedback> getCommentFeedbackList() {
      return commentFeedbackList;
   }


   public void setCommentFeedbackList( List<CommentFeedback> commentFeedbackList ) {
      this.commentFeedbackList = commentFeedbackList;
   }
}
