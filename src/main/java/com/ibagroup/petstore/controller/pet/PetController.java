package com.ibagroup.petstore.controller.pet;

import com.ibagroup.petstore.dto.OperationMessageDto;
import com.ibagroup.petstore.dto.pet.PetDto;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/pet")
@Slf4j
public class PetController {

  private Map<Long, PetDto> pets = new HashMap<>();

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity addPet(@RequestBody PetDto body) {
    long size = pets.size();
    pets.put(size, body);
    body.setId(size);
    return ResponseEntity.ok(body);
  }

  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity updatePet(@RequestBody PetDto body) {
    pets.put(body.getId(), body);
    return ResponseEntity.ok(new OperationMessageDto("Updated successfully"));
  }

  @GetMapping(value = "/{petId}",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity getPetById(@PathVariable Long petId) {
    PetDto petDto = pets.get(petId);
    return ResponseEntity.ok(petDto);
  }

}
