package com.wheelz.api.controller;


import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaSavingRequest;
import com.wheelz.api.service.reserva.tipoCobertura.TipoCoberturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipocobertura")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TipoCoberturaController {

    private final TipoCoberturaService tipoCoberturaService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCoberturas(){
        return ResponseEntity.ok(tipoCoberturaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservaPorId(@PathVariable Long id){
        return ResponseEntity.ok(tipoCoberturaService.findByTipoCoberturaId(id));
    }

    @PostMapping
    public ResponseEntity<?> saveTipoCobertura(@Valid @RequestBody TipoCoberturaSavingRequest tipoCobertura, BindingResult result){
        if (result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.ok(tipoCoberturaService.save(tipoCobertura));
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errorMessage));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTipoCobertura(@PathVariable Long id, @Valid @RequestBody TipoCoberturaSavingRequest tipoCoberturaUpdate) throws BadRequestException {
        return ResponseEntity.ok(tipoCoberturaService.update(id,tipoCoberturaUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoCobertura(@PathVariable Long id) {
        try {
            tipoCoberturaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", errorMessage));
        }
    }

}
