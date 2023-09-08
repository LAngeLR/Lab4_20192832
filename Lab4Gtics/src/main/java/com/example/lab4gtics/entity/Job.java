package com.example.lab4gtics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "jobs")
@Getter
@Setter

public class Job {

    @Id
    @Column(name = "job_id",nullable = false,length = 30)
    private Integer jobId;

    @Column(name = "job_title",nullable = false,length = 35)
    private String nombre;

    @Column(name = "min_salary",nullable = false)
    private Integer minSalary;

    @Column(name = "max_salary",nullable = false)
    private Integer maxSalary;

}
