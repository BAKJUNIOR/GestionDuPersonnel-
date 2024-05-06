package com.example.applicationgestinemployes.servive;

import com.example.applicationgestinemployes.model.Employe;
import com.example.applicationgestinemployes.repository.EmployeRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;


import java.util.List;

@Named
public class EmployeServiceImpl implements EmployeService {

    @Inject
    private EmployeRepository employeRepository;

    @Override
    public List<Employe> getAllEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public void saveEmploye(Employe employe) {
        employeRepository.save(employe);
    }

    @Override
    public void deleteEmploye(Employe employe) {
        employeRepository.delete(employe);
    }
}
