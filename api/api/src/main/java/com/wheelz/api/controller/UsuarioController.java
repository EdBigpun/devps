package com.wheelz.api.controller;

import com.wheelz.api.dto.usuario.LoginRequestDTO;
import com.wheelz.api.dto.usuario.UsuarioResponse;
import com.wheelz.api.dto.usuario.UsuarioSavingRequest;
import com.wheelz.api.dto.usuario.UsuarioUpdateRequest;
import com.wheelz.api.entity.usuario.TipoUsuario;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.service.usuario.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/usuario")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@Valid @RequestBody LoginRequestDTO loginRequestDTO){
        Usuario authenticatedUser = usuarioService.login(loginRequestDTO);
        if(authenticatedUser != null){
            UsuarioResponse usuarioResponse = new UsuarioResponse();
            usuarioResponse.setId(Long.valueOf(authenticatedUser.getId()));
            usuarioResponse.setNombre(authenticatedUser.getNombre());
            usuarioResponse.setApellido(authenticatedUser.getApellido());
            usuarioResponse.setEmail(authenticatedUser.getEmail());
            return new ResponseEntity<>(usuarioResponse, HttpStatus.OK);
        }
        throw new RuntimeException("Error en Login");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findByAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsuarioPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.findByUsuarioId(id));
    }
    @PostMapping
    public ResponseEntity<?> saveUsuario(@Valid @RequestBody UsuarioSavingRequest usuario, BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.ok(usuarioService.save(usuario));
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Tipo de usuario invalido")) {
                String acceptedValues = Arrays.stream(TipoUsuario.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                errorMessage = String.format("Valor invalido para tipo de usuario. Los valores aceptados son: [%s]", acceptedValues);
            }
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errorMessage));
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateRequest usuarioUpdate) throws BadRequestException{
        return ResponseEntity.ok(usuarioService.update(id,usuarioUpdate));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id){
        usuarioService.desactivar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
