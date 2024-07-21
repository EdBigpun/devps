package com.wheelz.api.dto.error;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorDTO {
    private String code;
    private String message;
}
