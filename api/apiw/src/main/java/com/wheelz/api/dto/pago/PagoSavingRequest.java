package com.wheelz.api.dto.pago;

import com.wheelz.api.entity.pago.TipoEstadoPago;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoSavingRequest {
    @NotNull//toDo
    private BigDecimal monto;
    @NotNull
    private long idReserva;
    @NotNull
    private Date fechaPago;
    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoEstadoPago tipoPago;
}
