package com.example.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.entities.Correo;
import com.example.entities.Departamento;
import com.example.entities.Empleado;
import com.example.entities.Telefono;
import com.example.services.DepartamentoService;
import com.example.services.EmpleadoService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {

    private final EmpleadoService empleadoService;
    private final DepartamentoService departamentoService;

    private final Logger LOG = Logger.getLogger("MainController");

    // antiguamente se hacia de esta manera
    // @GetMapping("/all")
    // public ModelAndView dameEmpleado() {
    // ModelAndView modelo = new ModelAndView("views/listadoEmpleados");
    // List<Empleado> empleados = empleadoService.dameTodosLosEmpleados();
    // modelo.addObject("empleados", empleados);

    // return modelo;
    // }

    // Actualmente se hace asi:

    @GetMapping("/all")
    public String dameEmpleados(Model model) {

        model.addAttribute("empleados", empleadoService.dameTodosLosEmpleados());
        return "views/listadoEmpleados";

    }
    // Cuando se recibe un parametro conjuntamente con la request se procede de la
    // siguiente manera
    // @GetMapping("/detalles")
    // public String detallesEmpleado(@RequestParam(name = "id") int idEmpleado,
    // Model model) {
    // LOG.info("ID Empleado Recibido" + idEmpleado);
    // return "views/empleadoDetalles";

    @GetMapping("/detalles/{id}")
    public String detallesEmpleado(@PathVariable(name = "id") int idEmpleado, Model model) {
        LOG.info("ID Empleado Recibido" + idEmpleado);
        model.addAttribute("empleado", empleadoService.dameunEmpleado(idEmpleado));

        return "views/empleadoDetalles";
    }

    @GetMapping("/frmAltaModificacion")
    public String formularioAltaModificacionEmpleado(Model model) {
        // Al Modelo le paso un objeto empleado vacio
        Empleado empleado = new Empleado();

        model.addAttribute("empleado", empleado);

        // Tambien los departamentos

        model.addAttribute("departamentos", departamentoService.dameDepartamentos());

        return "views/frmAltaModificacionEmpleado";

    }

    @PostMapping("/persistir")
    @Transactional
    public String persistirEmpleado(@ModelAttribute(name = "empleado") Empleado empleado,
            @RequestParam(name = "numerosTel", required = false) String telefonosRecibidos,
            @RequestParam(name = "direccionesCorreo", required = false) String correosRecibidos) {

        // Procesar los telefonos
        if (telefonosRecibidos != null) {
            String[] arrayTelefonos = telefonosRecibidos.split(";");
            List<String> numerosTelefonos = Arrays.asList(arrayTelefonos);

            List<Telefono> telefonos = new ArrayList<>();
            numerosTelefonos.stream().forEach(numeroTelefono -> {
                telefonos.add(
                        Telefono.builder()
                                .numero(numeroTelefono)
                                .empleado(empleado)
                                .build());
            });
            empleado.setTelefonos(telefonos);
        }
        // Procesar los correos
        if (correosRecibidos != null) {
            String[] arrayCorreos = correosRecibidos.split(";");
            List<String> direccionesDeCorreo = Arrays.asList(arrayCorreos);

            List<Correo> correos = new ArrayList<>();
            direccionesDeCorreo.stream().forEach(direccionDeCorreo -> {
                correos.add(
                        Correo.builder()
                                .correo(direccionDeCorreo)
                                .empleado(empleado)
                                .build());
            });
            empleado.setCorreos(correos);

        }
        empleadoService.persistirEmpleado(empleado);
        return "redirect:/all";

    }

    @GetMapping("/actualizar/{id}")
    @Transactional
    public String actualizarEmpleado(@PathVariable(name = "id", required = true) int idEmpleado, Model model) {
        // Recupera el empleado cuyo id se recibe como parametro
        Empleado empleado = empleadoService.dameunEmpleado(idEmpleado);
        model.addAttribute("empleado", empleado);
        // Recupero los departamentos
        List<Departamento> departamentos = departamentoService.dameDepartamentos();
        model.addAttribute("departamentos", departamentos);

        // Obtengo los numeros de telefono (separados por ;) para que aparezcan en el
        // formulario
        // no necesito recupararlos porque ya se propagan los cambios por cascada

        if (empleado.getTelefonos() != null) {
            String numerosTelefono = empleado.getTelefonos().stream().map(Telefono::getNumero)
                    .collect(Collectors.joining(";"));
            model.addAttribute("numerosTelefono", numerosTelefono);
        }

        // Obtengo los correos

        if (empleado.getCorreos() != null) {
            String direccionesDeCorreo = empleado.getCorreos().stream().map(Correo::getCorreo)
                    .collect(Collectors.joining(";"));
            model.addAttribute("direccionesDeCorreo", direccionesDeCorreo);
        }
        return "views/frmAltaModificacionEmpleado";

    }

    @GetMapping("/eliminar/{id}")
    @Transactional
    public String eliminarEmpleado(@PathVariable(name = "id", required = true) int idEmpleado) {
        empleadoService.eliminarEmpleado(idEmpleado);

        return "redirect:/all";

    }
}
