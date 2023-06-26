package com.lcwd.electronic.store.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private String imageName;
    private String message;
    private boolean success;
    private String status;
}
