package com.example.lab4gtics.controller;


import com.example.lab4gtics.entity.Empleado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.lab4gtics.repository.EmpleadoRepository;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class EmpleadoController {

    final EmpleadoRepository empleadoRepository;
    public EmpleadoController(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @GetMapping("")
    public String mostrarMenu() {
        return "menuPrincipal";
    }

    @GetMapping("/empleado")
    public String listarEmpleado(Model model) {
        List<Empleado> lista = EmpleadoRepository.findAll();
        model.addAttribute("empleadoList", lista);

        return "shipper/list";
        return "listaEmpleado";
    }

    @GetMapping("/reportes")
    public String reportes() {
        return "reportes";
    }

    @GetMapping("/historial")
    public String historial() {
        return "historial";
    }

    @PostMapping("/buscarFiltro")
    public String buscarPorNombre(@RequestParam("searchField") String searchField, Model model) {

        List<Empleado> lista = EmpleadoRepository.filtrar(searchField);
        model.addAttribute("empleadoFiltroNombre", lista);
        model.addAttribute("textoBuscado", searchField);

        return "menu/empleado";
    }

    @PostMapping("/save")
    public String guardarNuevoEmpleado(Empleado empleado) {
        empleadoRepository.save(empleado);
        return "redirect:/menu/empleado";
    }


}
