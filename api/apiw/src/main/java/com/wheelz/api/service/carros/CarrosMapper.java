package com.wheelz.api.service.carros;

import com.wheelz.api.dto.carro.CarrosResponseDTO;
import com.wheelz.api.dto.carro.CarrosSavingRequestDTO;
import com.wheelz.api.entity.carro.Carros;
import com.wheelz.api.exception.RequestException;
import org.springframework.stereotype.Service;

@Service
public class CarrosMapper {
    public CarrosResponseDTO toCarrosResponseDTO(Carros carro) {
        if(carro == null){
            throw new RequestException("Carro no puede ser nulo!");
        }
        return CarrosResponseDTO.builder()
                .id(carro.getId())
                .marca(carro.getMarca())
                .modelo(carro.getModelo())
                .placa(carro.getPlaca())
                .categoria(carro.getCategoria())
                .tipoTransmision(carro.getTipoTransmision())
                .imagenes(carro.getImagenes())
                .precioDia(carro.getPrecioDia())
                .disponibilidad(carro.getDisponibilidad())
                .año(carro.getAño())
                .active(carro.isActive())
                .build();
    }

    public Carros carrosRequestToPost(CarrosSavingRequestDTO carro){
        if (carro == null){
            throw new RequestException("Usuario no puede ser nulo!!!");
        }
        return Carros.builder()
                .marca(carro.getMarca())
                .modelo(carro.getModelo())
                .placa(carro.getPlaca())
                .categoria(carro.getCategoria())
                .tipoTransmision(carro.getTipoTransmision())
                .imagenes(carro.getImagenes())
                .precioDia(carro.getPrecioDia())
                .disponibilidad(carro.getDisponibilidad())
                .año(carro.getAño())
                .build();
    }
}
