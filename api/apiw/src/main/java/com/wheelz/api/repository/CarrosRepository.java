package com.wheelz.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wheelz.api.entity.carro.Carros;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CarrosRepository extends JpaRepository<Carros, Long> {
    List<Carros> findByActiveTrue();
    List<Carros> findByActiveTrueAndDisponibilidadTrue();
    Optional<Carros> findByPlaca(String placa);
    @Query(value = "CALL buscarPorMarca(:marcaBuscada)", nativeQuery = true)
    List<Carros> buscarPorMarca(@Param("marcaBuscada") String marcaBuscada);
    @Query(value = "CALL buscarPorModelo(:modeloBuscado)", nativeQuery = true)
    List<Carros> buscarPorModelo(@Param("modeloBuscado") String modeloBuscado);

}
