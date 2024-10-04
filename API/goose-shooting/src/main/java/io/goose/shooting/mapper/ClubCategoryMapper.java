package io.goose.shooting.mapper;

import io.goose.shooting.domain.ClubCategory;

import java.util.List;

public interface ClubCategoryMapper {
   public List<ClubCategory> selectClubCategory(String type);
}
