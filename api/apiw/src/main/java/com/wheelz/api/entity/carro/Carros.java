package com.wheelz.api.entity.carro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "carro")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Carros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "placa", unique = true)
    private String placa;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoTransmision tipoTransmision;

    @Column(name = "imagenes")
    private String imagenes;

    @Column(name = "precio_dia")
    private BigDecimal precioDia;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Column(name = "año")
    private int año;

    @Column(name = "active")
    private boolean active = true;
}