package com.example.lab6_prueba.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "partida")
public class Partida {
    @Id
    private Integer id;

    @Column(name = "picotazos_restantes")
    private Integer picotazosRestantes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPicotazosRestantes() {
        return picotazosRestantes;
    }

    public void setPicotazosRestantes(Integer picotazosRestantes) {
        this.picotazosRestantes = picotazosRestantes;
    }

    // TODO: Clic derecho -> Generate -> Getter and Setter
}