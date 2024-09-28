package com.pick2cart.PickToCart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer discountPercent;
    private int quantity;
    private String brand;
    private String color;
    private int numRatings;

    @Embedded
    @ElementCollection
    private Set<Size> sizes=new HashSet<>();
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<Rating> ratings=new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Reviews> reviews=new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", discountPercent=" + discountPercent +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", numRatings=" + numRatings +
                ", sizes=" + sizes +
                ", imageUrl='" + imageUrl + '\'' +
                ", ratings=" + ratings +
                ", reviews=" + reviews +
                ", category=" + category +
                ", createdAt=" + createdAt +
                '}';
    }
}
