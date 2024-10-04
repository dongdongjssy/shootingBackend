package io.goose.shooting.domain.details;

import io.goose.shooting.domain.ClubPost;
import lombok.Data;

import java.util.List;

@Data
public class CoachDetails {
   private ClubPost clubPost;
   private List<CommentDetails> commentDetailsList;
   private Boolean isLike;
   private Boolean isCollect;
}
