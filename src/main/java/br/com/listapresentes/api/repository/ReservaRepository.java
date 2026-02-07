package br.com.listapresentes.api.repository;

import br.com.listapresentes.api.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByItemId(Long itemId);

    List<Reserva> findAllByOrderByDataReservaDesc();
}
