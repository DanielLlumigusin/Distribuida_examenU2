package com.example.evento.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.evento.Client.AsistenteFeignClient;
import com.example.evento.Entity.Asistente;
import com.example.evento.Entity.Evento;
import com.example.evento.Entity.EventoAsistente;
import com.example.evento.Repository.EventoAsistenteRepository;
import com.example.evento.Repository.EventoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoAsistenteRepository eventoAsistenteRepository;

    @Autowired
    private AsistenteFeignClient asistenteFeignClient;

    public Evento saveEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Optional<Evento> getEventoById(Long id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> getAllEventos() {
        return eventoRepository.findAll();
    }

    public Evento updateEvento(Long id, Evento updatedEvento) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento not found"));

        evento.setNombre(updatedEvento.getNombre());
        return eventoRepository.save(evento);
    }

    public void deleteEvento(Long id) {
        if (!eventoRepository.existsById(id)) {
            throw new RuntimeException("Evento not found");
        }
        eventoRepository.deleteById(id);
    }

    public EventoAsistente addAsistenteToEvento(Long eventoId, Long asistenteId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento not found"));

        asistenteFeignClient.getAsistente(asistenteId);

        Optional<EventoAsistente> existingAsistente = eventoAsistenteRepository.findByEventoIdAndAsistenteId(eventoId, asistenteId);
        if (existingAsistente.isPresent()) {
            throw new RuntimeException("Asistente is already in the evento");
        }

        EventoAsistente eventoAsistente = new EventoAsistente();
        eventoAsistente.setEvento(evento);
        eventoAsistente.setAsistenteId(asistenteId);
        return eventoAsistenteRepository.save(eventoAsistente);
    }

    public List<Asistente> getAsistentesInEvento(Long eventoId) {
        List<EventoAsistente> eventoAsistentes = eventoAsistenteRepository.findByEventoId(eventoId);

        List<Asistente> asistentes = new ArrayList<>();
        for (EventoAsistente eventoAsistente : eventoAsistentes) {
            Long asistenteId = eventoAsistente.getAsistenteId();
            Asistente asistente = asistenteFeignClient.getAsistente(asistenteId).getBody();
            if (asistente != null) {
                asistentes.add(asistente);
            }
        }
        return asistentes;
    }

    public void removeAsistenteFromEvento(Long eventoId, Long asistenteId) {
        EventoAsistente eventoAsistente = eventoAsistenteRepository.findByEventoIdAndAsistenteId(eventoId, asistenteId)
                .orElseThrow(() -> new RuntimeException("Asistete not found in evento"));

        eventoAsistenteRepository.delete(eventoAsistente);
    }

    public List<Evento> getEventosForAsistente(Long asistenteId) {
        List<EventoAsistente> eventoAsistentes = eventoAsistenteRepository.findByAsistenteId(asistenteId);
        List<Evento> eventos = new ArrayList<>();

        for (EventoAsistente eventoAsistente : eventoAsistentes) {
            eventos.add(eventoAsistente.getEvento());
        }

        return eventos;
    }
}
