package com.wheelz.api.service.pagos;

import com.wheelz.api.dto.pago.PagoResponse;
import com.wheelz.api.dto.pago.PagoSavingRequest;
import com.wheelz.api.entity.pago.Pago;
import com.wheelz.api.entity.reserva.Reserva;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagoMapper {
    @Autowired
    private  ReservaRepository reservaRepository;
    public PagoResponse toPagoResponse(Pago pago){
        if(pago == null){
            throw new RequestException("Pago no puede ser nulo");
        }
        return PagoResponse.builder()
                .id(pago.getId())
                .idReserva(pago.getId())
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .tipoPago(pago.getTipoPago())
                .build();
    }

    public Pago pagoRequestToPost(PagoSavingRequest pago){
        if(pago == null){
            throw new RequestException("Pago no puede ser nulo");
        }
        Reserva reserva = reservaRepository.findById(pago.getIdReserva())
                .orElseThrow(() -> new RequestException("Reserva no encontrada para el id: " + pago.getIdReserva()));

        return Pago.builder()
                .reserva(reserva)
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .tipoPago(pago.getTipoPago())
                .build();
    }

}
