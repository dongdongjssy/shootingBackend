package io.goose.shooting.domain.details;

import java.util.List;

import io.goose.shooting.domain.Recommend;


public class RecommendDetails {

   private Recommend recommend;


   public Recommend getRecommend() {
      return recommend;
   }


   public void setRecommend( Recommend recommend ) {
      this.recommend = recommend;
   }


   private List<CommentDetails> commentDetailsList;


   public List<CommentDetails> getCommentDetailsList() {
      return commentDetailsList;
   }


   public void setCommentDetailsList( List<CommentDetails> commentDetailsList ) {
      this.commentDetailsList = commentDetailsList;
   }

}
