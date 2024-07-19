package com.wheelz.api.dto.reserva.tipocobertura;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCoberturaResponse {
    private long id;
    private String nombre;
    private BigDecimal porcentaje;
}
