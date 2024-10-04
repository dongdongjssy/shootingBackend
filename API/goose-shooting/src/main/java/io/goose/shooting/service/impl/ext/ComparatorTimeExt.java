package io.goose.shooting.service.impl.ext;

import io.goose.shooting.domain.ClubActivity;
import io.goose.shooting.domain.ClubPost;

import java.util.Comparator;

/**
 * @Author Bian
 * @Date 2021/1/15 14:24
 * @Version 1.0
 */
public class ComparatorTimeExt implements Comparator {

    @Override
    public int compare(Object arg0, Object arg1) {
        ClubPost cb ;
        ClubActivity rd ;
        ClubPost cb1 ;
        ClubActivity rd1 ;
        if(arg0 instanceof ClubPost){
            cb=(ClubPost)arg0;
            if(arg1 instanceof ClubActivity){
                rd=(ClubActivity)arg1;
                return cb.getCreateTime().compareTo(rd.getCreateTime());
            }else{
                cb1=(ClubPost)arg1;
                return cb.getCreateTime().compareTo(cb1.getCreateTime());
            }
        }else{
            rd1=(ClubActivity)arg0;
            if(arg1 instanceof ClubActivity){
                rd=(ClubActivity)arg1;
                return rd1.getCreateTime().compareTo(rd.getCreateTime());
            }else{
                cb=(ClubPost)arg1;
                return rd1.getCreateTime().compareTo(cb.getCreateTime());
            }
        }
    }
}
