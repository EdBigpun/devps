package com.wheelz.api.dto.carro;

import com.wheelz.api.entity.carro.Categoria;
import com.wheelz.api.entity.carro.TipoTransmision;
import lombok.*;

import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrosResponseDTO {

    private Long id;
    private String marca;
    private String modelo;
    private String placa;
    private Categoria categoria;
    private TipoTransmision tipoTransmision;
    private String imagenes;
    private BigDecimal precioDia;
    private Boolean disponibilidad;
    private int año;
    private boolean active;
}
