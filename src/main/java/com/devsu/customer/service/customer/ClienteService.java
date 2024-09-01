package com.devsu.customer.service.customer;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.util.exception.MyApplicationException;

import java.util.List;

public interface ClienteService {

    Cliente getCustomerById(Long id);
    List<Cliente> getCustomerAll();

    Cliente saveCustomer(String identificacion, String nombre, String direccion, String telefono,
                         String contrasena, int edad, String genero) throws Exception;

    Cliente updateCustomer(Long id, String identificacion, String nombre, String direccion, String telefono,
                           String contrasena, int edad, String genero, String estado) throws Exception;

    void deleteCustomer(Long id) throws MyApplicationException;
}
