package com.example.evento.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Table(name = "evento_asistente")
@Entity
public class EventoAsistente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    @JsonBackReference
    private Evento evento;

    private Long asistenteId;

    private LocalDate create_Date = LocalDate.now();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Long getAsistenteId() {
		return asistenteId;
	}

	public void setAsistenteId(Long asistenteId) {
		this.asistenteId = asistenteId;
	}

	public LocalDate getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(LocalDate create_Date) {
		this.create_Date = create_Date;
	}
}
