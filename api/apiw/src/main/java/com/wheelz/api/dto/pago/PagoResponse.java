package com.wheelz.api.dto.pago;

import com.wheelz.api.entity.pago.TipoEstadoPago;
import com.wheelz.api.entity.reserva.Reserva;
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
public class PagoResponse {
    private long id;
    private long idReserva;
    private BigDecimal monto;
    private Date fechaPago;
    private TipoEstadoPago tipoPago;
}
