package com.ootbatch.domain.clothesImage.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.clothes.entity.Clothes;
import com.ootbatch.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "clothes_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClothesImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clothes_id", nullable = false)
    private Clothes clothes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    private Boolean isMain;
}
