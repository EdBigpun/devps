package com.wheelz.api.dto.carro;

import com.wheelz.api.entity.carro.Categoria;
import com.wheelz.api.entity.carro.TipoTransmision;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrosSavingRequestDTO {

    @NotBlank(message = "La Marca es obligatoria.")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio.")
    private String modelo;

    @NotBlank(message = "La placa es obligatorio.")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "La placa debe tener el formato de tres letras mayúsculas seguidas de tres números.")
    private String placa;

    @NotNull(message = "La categoría es obligatoria.")
    private Categoria categoria;

    @NotNull(message = "El tipo de transmisión es obligatorio.")
    private TipoTransmision tipoTransmision;

    @NotBlank(message = "Las imágenes son obligatorias.")
    private String imagenes;

    @NotNull(message = "El precio por día es obligatorio.")
    @Positive(message = "El precio por día debe de ser un valor positivo.")
    @Min(value = 100000, message = "El precio por día debe de ser como mínimo $ 100,000.")
    private BigDecimal precioDia;

    @NotNull(message = "La disponibilidad es obligatoria.")
    private Boolean disponibilidad;

    @NotNull(message = "El año es obligatorio.")
    @Min(value = 1886, message = "El año debe ser mayor o igual a 1886.") // Año del primer automóvil
    private int año;
}
