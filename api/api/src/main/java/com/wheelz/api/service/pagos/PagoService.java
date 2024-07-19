package com.wheelz.api.service.pagos;

import com.wheelz.api.dto.pago.PagoResponse;
import com.wheelz.api.dto.pago.PagoSavingRequest;
import com.wheelz.api.entity.pago.Pago;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    @Lazy
    private final PagoMapper pagoMapper;

    public List<PagoResponse> findByAll() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagoRepository.findAll().stream()
                .map(pagoMapper::toPagoResponse).toList();
    }

    public PagoResponse findByPagoId(Long id) {
        if (id == null || id == 0) {
            throw new RequestException("Id invalido");
        }
        Pago pago = pagoRepository.findById(id).orElseThrow(() -> new RequestException("Pago no encontrado"));
        return pagoMapper.toPagoResponse(pago);
    }

    public PagoResponse save(PagoSavingRequest pagoSavingRequest) {

        Pago pago = pagoMapper.pagoRequestToPost(pagoSavingRequest);
        try {
            return pagoMapper.toPagoResponse(pagoRepository.save(pago));
        } catch (Exception e) {
            throw new RequestException("Error al guardar el pago: " + e.getMessage());
        }
    }
}
