package com.example.lab4gtics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.lab4gtics.entity.Empleado;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado,Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM employees where first_name = ?1 or last_name=?1")
    List<Empleado> filtrar(String textoIngreso);
}
