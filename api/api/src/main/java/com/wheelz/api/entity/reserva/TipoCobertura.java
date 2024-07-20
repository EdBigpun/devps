package com.wheelz.api.entity.reserva;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tipoCobertura")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TipoCobertura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "nombre", unique = true)
    private String nombre;
    @Column(name = "porcentaje")
    private BigDecimal porcentaje;
}
