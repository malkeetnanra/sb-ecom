package com.ecommerce.ecomProject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity(name = "categories")
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank//(message = "A name is needed")//Used a validator here @NotBlank and used @Valid in the controller class
    @Size(min=5,message = "Please enter minimum 5 characters.")//removed the above messege to make this work
    private String categoryName;

}
