package io.goose.shooting.service.impl.ext;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.goose.shooting.domain.Recommend;
import io.goose.shooting.domain.details.CommentDetails;
import io.goose.shooting.domain.details.DynamicSimple;
import io.goose.shooting.domain.details.RecommendDetails;
import io.goose.shooting.service.impl.RecommendServiceImpl;


@Service
public class RecommendServiceImplExt extends RecommendServiceImpl {

   @Autowired
   public CommentServiceImplExt commentService;


   public List<DynamicSimple> selectDynamicSimpleList( Recommend recommendForSearch ) {

      List<Recommend> recommendList = selectRecommendList( recommendForSearch );

      List<DynamicSimple> dynamicSimpleList = recommendList.stream().map( recommend -> {
         return getDynamicSimple( recommend );
      } ).collect( Collectors.toList() );

      // get the ontop list
      List<DynamicSimple> sortedDynamicList = sortDynamicList( dynamicSimpleList, 1 );

      // get the other list
      sortedDynamicList.addAll( sortDynamicList( dynamicSimpleList, 0 ) );

      return dynamicSimpleList;
   }


   public RecommendDetails selectRecommendDetailsById( Long id ) {

      Recommend recommend = selectRecommendByIdAssoc( id );
      RecommendDetails recommendDetails = getRecommendDetails( recommend );

      return recommendDetails;
   }


   public List<RecommendDetails> selectRecommendDetailsList( Recommend recommendForSearch ) {

      List<Recommend> recommendList = selectRecommendList( recommendForSearch );

      // get the ontop list
      List<Recommend> sortedRecommendList = sortList( recommendList, 1 );

      // get the other list
      sortedRecommendList.addAll( sortList( recommendList, 0 ) );

      List<RecommendDetails> recommendDetailsList = sortedRecommendList.stream().map( recommend -> {
         return getRecommendDetails( recommend );
      } ).collect( Collectors.toList() );

      return recommendDetailsList;
   }


   private DynamicSimple getDynamicSimple( Recommend recommend ) {

      DynamicSimple dynamicSimple = new DynamicSimple();

      dynamicSimple.setId( recommend.getId() );
      dynamicSimple.setDynamicType( ServiceUtils.DYNAMIC_TYPES[0] );
      dynamicSimple.setCategory( ServiceUtils.CATEGORIES[recommend.getCategory() - 1] );
      dynamicSimple.setTitle( recommend.getTitle() );
      dynamicSimple.setContent( ServiceUtils.trimContentRichText( recommend.getContent() ) );

      dynamicSimple.setImage1( recommend.getImage1() );
      dynamicSimple.setImage2( recommend.getImage2() );
      dynamicSimple.setImage3( recommend.getImage3() );
      dynamicSimple.setImage4( recommend.getImage4() );
      dynamicSimple.setImage5( recommend.getImage5() );
      dynamicSimple.setImage6( recommend.getImage6() );
      dynamicSimple.setImage7( recommend.getImage7() );
      dynamicSimple.setImage8( recommend.getImage8() );
      dynamicSimple.setImage9( recommend.getImage9() );
      dynamicSimple.setVideo( recommend.getVideo() );

      dynamicSimple.setLikeCount( recommend.getLikeCount() );
      dynamicSimple.setCommentCount( recommend.getCommentCount() );
      dynamicSimple.setReadCount( recommend.getReadCount() );
      dynamicSimple.setOnTop( recommend.getOnTop() );
      dynamicSimple.setRankings( recommend.getRankings() );

      Date createDateTime = recommend.getCreateTime();
      dynamicSimple.setCreateDateTime( createDateTime );
      dynamicSimple.setCreateTime( new SimpleDateFormat( "yyyy-MM-dd" ).format( createDateTime ) );

      dynamicSimple.setAdTitle( recommend.getAdTitle() );
      dynamicSimple.setAdContent( recommend.getAdContent() );
      dynamicSimple.setAdMediaUrl( recommend.getAdMediaUrl() );

      ServiceUtils.setClientUser( dynamicSimple, recommend.getClientUser() );

      return dynamicSimple;
   }


   private RecommendDetails getRecommendDetails( Recommend recommend ) {

      List<CommentDetails> commentDetailsList = commentService.selectCommentDetailsList(
            recommend.getId(), CommentServiceImplExt.FK_TABLE_ID_RECOMMEND );

      RecommendDetails recommendDetails = new RecommendDetails();
      recommendDetails.setRecommend( recommend );
      recommendDetails.setCommentDetailsList( commentDetailsList );

      return recommendDetails;
   }


   private List<Recommend> sortList( List<Recommend> recommendList, Integer onTopValue ) {
      return recommendList.stream()
            .filter( resultRecommend -> resultRecommend.getOnTop() == onTopValue )
            .sorted( Comparator.comparing( Recommend::getCreateTime ).reversed() )
            .collect( Collectors.toList() );
   }


   private List<DynamicSimple> sortDynamicList( List<DynamicSimple> dynamicSimple,
         Integer onTopValue ) {
      return dynamicSimple.stream().filter( dynamic -> dynamic.getOnTop() == onTopValue )
            .sorted( Comparator.comparing( DynamicSimple::getCreateTime ).reversed() )
            .collect( Collectors.toList() );
   }

}
