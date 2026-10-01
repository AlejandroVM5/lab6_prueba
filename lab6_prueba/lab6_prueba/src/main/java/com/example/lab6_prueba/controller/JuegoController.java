package com.example.lab6_prueba.controller;

import com.example.lab6_prueba.dto.TableroDto;
import com.example.lab6_prueba.entity.Celda;
import com.example.lab6_prueba.entity.Partida;
import com.example.lab6_prueba.repository.CeldaRepository;
import com.example.lab6_prueba.repository.PartidaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cacatua_sulfurea")
public class JuegoController {

    private final CeldaRepository celdaRepository;
    private final PartidaRepository partidaRepository;

    public JuegoController(CeldaRepository celdaRepository, PartidaRepository partidaRepository) {
        this.celdaRepository = celdaRepository;
        this.partidaRepository = partidaRepository;
    }

    // Ruta principal (http://localhost:8080/cacatua_sulfurea)
    @GetMapping
    public String mostrarJuego(Model model) {
        
        // 1. Obtenemos los picotazos actuales
        Partida partida = partidaRepository.findById(1).orElse(null);
        int picotazos = (partida != null) ? partida.getPicotazosRestantes() : 45;

        // 2. Obtenemos todas las celdas ordenadas (Gracias a nuestro Query Method)
        List<Celda> celdas = celdaRepository.findAllByOrderByFilaAscColumnaAsc();

        // 3. Transformamos la lista plana en una Matriz (Lista de Listas)
        List<List<Celda>> matriz = new ArrayList<>();
        List<Celda> filaActual = new ArrayList<>();
        int filaNum = 1;

        for (Celda c : celdas) {
            if (c.getFila() != filaNum) {
                matriz.add(filaActual);
                filaActual = new ArrayList<>();
                filaNum = c.getFila();
            }
            filaActual.add(c);
        }
        if (!filaActual.isEmpty()) {
            matriz.add(filaActual);
        }

        // 4. ¡USO DEL DTO! Empaquetamos todo y lo enviamos a la vista
        TableroDto dto = new TableroDto(picotazos, matriz);
        model.addAttribute("dto", dto);

        return "index";
    }

    @GetMapping("/mover")
    public String moverCacatua(@org.springframework.web.bind.annotation.RequestParam("dir") String dir) {
        
        // 1. Verificamos si aún tenemos energía
        Partida partida = partidaRepository.findById(1).orElse(null);
        if (partida == null || partida.getPicotazosRestantes() <= 0) {
            return "redirect:/cacatua_sulfurea"; // Si no hay energía, ignoramos el click
        }

        // 2. Buscamos dónde está la cacatúa actualmente
        Celda actual = celdaRepository.findByTieneCacatuaTrue();
        if (actual == null) return "redirect:/cacatua_sulfurea";

        // 3. Calculamos a dónde se quiere mover matemáticamente
        int targetFila = actual.getFila();
        int targetColumna = actual.getColumna();

        switch (dir) {
            case "U": targetFila--; break; // Arriba = restar fila
            case "D": targetFila++; break; // Abajo = sumar fila
            case "L": targetColumna--; break; // Izquierda = restar columna
            case "R": targetColumna++; break; // Derecha = sumar columna
        }

        // 4. Verificamos si la celda destino existe en la matriz (No salirse de los bordes)
        Celda destino = celdaRepository.findByFilaAndColumna(targetFila, targetColumna);
        if (destino != null) {
            
            // Consumimos 1 de energía siempre que el movimiento sea válido
            partida.setPicotazosRestantes(partida.getPicotazosRestantes() - 1);
            partidaRepository.save(partida);

            if (destino.getNivelMadera() > 0) {
                // REGLA: Si hay madera, PICOTEAR (Baja el nivel de madera en 1, pero no se mueve)
                destino.setNivelMadera(destino.getNivelMadera() - 1);
                celdaRepository.save(destino);
            } else {
                // REGLA: Si no hay madera (0), MOVERSE
                actual.setTieneCacatua(false); // Desaparece de la celda actual
                destino.setTieneCacatua(true); // Aparece en la celda nueva
                celdaRepository.save(actual);
                celdaRepository.save(destino);
            }
        }

        // Redireccionamos a la pantalla principal para que los Fragmentos redibujen todo
        return "redirect:/cacatua_sulfurea";
    }

    @GetMapping("/reiniciar")
    public String reiniciarJuego() {
        // 1. Restauramos la energía a 45
        Partida partida = partidaRepository.findById(1).orElse(null);
        if (partida != null) {
            partida.setPicotazosRestantes(45);
            partidaRepository.save(partida);
        }

        // 2. Restauramos todas las celdas a su configuración inicial
        List<Celda> celdas = celdaRepository.findAll();
        for (Celda c : celdas) {
            c.setTieneCacatua(false);
            c.setTieneGirasol(false);
            
            int f = c.getFila();
            int col = c.getColumna();
            
            if (f == 1) { c.setNivelMadera(0); if (col == 2) c.setTieneCacatua(true); }
            else if (f == 2) { c.setNivelMadera(3); }
            else if (f == 3) { if (col == 2 || col == 3) c.setNivelMadera(2); else c.setNivelMadera(3); }
            else if (f == 4) { if (col == 2 || col == 3) c.setNivelMadera(3); else c.setNivelMadera(2); }
            else if (f == 5) { if (col == 3) c.setNivelMadera(2); else c.setNivelMadera(3); }
            else if (f == 6) { 
                if (col == 3) c.setNivelMadera(2); 
                else if (col == 5) { c.setNivelMadera(0); c.setTieneGirasol(true); }
                else c.setNivelMadera(3); 
            }
        }
        celdaRepository.saveAll(celdas);

        return "redirect:/cacatua_sulfurea";
    }
}
