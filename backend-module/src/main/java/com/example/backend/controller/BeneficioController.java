package com.example.backend.controller;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.entity.dto.BeneficioDto;
import com.example.ejb.entity.dto.TransferenciaDto;
import com.example.ejb.service.BeneficioEjbService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Benefícios", description = "Operações relacionadas a benefícios")
@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    @Autowired
    private BeneficioEjbService beneficioEjbService;

    @Autowired
    private ModelMapper mapper;

    private static final String ID = "/{id}";

    @Operation(summary = "Transferir benefício entre IDs")
    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferenciaDto dto) {

        beneficioEjbService.transfer(
                dto.getFromId(),
                dto.getToId(),
                dto.getAmount()
        );

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar todos os benefícios")
    @GetMapping
    public List<Beneficio> list() {
        return beneficioEjbService.findAll();
    }

    @Operation(summary = "Criar novo benefício")
    @PostMapping
    public ResponseEntity<Beneficio> create(@RequestBody BeneficioDto obj) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("").buildAndExpand(beneficioEjbService.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Operation(summary = "Atualizar benefício existente pelo ID")
    @PutMapping(value = ID)
    public ResponseEntity<Beneficio> update(@RequestBody BeneficioDto obj, @PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(beneficioEjbService.upDate(obj, id), Beneficio.class));
    }

    @Operation(summary = "Deletar benefício pelo ID")
    @DeleteMapping(value = ID)
    public ResponseEntity<Beneficio> delete(@PathVariable Integer id) {
        beneficioEjbService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
