package com.example.lab4gtics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "departments")
@Getter
@Setter

public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentId;

    @Column(name = "department_name",nullable = false,length = 30)
    private String nombreDepartamento;

    @Column(name = "manager_id",nullable = true)
    private Integer managerId;

    @Column(name = "location_id",nullable = true)
    private Integer locationId;
}
