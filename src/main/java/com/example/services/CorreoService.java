package com.example.services;

import java.util.List;

import com.example.entities.Correo;

public interface CorreoService {

    public List<Correo> dameCorreos(int idEmpleado);
    public void eliminarCorreos(int idEmpleado);
    public void persistirCorreos(int idEmpleado, Correo correo);

}
