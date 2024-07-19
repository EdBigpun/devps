package com.wheelz.api.dto.usuario;

import com.wheelz.api.entity.usuario.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String contraseña;
    private long documento;
    private TipoUsuario tipoUsuario;
    private boolean active;

}
