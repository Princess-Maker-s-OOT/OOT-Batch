package com.ootbatch.domain.closetimage.entity;

import com.ootbatch.common.entity.BaseEntity;
import com.ootbatch.domain.closet.entity.Closet;
import com.ootbatch.domain.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "closet_images")
public class ClosetImage extends BaseEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "closet_id", nullable = false, unique = true)
    private Closet closet;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", nullable = false, unique = true)
    private Image image;
}