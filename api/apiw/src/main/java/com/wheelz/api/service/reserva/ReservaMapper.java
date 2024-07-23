package com.wheelz.api.service.reserva;


import com.wheelz.api.dto.reserva.ReservaResponse;
import com.wheelz.api.dto.reserva.ReservaSavingRequest;
import com.wheelz.api.entity.carro.Carros;
import com.wheelz.api.entity.reserva.Reserva;
import com.wheelz.api.entity.reserva.TipoCobertura;
import com.wheelz.api.entity.usuario.Usuario;
import com.wheelz.api.exception.RequestException;
import com.wheelz.api.service.carros.CarrosService;
import com.wheelz.api.service.reserva.tipoCobertura.TipoCoberturaService;
import com.wheelz.api.service.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaMapper {

    private UsuarioService usuarioService;
    private CarrosService carrosService;
    private TipoCoberturaService tipoCoberturaService;
    public ReservaResponse toReservaResponse(Reserva reserva) {
        if (reserva == null){
            throw new RequestException("Reserva no puede ser nulo!");
        }
        return ReservaResponse.builder()
                .id(reserva.getId())
                .idUsuario(reserva.getUsuario().getId())
                .idcarro(reserva.getCarro().getId())
                .fechaEntrega(reserva.getFechaEntrega())
                .fechaDevolucion(reserva.getFechaDevolucion())
                .idTipoCobertura(reserva.getTipoCobertura().getId())
                .estadoReserva(reserva.getEstadoReserva())
                .precioTotal(reserva.getPrecioTotal())
                .build();
    }

    public Reserva reservaRequestToPost(ReservaSavingRequest reserva){
        if (reserva == null){
            throw new RequestException("Reserva no puede ser nulo!!!");
        }

        Usuario usuario = usuarioService.getUsuarioById(reserva.getIdUsuario());
        Carros carro = carrosService.getCarroById(reserva.getIdCarro());
        TipoCobertura tipoCobertura = tipoCoberturaService.getCoberturaById(reserva.getIdTipoCobertura());

        return Reserva.builder()
                .usuario(usuario)
                .carro(carro)
                .tipoCobertura(tipoCobertura)
                .fechaEntrega(reserva.getFechaEntrega())
                .fechaDevolucion(reserva.getFechaDevolucion())
                .estadoReserva(reserva.getEstadoReserva())
                .build();
    }
}
