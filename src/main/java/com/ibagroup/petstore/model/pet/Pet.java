package com.ibagroup.petstore.model.pet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ibagroup.petstore.model.category.Category;
import com.ibagroup.petstore.model.tag.Tag;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "photo_url")
    private String photo;

    @Enumerated(EnumType.STRING)
    private PetStatus status;

    @OneToMany(mappedBy = "petId")
    List<Tag> tags = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "pets_categories",
            joinColumns =  @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    List<Category> categories = new ArrayList<>();
}
