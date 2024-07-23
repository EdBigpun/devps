package com.wheelz.api.service.auth;

import com.wheelz.api.dto.auth.AuthResponse;
import com.wheelz.api.dto.auth.LoginRequest;
import com.wheelz.api.dto.auth.RegisterRequest;
import com.wheelz.api.entity.usuario.TipoUsuario;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserDetails user = usuarioRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequest request){

        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .contraseña(passwordEncoder.encode(request.getContraseña()))
                .documento(request.getDocumento())
                .tipoUsuario(TipoUsuario.CLIENTE)
                .active(true)
                .build();

        usuarioRepository.save(usuario);

        return AuthResponse.builder()
                .token(jwtService.getToken(usuario))
                .build();
    }


}
