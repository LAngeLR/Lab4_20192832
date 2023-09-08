package com.example.lab4gtics.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "job_history")
@Getter
@Setter
public class JobHistory {
    @Id
    @Column(name = "job_history_id",nullable = false)
    private Integer jobHistoryId;

    @Column(name = "employee_id",nullable = false)
    private Integer employeeId;

    @Id
    @Column(name = "start_date",nullable = false)
    private Date startDate;

    @Column(name = "end_date",nullable = false)
    private Date endDate;

    @Column(name = "job_id",nullable = false,length = 10)
    private String jobId;

    @Column(name = "department_id",nullable = true)
    private Integer departmentId;
}
