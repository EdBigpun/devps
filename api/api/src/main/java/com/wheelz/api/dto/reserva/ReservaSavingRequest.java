package com.wheelz.api.dto.reserva;

import com.wheelz.api.entity.reserva.EstadoReserva;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ReservaSavingRequest {
    @NotNull(message = "El id_usuario de la reserva no puede estar vacio")
    private Long idUsuario;
    @NotNull(message = "El id_vehiculo de la reserva no puede estar vacio")
    private Long idCarro;
    @NotNull(message = "El id_tipo_cobertura de la reserva no puede estar vacio")
    private Long idTipoCobertura;
    @NotNull(message = "La fecha de inicio de la reserva no puede estar vacio")
    private Date fechaEntrega;
    @NotNull(message = "La fecha de devolución no puede estar vacio")
    private Date fechaDevolucion;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;
    @NotNull(message = "El Precio Total no puede estar vacio")
    private BigDecimal precioTotal;
}
