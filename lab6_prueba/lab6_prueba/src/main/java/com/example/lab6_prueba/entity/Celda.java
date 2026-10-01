package com.example.lab6_prueba.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "celda")
public class Celda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer fila;
    private Integer columna;

    @Column(name = "nivel_madera")
    private Integer nivelMadera;

    @Column(name = "tiene_cacatua")
    private Boolean tieneCacatua;

    @Column(name = "tiene_girasol")
    private Boolean tieneGirasol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getColumna() {
        return columna;
    }

    public void setColumna(Integer columna) {
        this.columna = columna;
    }

    public Integer getNivelMadera() {
        return nivelMadera;
    }

    public void setNivelMadera(Integer nivelMadera) {
        this.nivelMadera = nivelMadera;
    }

    public Boolean getTieneCacatua() {
        return tieneCacatua;
    }

    public void setTieneCacatua(Boolean tieneCacatua) {
        this.tieneCacatua = tieneCacatua;
    }

    public Boolean getTieneGirasol() {
        return tieneGirasol;
    }

    public void setTieneGirasol(Boolean tieneGirasol) {
        this.tieneGirasol = tieneGirasol;
    }

    // TODO: Clic derecho -> Generate -> Getter and Setter
}