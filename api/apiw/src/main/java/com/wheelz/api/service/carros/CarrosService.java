package com.wheelz.api.service.carros;

import com.wheelz.api.dto.carro.CarrosResponseDTO;
import com.wheelz.api.dto.carro.CarrosSavingRequestDTO;
import com.wheelz.api.dto.carro.CarrosUpdateRequestDTO;
import com.wheelz.api.entity.carro.Carros;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.CarrosRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarrosService {

    private final CarrosRepository carrosRepository;

    private final CarrosMapper carrosMapper;
    public List<CarrosResponseDTO> findByAll() {
        return carrosRepository.findAll().stream()
                .map(carrosMapper::toCarrosResponseDTO).toList();
    }
    public List<CarrosResponseDTO> findActiveAndDisponible() {
        return carrosRepository.findByActiveTrueAndDisponibilidadTrue().stream()
                .map(carrosMapper::toCarrosResponseDTO)
                .collect(Collectors.toList());
    }
    public Carros getCarroById(Long id) {
        if (id == null || id == 0) {
            throw new RequestException("Id invalido!!!");
        }
        return carrosRepository.findById(id)
                .orElseThrow(() -> new RequestException("Carro no encontrado.!"));
    }
    public CarrosResponseDTO findByCarroId(Long id) {
        if (id == null|| id == 0){
            throw new RequestException("Id invalido!!!");
        }
        Carros carros = carrosRepository.findById(id).orElseThrow(() -> new RequestException("Usuario no encontrado.!"));
        return carrosMapper.toCarrosResponseDTO(carros);
    }
    public List<Carros> buscarCarrosPorMarca(String marcaBuscada) {
        return carrosRepository.buscarPorMarca(marcaBuscada);
    }
    public List<Carros> buscarCarrosPorModelo(String modeloBuscado) {
        return carrosRepository.buscarPorModelo(modeloBuscado);
    }
    public CarrosResponseDTO saveCarros(CarrosSavingRequestDTO carrosSavingRequestDTO) {
        verificarDatosRepetidos(carrosSavingRequestDTO);
        Carros carro = carrosMapper.carrosRequestToPost(carrosSavingRequestDTO);
        carro.setActive(true);

        try {
            return carrosMapper.toCarrosResponseDTO(carrosRepository.save(carro));
        } catch (Exception e) {
            throw new RequestException("Error al guardar el carro: " + e.getMessage());
        }
    }
    private void verificarDatosRepetidos(CarrosSavingRequestDTO carrosSavingRequestDTO) {
        Optional<Carros> carroOptional = carrosRepository.findByPlaca(carrosSavingRequestDTO.getPlaca());
        if (carroOptional.isPresent()) {
            throw new RequestException("Placa repetida!");
        }
    }

    public CarrosResponseDTO updateCarros(Long id, CarrosUpdateRequestDTO carrosUpdateRequestDTO) throws BadRequestException {
        if (id == null || id <= 0){
            throw new BadRequestException("ID de carro invalido");
        }
        Carros carro = carrosRepository.findById(id).orElseThrow(() -> new RuntimeException("Carro no encontrado con id: " + id));
        if (carrosUpdateRequestDTO.getPrecioDia() != null) {
            carro.setPrecioDia(carrosUpdateRequestDTO.getPrecioDia());
        }
        if (carrosUpdateRequestDTO.getDisponibilidad() != null) {
            carro.setDisponibilidad(carrosUpdateRequestDTO.getDisponibilidad());
        }
        if (carrosUpdateRequestDTO.getImagenes() != null) {
            carro.setImagenes(carrosUpdateRequestDTO.getImagenes());
        }
        carro.setActive(true);
        return carrosMapper.toCarrosResponseDTO(carrosRepository.save(carro));
    }
    public void desactivar(Long id) {
        if (id == null || id <= 0) {
            throw new RequestException("El ID es invalido o inexistente.");
        }
        Optional<Carros> carroOptional = carrosRepository.findById(id);
        if (!carroOptional.isPresent()){
            throw new RequestException("No se encontro ningun usuario con el id : " + id);
        }
        Carros carro = carroOptional.get();
        if (!carro.isActive()){
            throw new RequestException("El carro ya esta desactivado.");
        }
        carro.setActive(false);
        carrosRepository.save(carro);
    }
}





