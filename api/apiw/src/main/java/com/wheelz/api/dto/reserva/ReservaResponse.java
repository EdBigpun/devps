package com.wheelz.api.dto.reserva;

import com.wheelz.api.entity.reserva.EstadoReserva;
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
public class ReservaResponse {
    private long id;
    private long idUsuario;
    private long idcarro;
    private Date fechaEntrega;
    private Date fechaDevolucion;
    private long idTipoCobertura;
    private EstadoReserva estadoReserva;
    private BigDecimal precioTotal;
}
