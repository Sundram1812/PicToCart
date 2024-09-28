package com.pick2cart.PickToCart.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pick2cart.PickToCart.entity.Category;
import com.pick2cart.PickToCart.entity.Rating;
import com.pick2cart.PickToCart.entity.Reviews;
import com.pick2cart.PickToCart.entity.Size;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductDto {
    private String title;
    private String description;
    private Integer price;
    private Integer discountedPrice;
    private Integer discountPercent;
    private int quantity;
    private String brand;
    private String color;
    private int numRatings;
    private Set<Size> sizes=new HashSet<>();
    private String imageUrl;
    private List<Rating> ratings=new ArrayList<>();
    private List<Reviews> reviews=new ArrayList<>();
    private Category category;
    private LocalDateTime createdAt;

//    mens/clothing/mens_shirt
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
}
