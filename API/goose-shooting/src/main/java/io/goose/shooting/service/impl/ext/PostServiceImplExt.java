package io.goose.shooting.service.impl.ext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.ClientUser;
import io.goose.shooting.domain.Post;
import io.goose.shooting.domain.Role;
import io.goose.shooting.domain.details.CommentDetails;
import io.goose.shooting.domain.details.PostDetails;
import io.goose.shooting.service.IClientUserService;
import io.goose.shooting.service.IRoleService;
import io.goose.shooting.service.impl.PostServiceImpl;


@Service
public class PostServiceImplExt extends PostServiceImpl {

   @Autowired
   IClientUserService userService;

   @Autowired
   IRoleService roleService;

   @Autowired
   public CommentServiceImplExt commentService;


   public List<PostDetails> selectPostDetailsList( Post postForSearch ) {
      List<Post> resultPostList = selectPostList( postForSearch );

      // use remark to represent user role
      resultPostList.stream().forEach( resultPost -> {
         resultPost.getClientUser()
               .setRemark( getFirstRoleNameAndDescription( resultPost.getClientUserId() ) );
      } );

      List<Post> sortedPostList =
            resultPostList.stream().sorted( Comparator.comparing( Post::getCreateTime ).reversed() )
                  .collect( Collectors.toList() );

      List<PostDetails> postDetailsList = sortedPostList.stream().map( post -> {
         return getPostDetails( post );
      } ).collect( Collectors.toList() );

      return postDetailsList;
   }


   private String getFirstRoleNameAndDescription( Long userId ) {

      ClientUser user = userService.selectClientUserById( userId );

      if ( user == null ) {
         return "";
      }

      String roleIds = user.getRoleIds();

      if ( StringUtils.isEmpty( roleIds ) ) {
         return "";
      }

      String firstRoleId = roleIds.split( "," )[0];

      Role role = roleService.selectRoleById( Integer.parseInt( firstRoleId ) );

      if ( role == null ) {
         return "";
      }

      return role.getName() + ":" + role.getDescription();
   }


   private PostDetails getPostDetails( Post post ) {

      List<CommentDetails> commentDetailsList = commentService
            .selectCommentDetailsList( post.getId(), CommentServiceImplExt.FK_TABLE_ID_POST );

      PostDetails postDetails = new PostDetails();
      postDetails.setPost( post );
      postDetails.setCommentDetailsList( commentDetailsList );

      return postDetails;
   }
}
