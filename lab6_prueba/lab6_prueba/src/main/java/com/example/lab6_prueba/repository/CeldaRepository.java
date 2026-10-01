package com.example.lab6_prueba.repository;
import com.example.lab6_prueba.entity.Celda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CeldaRepository extends JpaRepository<Celda, Integer> {
    // Esto asegura que la base de datos nos devuelva el tablero en perfecto orden
    List<Celda> findAllByOrderByFilaAscColumnaAsc();

    // Nos permite encontrar dónde está la cacatúa actualmente
    Celda findByTieneCacatuaTrue();
    
    // Nos permite encontrar la celda a la que queremos ir (usando su fila y columna)
    Celda findByFilaAndColumna(Integer fila, Integer columna);
}