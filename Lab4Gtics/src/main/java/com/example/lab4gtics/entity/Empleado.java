package com.example.lab4gtics.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter

public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeId;

    @Column(name = "first_name",nullable = false,length = 20)
    private String nombre;

    @Column(name = "last_name",nullable = false,length = 25)
    private String apellido;

    @Column(name = "email",nullable = false,length = 25)
    private String email;

    @Column(name = "password",nullable = true,length = 65)
    private String password;

    @Column(name = "phone_number",nullable = true,length = 20)
    private String phone;

    @Column(name = "hire_date",nullable = false)
    private Date hireDate;

    @Column(name = "job_id",nullable = false)
    private Integer jobId;

    @Column(name = "salary",nullable = true)
    private Double salario;

    @Column(name = "commission_pct",nullable = true)
    private Double comision;

    @Column(name = "manager_id",nullable = true)
    private Integer managerId;

    @Column(name = "department_id",nullable = true)
    private Integer departamentoId;

    @Column(name = "enabled",nullable = true)
    private Integer enabled;
}
