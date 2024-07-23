package com.wheelz.api.dto.carro;

import com.wheelz.api.entity.carro.Categoria;
import com.wheelz.api.entity.carro.TipoTransmision;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrosUpdateRequestDTO {
    private String marca;
    private String modelo;
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "La placa debe tener el formato de tres letras mayúsculas seguidas de tres números.")
    private String placa;
    private Categoria categoria;
    private TipoTransmision tipoTransmision;
    private String imagenes;
    @Positive(message = "El precio por día debe de ser un valor positivo.")
    @Min(value = 100000, message = "El precio por día debe de ser como mínimo $ 100,000.")
    private BigDecimal precioDia;

    private Boolean disponibilidad;

    private Boolean activo;
    private int año;

    }
