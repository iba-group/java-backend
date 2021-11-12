package com.ibagroup.petstore.repo;

import com.ibagroup.petstore.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepo extends JpaRepository<Pet, Long> {
}
