package com.wheelz.api.controller;

import com.wheelz.api.dto.carro.CarrosResponseDTO;
import com.wheelz.api.dto.carro.CarrosSavingRequestDTO;
import com.wheelz.api.dto.carro.CarrosUpdateRequestDTO;
import com.wheelz.api.dto.usuario.UsuarioResponse;
import com.wheelz.api.entity.carro.Carros;
import com.wheelz.api.entity.usuario.TipoUsuario;
import com.wheelz.api.service.carros.CarrosService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carros")
@CrossOrigin("*")
public class CarrosController {

    private final CarrosService carrosService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<CarrosResponseDTO>> getAllCarros() {
        return ResponseEntity.ok(carrosService.findByAll());
    }
    @GetMapping("/active")
    public ResponseEntity<List<CarrosResponseDTO>> getActiveCarros() {
        return ResponseEntity.ok(carrosService.findActive());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<CarrosResponseDTO> getCarroById(@PathVariable Long id) {
        return ResponseEntity.ok(carrosService.findByCarroId(id));
    }
    @GetMapping("/buscarpormarca")
    public ResponseEntity<List<Carros>> buscarPorMarca(@RequestParam String marcaBuscada) {
        List<Carros> carros = carrosService.buscarCarrosPorMarca(marcaBuscada);
        return ResponseEntity.ok(carros);
    }
    @GetMapping("/buscarpormodelo")
    public ResponseEntity<List<Carros>> buscarPorModelo(@RequestParam String modeloBuscado) {
        List<Carros> carros = carrosService.buscarCarrosPorModelo(modeloBuscado);
        return ResponseEntity.ok(carros);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> saveCarros(@Validated @RequestBody CarrosSavingRequestDTO carro, BindingResult result) {
        if (result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            return ResponseEntity.ok(carrosService.saveCarros(carro));
        }catch (IllegalArgumentException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.badRequest().body(errorMessage);
            }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<CarrosResponseDTO> updateCarros(@PathVariable Long id, @Validated @RequestBody CarrosUpdateRequestDTO carrosUpdateRequestDTO) throws BadRequestException {
        return ResponseEntity.ok(carrosService.updateCarros(id,carrosUpdateRequestDTO));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> desactivarCarro(@PathVariable Long id){
        carrosService.desactivar(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}



