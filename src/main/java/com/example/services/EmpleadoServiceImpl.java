package com.example.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.EmpleadoDao;
import com.example.entities.Empleado;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoDao empleadoDao;

    @Override
    public List<Empleado> dameTodosLosEmpleados() {
        return empleadoDao.findAll();
        // nos devuelve todos los empleados

    }

    @Override
    public Empleado dameunEmpleado(int idEmpleado) {

        return empleadoDao.findById(idEmpleado).get();
        // le agregamos el metodo .get para que dentro del opcional nos devuelva un
        // empleado
    }

    @Override
    public void eliminarEmpleado(int idEmpleado) {
        empleadoDao.deleteById(idEmpleado);
    }

    @Override
    public void persistirEmpleado(Empleado empleado) {

        empleadoDao.save(empleado);
    }

    @Override
    public void actualizarEmpleado(Empleado empleado) {

        empleadoDao.save(empleado);
    }

}
