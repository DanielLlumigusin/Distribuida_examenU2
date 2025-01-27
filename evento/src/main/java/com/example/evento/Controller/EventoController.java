package com.example.evento.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.evento.Entity.Asistente;
import com.example.evento.Entity.Evento;
import com.example.evento.Entity.EventoAsistente;
import com.example.evento.Service.EventoService;
import com.example.evento.Utils.Validation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("api/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<?> createEvento(@Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = Validation.getValidationErrors(result);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Evento newEvento = eventoService.saveEvento(evento);
        return new ResponseEntity<>(newEvento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable Long id) {
        Optional<Evento> evento = eventoService.getEventoById(id);
        return evento.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Evento>> getAllEventos() {
        List<Evento> eventos = eventoService.getAllEventos();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvento(@PathVariable Long id, @Valid @RequestBody Evento evento, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = Validation.getValidationErrors(result);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            Evento updatedEvento = eventoService.updateEvento(id, evento);
            return new ResponseEntity<>(updatedEvento, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable Long id) {
        try {
            eventoService.deleteEvento(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{eventoId}/asistentes/{asistenteId}")
    public ResponseEntity<?> addAsistenteToEvento(
            @PathVariable Long eventoId, @PathVariable Long asistenteId) {
        try {
            EventoAsistente eventoAsistente = eventoService.addAsistenteToEvento(eventoId, asistenteId);
            return new ResponseEntity<>(eventoAsistente, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{eventoId}/asistentes")
    public ResponseEntity<List<Asistente>> getAsistenteInEvento(@PathVariable Long eventoId) {
        List<Asistente> asistentes = eventoService.getAsistentesInEvento(eventoId);  
        return new ResponseEntity<>(asistentes, HttpStatus.OK);
    }

    @DeleteMapping("/{eventoId}/asistentes/{asistenteId}")
    public ResponseEntity<?> removeAsistenteFromEvento(
            @PathVariable Long eventoId, @PathVariable Long asistenteId) {
        try {
            eventoService.removeAsistenteFromEvento(eventoId, asistenteId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/asistente/{asistenteId}/eventos")
    public ResponseEntity<List<Evento>> getEventosForAsistente(@PathVariable Long asistenteId) {
        List<Evento> eventos = eventoService.getEventosForAsistente(asistenteId);
        if (eventos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }
}
