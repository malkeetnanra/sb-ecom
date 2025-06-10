package com.ecommerce.ecomProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 14, message = "Must contain 3-14 letters")
    private Long productId;

    private String productName;
    private String description;
    private Integer quantity;
    private double price;
    private double specialPrice;
    private double discount;
    private String image;

    // Many products can belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Equals and hashCode based on ID
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != getEffectiveClass(o)) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public final int hashCode() {
        return getEffectiveClass(this).hashCode();
    }

    private Class<?> getEffectiveClass(Object obj) {
        return obj instanceof HibernateProxy
                ? ((HibernateProxy) obj).getHibernateLazyInitializer().getPersistentClass()
                : obj.getClass();
    }
}
