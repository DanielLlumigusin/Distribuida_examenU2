package com.example.evento.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.evento.Entity.EventoAsistente;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventoAsistenteRepository extends JpaRepository<EventoAsistente, Long> {
    List<EventoAsistente> findByEventoId(Long teamId);
    List<EventoAsistente> findByAsistenteId(Long playerId);
    Optional<EventoAsistente> findByEventoIdAndAsistenteId(Long eventoId, Long asistenteId);
}
