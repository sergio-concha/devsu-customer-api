package com.devsu.customer.service.customer;

import com.devsu.customer.domain.dto.Persona;

public interface PersonaService {

    Persona registerPerson(String identificacion, String nombre, String direccion, String telefono,
            String genero, int edad) throws Exception;

    Persona getPersonaByIdentificacion(String idenitificacion);
}
