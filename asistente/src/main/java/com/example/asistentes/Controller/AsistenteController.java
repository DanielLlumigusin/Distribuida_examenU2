package com.example.asistentes.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.asistentes.Entity.Asistente;
import com.example.asistentes.Service.AsistenteService;
import com.example.asistentes.Utils.Validation;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/asistente")
public class AsistenteController {

    @Autowired
    private AsistenteService asistenteService;

    @PostMapping
    public ResponseEntity<?> createAsistente(@Valid @RequestBody Asistente asistente, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = Validation.getValidationErrors(result);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Asistente newPlayer = asistenteService.saveAsistente(asistente);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asistente> getAsistente(@PathVariable Long id) {
        return asistenteService.getAsistenteById(id)
                .map(asistente -> new ResponseEntity<>(asistente, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Asistente>> getAllPlayers() {
        List<Asistente> asistentes = asistenteService.getAllAsistentes();
        return new ResponseEntity<>(asistentes, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsistente(@PathVariable Long id, @Valid @RequestBody Asistente updatedAsistente, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = Validation.getValidationErrors(result);
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        try {
            Asistente asistente = asistenteService.updateAsistente(id, updatedAsistente);
            return new ResponseEntity<>(asistente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsistente(@PathVariable Long id) {
        try {
            asistenteService.deleteAsistente(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
