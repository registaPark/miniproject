package com.hanghae99.dog.image.dto;

import com.hanghae99.dog.image.entity.Image;
import lombok.Getter;

@Getter
public class ImageResponseDto {
    private String imageUrl;

    public ImageResponseDto(Image image) {
        this.imageUrl = image.getUrl();
    }
}
