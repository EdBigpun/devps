package com.wheelz.api.dto.reserva.tipocobertura;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCoberturaSavingRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    @NotNull
    private BigDecimal porcentaje;
}
