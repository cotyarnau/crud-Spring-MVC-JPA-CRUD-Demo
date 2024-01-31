package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.DepartamentoDao;
import com.example.entities.Departamento;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class DepartamentoServiceImpl implements DepartamentoService {

     // Inyeccion de dependencia (DI) por constructor (esta es la forma que mas se usa)

    //  Asi se hacia antes
    // private DepartamentoDao departamentoDao;
    // public DepartamentoServiceImpl(DepartamentoDao departamentoDao){
    //     this.departamentoDao = departamentoDao;
    // }

    // Asi se hace ahora

    private final DepartamentoDao departamentoDao; 


    @Override
    public List<Departamento> dameDepartamentos() {
       
        return departamentoDao.findAll();
    }

    @Override
    public Departamento dameunDepartamento(int idDepartamento) {
       
        return departamentoDao.findById(idDepartamento).get(); 
    }

    @Override
    public void persistirDpto(Departamento departamento) {
        departamentoDao.save(departamento);
    }

}
