package com.wheelz.api.dto.reserva;

import com.wheelz.api.entity.reserva.EstadoReserva;
import com.wheelz.api.entity.reserva.TipoCobertura;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaUpdateRequest {
    private Date fechaEntrega;
    private Date fechaDevolucion;
    private TipoCobertura idTipoCobertura;
    private EstadoReserva estadoReserva;
    private BigDecimal precioTotal;
}
