package com.wheelz.api.service.usuario;

import com.wheelz.api.dto.usuario.UsuarioContraseñaResponse;
import com.wheelz.api.dto.usuario.UsuarioResponse;
import com.wheelz.api.dto.usuario.UsuarioSavingRequest;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.exception.RequestException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioMapper {
    public UsuarioResponse toUsuarioResponse(Usuario usuario){
        if(usuario == null){
            throw new RequestException("Usuario no puede ser nulo!");
        }
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .documento(usuario.getDocumento())
                .tipoUsuario(usuario.getTipoUsuario())
                .active(usuario.isActive())
                .build();
    }

    public UsuarioContraseñaResponse toUsuarioConContrasenaResponse(Usuario usuario) {
        if (usuario == null) {
            throw new RequestException("Usuario no puede ser nulo!");
        }
        return UsuarioContraseñaResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .contraseña(usuario.getContraseña())
                .documento(usuario.getDocumento())
                .tipoUsuario(usuario.getTipoUsuario())
                .active(usuario.isActive())
                .build();
    }

    public Usuario usuarioRequestToPost(UsuarioSavingRequest usuario){
        if (usuario == null){
            throw new RequestException("Usuario no puede ser nulo!!!");
        }
        return Usuario.builder()
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .contraseña(usuario.getContraseña())
                .documento(usuario.getDocumento())
                .tipoUsuario(usuario.getTipoUsuario())
                .build();
    }
}
