package com.wheelz.api.service.reserva.tipoCobertura;


import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaResponse;
import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaSavingRequest;
import com.wheelz.api.entity.reserva.TipoCobertura;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.TipoCoberturaRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoCoberturaService {

    @Autowired
    private final TipoCoberturaRepository coberturaRepository;
    @Lazy
    private final TipoCoberturaMapper tipoCoberturaMapper;

    public List<TipoCoberturaResponse> findAll() {
        List<TipoCobertura> coberturas = coberturaRepository.findAll();
        return coberturaRepository.findAll().stream()
                .map(tipoCoberturaMapper::toTipoCoberturaResponse).toList();
    }
    public TipoCobertura getCoberturaById(Long id) {
        if (id == null || id == 0) {
            throw new RequestException("Id invalido!!!");
        }
        return coberturaRepository.findById(id)
                .orElseThrow(() -> new RequestException("Tipo de Cobertura no encontrada.!"));
    }


    public TipoCoberturaResponse findByTipoCoberturaId(Long id) {
        if (id == null|| id == 0){
            throw new RequestException("Id invalido!!!");
        }
        TipoCobertura tipoCobertura = coberturaRepository.findById(id).orElseThrow(() -> new RequestException("Tipo de Cobertura no encontrada.!"));
        return tipoCoberturaMapper.toTipoCoberturaResponse(tipoCobertura);
    }

    public TipoCoberturaResponse save(TipoCoberturaSavingRequest tipoCoberturaSavingRequest) {
        verificacionDatosRepetidos(tipoCoberturaSavingRequest);

        TipoCobertura tipoCobertura = tipoCoberturaMapper.tipoCoberturaRequestToPost(tipoCoberturaSavingRequest);

        try {
            return tipoCoberturaMapper.toTipoCoberturaResponse(coberturaRepository.save(tipoCobertura));
        } catch (Exception e) {
            throw new RequestException("Error al guardar el tipo de cobertura: " + e.getMessage());
        }
    }

    public void verificacionDatosRepetidos(TipoCoberturaSavingRequest tipoCobertura){
        Optional<TipoCobertura> coberturaOptional = coberturaRepository.findByNombre(tipoCobertura.getNombre());
        if (coberturaOptional.isPresent()) {
            throw new RequestException("Nombre Repetido!");
        }
    }

    public TipoCoberturaResponse update(Long id, @Valid TipoCoberturaSavingRequest coberturaUpdate) throws BadRequestException {
        if (id == null || id <= 0){
            throw new BadRequestException("ID de Tipo Cobertura invalido");
        }
        TipoCobertura cobertura = coberturaRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Id Tipo Cobertura inexistende!!!"));
        if (coberturaUpdate.getNombre() != null){
            cobertura.setNombre(coberturaUpdate.getNombre());
        }
        if (coberturaUpdate.getPorcentaje() != null){
            cobertura.setPorcentaje(coberturaUpdate.getPorcentaje());
        }
        return tipoCoberturaMapper.toTipoCoberturaResponse(coberturaRepository.save(cobertura));
    }

    public void delete(Long id) {
        Optional<TipoCobertura> tipoCobertura = coberturaRepository.findById(id);
        if (tipoCobertura.isPresent()) {
            coberturaRepository.delete(tipoCobertura.get());
        } else {
            throw new IllegalArgumentException("TipoCobertura no encontrado con id: " + id);
        }
    }

}
