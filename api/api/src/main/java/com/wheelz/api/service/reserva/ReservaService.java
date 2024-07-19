package com.wheelz.api.service.reserva;

import com.wheelz.api.dto.reserva.ReservaResponse;
import com.wheelz.api.dto.reserva.ReservaSavingRequest;
import com.wheelz.api.dto.reserva.ReservaUpdateRequest;
import com.wheelz.api.dto.reserva.tipocobertura.TipoCoberturaSavingRequest;
import com.wheelz.api.entity.reserva.Reserva;
import com.wheelz.api.entity.reserva.TipoCobertura;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.CarrosRepository;
import com.wheelz.api.repository.ReservaRepository;
import com.wheelz.api.repository.TipoCoberturaRepository;
import com.wheelz.api.repository.UsuarioRepository;
import com.wheelz.api.service.reserva.tipoCobertura.TipoCoberturaMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarrosRepository carrosRepository;

    @Lazy
    private final ReservaMapper reservaMapper;

    public List<ReservaResponse> findByAll() {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservaRepository.findAll().stream()
                .map(reservaMapper::toReservaResponse).toList();
    }

    public ReservaResponse findByReservaId(Long id) {
        if (id == null|| id == 0){
            throw new RequestException("Id invalido!!!");
        }
        Reserva reserva = reservaRepository.findById(id).orElseThrow(() -> new RequestException("Reserva no encontrada.!"));
        return reservaMapper.toReservaResponse(reserva);
    }

    public ReservaResponse save(ReservaSavingRequest reservaSavingRequest) {

        Reserva reserva = reservaMapper.reservaRequestToPost(reservaSavingRequest);

        try {
            return reservaMapper.toReservaResponse(reservaRepository.save(reserva));
        } catch (Exception e) {
            throw new RequestException("Error al guardar la Reserva: " + e.getMessage());
        }
    }



    public ReservaResponse update(Long id, ReservaUpdateRequest reservaUpdate) throws BadRequestException {
        if (id == null || id <= 0){
            throw new BadRequestException("ID de usuario invalido");
        }
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        if (reservaUpdate.getFechaEntrega() != null){
            reserva.setFechaEntrega(reservaUpdate.getFechaEntrega());
        }
        if (reservaUpdate.getFechaDevolucion() != null){
            reserva.setFechaDevolucion(reservaUpdate.getFechaDevolucion());
        }
        if (reservaUpdate.getIdTipoCobertura() != null){
            reserva.setTipoCobertura(reservaUpdate.getIdTipoCobertura());
        }
        if (reservaUpdate.getEstadoReserva() != null){
            reserva.setEstadoReserva(reservaUpdate.getEstadoReserva());
        }
        if (reservaUpdate.getPrecioTotal() != null){
            reserva.setPrecioTotal(reservaUpdate.getPrecioTotal());
        }
        try {
            Reserva updatedReserva = reservaRepository.save(reserva);
            return reservaMapper.toReservaResponse(updatedReserva);
        } catch (Exception e) {
            throw new RequestException("Error al actualizar la Reserva con ID: " + id + ". Detalles: " + e.getMessage());
        }
    }
}
