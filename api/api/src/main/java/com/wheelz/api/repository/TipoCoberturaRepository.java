package com.wheelz.api.repository;

import com.wheelz.api.entity.reserva.TipoCobertura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCoberturaRepository extends JpaRepository<TipoCobertura, Long> {
    Optional<TipoCobertura> findByNombre(String nombre);
}
