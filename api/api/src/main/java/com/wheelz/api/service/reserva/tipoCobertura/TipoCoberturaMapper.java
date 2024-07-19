package com.wheelz.api.service.reserva.tipoCobertura;

import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaResponse;
import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaSavingRequest;
import com.wheelz.api.entity.reserva.TipoCobertura;
import com.wheelz.api.exception.RequestException;
import org.springframework.stereotype.Service;

@Service
public class TipoCoberturaMapper {

    public TipoCoberturaResponse toTipoCoberturaResponse(TipoCobertura tipoCobertura){
        if (tipoCobertura == null){
            throw new RequestException("El Tipo de Cobertura no puede ser nulo!");
        }
        return TipoCoberturaResponse.builder()
                .id(tipoCobertura.getId())
                .nombre(tipoCobertura.getNombre())
                .porcentaje(tipoCobertura.getPorcentaje())
                .build();
    }

    public TipoCobertura tipoCoberturaRequestToPost(TipoCoberturaSavingRequest tipoCoberturaSavingRequest){
        if (tipoCoberturaSavingRequest == null){
            throw new RequestException("El Tipo de Cobertura no puede ser nulo!");
        }
        return TipoCobertura.builder()
                .nombre(tipoCoberturaSavingRequest.getNombre())
                .porcentaje(tipoCoberturaSavingRequest.getPorcentaje())
                .build();
    }
}
