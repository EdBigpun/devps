package com.wheelz.api.service.reserva;

import com.wheelz.api.dto.reserva.ReservaResponse;
import com.wheelz.api.dto.reserva.ReservaSavingRequest;
import com.wheelz.api.dto.reserva.ReservaUpdateRequest;
import com.wheelz.api.entity.carro.Carros;
import com.wheelz.api.entity.reserva.EstadoReserva;
import com.wheelz.api.entity.reserva.Reserva;
import com.wheelz.api.entity.reserva.TipoCobertura;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.repository.CarrosRepository;
import com.wheelz.api.repository.ReservaRepository;
import com.wheelz.api.repository.TipoCoberturaRepository;
import com.wheelz.api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarrosRepository carrosRepository;
    private final TipoCoberturaRepository coberturaRepository;

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
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(reservaSavingRequest.getIdUsuario());
        if (!usuarioOptional.isPresent()) {
            throw new RequestException("El usuario con el ID proporcionado no existe.");
        }
        Usuario usuario = usuarioOptional.get();
        if (!usuario.isActive()) {
            throw new RequestException("El usuario con el ID proporcionado no está activo.");
        }

        Optional<Carros> carroOptional = carrosRepository.findById(reservaSavingRequest.getIdCarro());
        if (!carroOptional.isPresent()) {
            throw new RequestException("El carro con el ID proporcionado no existe.");
        }
        Carros carro = carroOptional.get();
        if (!carro.getDisponibilidad()) {
            throw new RequestException("El carro con el ID proporcionado no está disponible.");
        }

        Optional<TipoCobertura> tipoCoberturaOptional = coberturaRepository.findById(reservaSavingRequest.getIdTipoCobertura());
        if (!tipoCoberturaOptional.isPresent()) {
            throw new RequestException("El tipo de cobertura con el ID proporcionado no existe.");
        }
        TipoCobertura tipoCobertura = tipoCoberturaOptional.get();

        //Aca se calcula el numero de dias entre la fecha de entrega y la fecha de devolucion.
        long daysBetween = ChronoUnit.DAYS.between(
                reservaSavingRequest.getFechaEntrega().toInstant(),
                reservaSavingRequest.getFechaDevolucion().toInstant()
        );
        //Validamos que al menos exista un dia de diferencia entre las dos fechas.
        if (daysBetween < 1) {
            throw new RequestException("La reserva debe ser de al menos un día.");
        }

        BigDecimal precioDia = carro.getPrecioDia();//traemos el precio del dia de carro
        BigDecimal precioTotal = precioDia.multiply(BigDecimal.valueOf(daysBetween));//Multiplicamos el precio del dia por la cantidad de dias seleccionados

        BigDecimal porcentajeCobertura = tipoCobertura.getPorcentaje();//Obtenemos el porcentaje del precio de cobertura
        BigDecimal cobertura = precioTotal.multiply(porcentajeCobertura).divide(BigDecimal.valueOf(100));//sacamos el porcentaje del precio total obtenido
        precioTotal.add(cobertura);//sumamos el porcentaje + precio total antes de cobertura

        Reserva reserva = reservaMapper.reservaRequestToPost(reservaSavingRequest);
        reserva.setPrecioTotal(precioTotal);

        if (reservaSavingRequest.getEstadoReserva() == EstadoReserva.COMPLETADO) {
            carro.setDisponibilidad(false);
            carrosRepository.save(carro); // Actualizar el carro en la base de datos
        }
        // Aca vamos a verificar el estado de la reserva y actualizar la disponibilidad del carro
        if (reservaSavingRequest.getEstadoReserva() == EstadoReserva.COMPLETADO) {
            // Verificar y activar carros si la fecha de devolución ha pasado
            LocalDate fechaDevolucion = reservaSavingRequest.getFechaDevolucion().toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
            if (fechaDevolucion.isBefore(LocalDate.now(ZoneId.of("UTC")))) {
                carro.setDisponibilidad(true);
            }
            carro.setDisponibilidad(false); // El carro estará disponible para otros después de la devolución
            carrosRepository.save(carro); // Actualizar el carro en la base de datos
        }


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

        Carros carro = reserva.getCarro();

        if (reservaUpdate.getEstadoReserva() != null){
            reserva.setEstadoReserva(reservaUpdate.getEstadoReserva());

            if (reservaUpdate.getEstadoReserva() == EstadoReserva.COMPLETADO) {
                carro.setDisponibilidad(true);
                carrosRepository.save(carro);
            }
        }

        try {
            Reserva updatedReserva = reservaRepository.save(reserva);
            return reservaMapper.toReservaResponse(updatedReserva);
        } catch (Exception e) {
            throw new RequestException("Error al actualizar la Reserva con ID: " + id + ". Detalles: " + e.getMessage());
        }
    }
}
