package io.goose.shooting.service.impl.ext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.goose.common.utils.StringUtils;
import io.goose.shooting.domain.ClubImage;
import io.goose.shooting.service.impl.ClubImageServiceImpl;


@Service
public class ClubImageServiceImplExt extends ClubImageServiceImpl {

   public List<String> selectImageListByClubId( Long clubId ) {

      List<ClubImage> clubImageList = selectClubImageAllAssoc();
      clubImageList = clubImageList.stream().filter( clubImage -> clubImage.getClubId() == clubId )
            .sorted( Comparator.comparing( ClubImage::getCreateTime ).reversed() )
            .collect( Collectors.toList() );

      List<String> pureImageList = new ArrayList<>();
      clubImageList.forEach( clubImage -> {
         pureImageList.addAll( getImages( clubImage ) );
      } );

      return pureImageList;
   }


   private List<String> getImages( ClubImage clubImage ) {

      List<String> imageList = new ArrayList<>();

      addImageToList( imageList, clubImage.getImage1() );
      addImageToList( imageList, clubImage.getImage2() );
      addImageToList( imageList, clubImage.getImage3() );
      addImageToList( imageList, clubImage.getImage4() );
      addImageToList( imageList, clubImage.getImage5() );
      addImageToList( imageList, clubImage.getImage6() );
      addImageToList( imageList, clubImage.getImage7() );
      addImageToList( imageList, clubImage.getImage8() );
      addImageToList( imageList, clubImage.getImage9() );

      return imageList;
   }


   private void addImageToList( List<String> imageList, String image ) {

      if ( StringUtils.isBlank( image ) ) {
         return;
      }
      imageList.add( image );
   }
}
