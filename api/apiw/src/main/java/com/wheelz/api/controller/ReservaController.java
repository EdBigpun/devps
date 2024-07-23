package com.wheelz.api.controller;

import com.wheelz.api.dto.reserva.ReservaSavingRequest;
import com.wheelz.api.dto.reserva.ReservaUpdateRequest;
import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaSavingRequest;
import com.wheelz.api.entity.usuario.TipoUsuario;
import com.wheelz.api.service.reserva.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservaController {
    private final ReservaService reservaService;
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAllReservas(){
        return ResponseEntity.ok(reservaService.findByAll());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<?> getReservaPorId(@PathVariable Long id){
        return ResponseEntity.ok(reservaService.findByReservaId(id));
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE')")
    public ResponseEntity<?> saveReserva(@Valid @RequestBody ReservaSavingRequest reserva, BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.ok(reservaService.save(reserva));
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @Valid @RequestBody ReservaUpdateRequest reservaUpdate) throws BadRequestException {
        return ResponseEntity.ok(reservaService.update(id,reservaUpdate));
    }
}
