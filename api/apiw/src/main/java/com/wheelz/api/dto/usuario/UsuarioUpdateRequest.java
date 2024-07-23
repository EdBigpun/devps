package com.wheelz.api.dto.usuario;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateRequest {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres, un número, una letra mayúscula, una letra minúscula y un carácter especial"
    )
    private String contraseña;

    private long documento;
}
