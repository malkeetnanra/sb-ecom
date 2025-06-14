package com.ecommerce.ecomProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Entity(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotBlank
    @Size(min = 5, message = "Please enter minimum 5 characters.")
    private String categoryName;

    @Version
    private Integer version; // For optimistic locking

    // One category can have many products
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    // Equals and hashCode based on ID
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != getEffectiveClass(o)) return false;
        Category category = (Category) o;
        return categoryId != null && categoryId.equals(category.categoryId);
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
