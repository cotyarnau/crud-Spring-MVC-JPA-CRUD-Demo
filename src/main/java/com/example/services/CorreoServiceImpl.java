package com.example.services;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.dao.CorreoDao;
import com.example.dao.EmpleadoDao;
import com.example.entities.Correo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor



public class CorreoServiceImpl implements CorreoService {
    
    private final CorreoDao correoDao;
    private final EmpleadoDao empleadoDao;
    
    @Override
    public List<Correo> dameCorreos(int idEmpleado) {
        return correoDao.findByEmpleado(empleadoDao.findById(idEmpleado).get());
        
    }

    @Override
    public void eliminarCorreos(int idEmpleado) {
        correoDao.deletebyEmpleado(empleadoDao.findById(idEmpleado).get());
    }

    @Override
    public void persistirCorreos(int idEmpleado, Correo correo) {
        correo.setEmpleado(empleadoDao.findById(idEmpleado).get());
        correoDao.save(correo);
    }

}
