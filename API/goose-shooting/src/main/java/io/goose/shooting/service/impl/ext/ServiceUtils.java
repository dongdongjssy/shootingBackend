package io.goose.shooting.service.impl.ext;

import org.jsoup.Jsoup;

import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.Role;
import io.goose.shooting.domain.details.DynamicSimple;
import io.goose.shooting.domain.details.DynamicSimple.ClientUser;


public class ServiceUtils {

   public static final String[] CATEGORIES = { "公告", "资讯", "培训", "比赛" };
   public static final String[] DYNAMIC_TYPES = { "recommend", "post", "club_post" };

   private static final int MAX_SIMPLE_CONTENT_LENGTH = 100;


   public static void setClientUser( DynamicSimple dynamicSimple,
         io.goose.shooting.domain.ClientUser user ) {

      ClientUser clientUser = dynamicSimple.new ClientUser();

      clientUser.setId( user.getId() );
      clientUser.setNickname( user.getNickname() );
      clientUser.setAvatar( user.getAvatar() );
      clientUser.setRoleName( "" );
      clientUser.setRoleDesciption( "" );

      dynamicSimple.setClientUser( clientUser );
   }


   public static String trimContentRichText( String richText ) {

      String trimmedText = trimRichText( richText, MAX_SIMPLE_CONTENT_LENGTH );

      return trimmedText;
   }


   public static String trimRichText( String richText, int maxLength ) {

      if ( StringUtils.isEmpty( richText ) ) {
         return "";
      }

      String text = Jsoup.parse( richText ).text();

      if ( StringUtils.isEmpty( text ) || text.length() <= maxLength ) {
         return text;
      }

      String trimmedText = text.substring( 0, maxLength );

      return trimmedText;
   }


   public static void setRoleFullDisplay( Role role ) {
      String description = role.getDescription();
      role.setName( role.getName() + " ("
            + ( StringUtils.isEmpty( description ) ? "-" : description ) + ")" );
   }


   public static void setUserFullDisplay( io.goose.shooting.domain.ClientUser clientUser ) {
      String nickName = clientUser.getNickname();
      clientUser.setNickname( clientUser.getPhone() + " ("
            + ( StringUtils.isEmpty( nickName ) ? "-" : nickName ) + ")" );
   }
}
