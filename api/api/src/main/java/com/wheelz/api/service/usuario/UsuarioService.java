package com.wheelz.api.service.usuario;

import com.wheelz.api.dto.usuario.LoginRequestDTO;
import com.wheelz.api.dto.usuario.UsuarioResponse;
import com.wheelz.api.dto.usuario.UsuarioSavingRequest;
import com.wheelz.api.dto.usuario.UsuarioUpdateRequest;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    @Lazy
    private final UsuarioMapper usuarioMapper;

    public Usuario login(LoginRequestDTO loginRequestDTO) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(loginRequestDTO.getEmail());
        if (optionalUsuario.isPresent()){
            Usuario usuario = optionalUsuario.get();
            if (usuario.getContraseña().equals(loginRequestDTO.getContraseña())){
                return usuario;
            }
            else {
                throw new IllegalArgumentException("Contraseña invalida!!!");
            }
        }
        throw new IllegalArgumentException("Email incorrecto!!!");
    }

    public List<UsuarioResponse> findByAll() {
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::toUsuarioResponse).toList();
    }
    public Usuario getUsuarioById(Long id) {
        if (id == null || id == 0) {
            throw new RequestException("Id invalido!!!");
        }
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RequestException("Usuario no encontrado.!"));
    }
    public UsuarioResponse findByUsuarioId(Long id) {
        if (id == null|| id == 0){
            throw new RequestException("Id invalido!!!");
        }
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RequestException("Usuario no encontrado.!"));
        return usuarioMapper.toUsuarioResponse(usuario);
    }

    public UsuarioResponse save(UsuarioSavingRequest usuarioSavingRequest) {
        verificacionDatosRepetidos(usuarioSavingRequest);

        Usuario usuario = usuarioMapper.usuarioRequestToPost(usuarioSavingRequest);
        usuario.setActive(true);

        try {
            return usuarioMapper.toUsuarioResponse(usuarioRepository.save(usuario));
        } catch (Exception e) {
            throw new RequestException("Error al guardar el usuario: " + e.getMessage());
        }
    }
    public void verificacionDatosRepetidos(UsuarioSavingRequest usuarioSavingRequest){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuarioSavingRequest.getEmail());
        if (usuarioOptional.isPresent()) {
            throw new RequestException("Email repetido!");
        }
        Optional<Usuario> usuarioByDocumento = usuarioRepository.findByDocumento(usuarioSavingRequest.getDocumento());
        if (usuarioByDocumento.isPresent()) {
            throw new RequestException("Documento repetido!");
        }
    }

    public UsuarioResponse update(Long id, UsuarioUpdateRequest usuarioUpdate) throws BadRequestException {
        if (id == null || id <= 0){
            throw new BadRequestException("ID de usuario invalido");
        }
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El ID del usuario no existe.!!!"));
        if (usuarioUpdate.getNombre() != null) {
            usuario.setNombre(usuarioUpdate.getNombre());
        }
        if (usuarioUpdate.getApellido() != null) {
            usuario.setApellido(usuarioUpdate.getApellido());
        }
        if (usuarioUpdate.getEmail() != null) {
            usuario.setEmail(usuarioUpdate.getEmail());
        }
        if (usuarioUpdate.getContraseña() != null) {
            usuario.setContraseña(usuarioUpdate.getContraseña());
        }
        if (usuarioUpdate.getDocumento() != 0) {
            String documentoString = String.valueOf(usuarioUpdate.getDocumento());
            if (documentoString.length() > 10) {
                throw new RequestException("El documento no puede tener más de 10 números");
            }
            if (documentoString.length() < 6) {
                throw new RequestException("El documento no puede tener menos de 6 números");
            }
            usuario.setDocumento(usuarioUpdate.getDocumento());
        }
        usuario.setActive(true);

        return usuarioMapper.toUsuarioResponse(usuarioRepository.save(usuario));
    }


    public void desactivar(Long id){
        if (id == null || id <= 0) {
            throw new RequestException("El ID es invalido o inexistente.");
        }
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            throw new RequestException("No se encontro ningun usuario con el id : " + id);
        }
        Usuario usuario = usuarioOptional.get();
        if (!usuario.isActive()) {
            throw new RequestException("El usuario ya está desactivado.");
        }
        usuario.setActive(false);
        usuarioRepository.save(usuario);
    }

}
