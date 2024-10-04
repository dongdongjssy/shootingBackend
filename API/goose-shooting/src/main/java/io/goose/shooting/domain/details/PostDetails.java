package io.goose.shooting.domain.details;

import java.util.List;

import io.goose.shooting.domain.Post;


public class PostDetails {

   private Post post;


   public Post getPost() {
      return post;
   }


   public void setPost( Post post ) {
      this.post = post;
   }


   private List<CommentDetails> commentDetailsList;


   public List<CommentDetails> getCommentDetailsList() {
      return commentDetailsList;
   }


   public void setCommentDetailsList( List<CommentDetails> commentDetailsList ) {
      this.commentDetailsList = commentDetailsList;
   }

}
