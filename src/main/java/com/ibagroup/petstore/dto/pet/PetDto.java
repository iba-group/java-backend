package com.ibagroup.petstore.dto.pet;

import com.ibagroup.petstore.dto.category.CategoryDto;
import com.ibagroup.petstore.dto.tag.TagDto;
import com.ibagroup.petstore.model.pet.PetStatus;
import java.util.List;
import lombok.Data;

@Data
public class PetDto {

  private Long id;
  private CategoryDto category;
  private String name;
  private List<String> photoUrls;
  private List<TagDto> tags;
  private PetStatus status;

}
