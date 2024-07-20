package com.wheelz.api.entity.pago;

import com.wheelz.api.entity.reserva.Reserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "pago")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    private Reserva reserva;

    @Column(name = "monto")
    private BigDecimal monto;

    @Column(name = "fecha_pago")
    private Date fechaPago;

    @Enumerated(EnumType.STRING)
    private TipoEstadoPago tipoPago;
}
