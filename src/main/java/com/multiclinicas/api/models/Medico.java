package com.multiclinicas.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
// Garante que não haja médicos com CRM duplicado na mesma clínica
@Table(name = "medicos", uniqueConstraints = @UniqueConstraint(columnNames = { "clinic_id", "crm" }))
public class Medico extends BaseUsuario {

    private String crm;

    private Boolean ativo = true;

    @Column(nullable = false)
    private Integer duracaoConsulta = 30;

    @ManyToMany
    @JoinTable(name = "medico_especialidade")
    private Set<Especialidade> especialidades = new HashSet<>();
}