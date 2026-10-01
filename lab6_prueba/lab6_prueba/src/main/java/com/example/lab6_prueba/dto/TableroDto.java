package com.example.lab6_prueba.dto;
import com.example.lab6_prueba.entity.Celda;
import java.util.List;

public class TableroDto {
    private Integer picotazosRestantes;
    private List<List<Celda>> matriz; // Una lista de listas para representar Fila -> Columnas

    // Constructor
    public TableroDto(Integer picotazosRestantes, List<List<Celda>> matriz) {
        this.picotazosRestantes = picotazosRestantes;
        this.matriz = matriz;
    }

    public Integer getPicotazosRestantes() {
        return picotazosRestantes;
    }

    public void setPicotazosRestantes(Integer picotazosRestantes) {
        this.picotazosRestantes = picotazosRestantes;
    }

    public List<List<Celda>> getMatriz() {
        return matriz;
    }

    public void setMatriz(List<List<Celda>> matriz) {
        this.matriz = matriz;
    }

    // TODO: Genera los Getters y Setters de ambos atributos
}