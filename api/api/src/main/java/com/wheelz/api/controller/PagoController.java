package com.wheelz.api.controller;

import com.wheelz.api.dto.pago.PagoSavingRequest;
import com.wheelz.api.entity.usuario.TipoUsuario;
import com.wheelz.api.service.pagos.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pago")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPagos() {
        return ResponseEntity.ok(pagoService.findByAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getPagosPorId(@PathVariable Long id){
        return ResponseEntity.ok(pagoService.findByPagoId(id));
    }

    @PostMapping
    public ResponseEntity<?> savePago(@Valid @RequestBody PagoSavingRequest pago, BindingResult result){
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.ok(pagoService.save(pago));
        }catch (IllegalArgumentException e){
            String errorMessage = e.getMessage();
            if(errorMessage.contains("Tipo de Pago Invalidado")){
                String acceptedValues = Arrays.stream(TipoUsuario.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", "));
                errorMessage = String.format("Valor invalido para tipo de usuario. Los valores aceptados son: [%s]", acceptedValues);
            }
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
