package com.xideral.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotBlank(message = "Name no valid")
    private String name;
    @NotBlank(message = "Email no valid")
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdated;
}
