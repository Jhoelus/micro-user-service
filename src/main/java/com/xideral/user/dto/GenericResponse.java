package com.xideral.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GenericResponse<T> {

    private String message;
    private T data;
    private List<String> error;
    private String code;

}
