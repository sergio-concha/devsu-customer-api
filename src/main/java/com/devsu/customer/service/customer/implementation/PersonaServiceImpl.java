package com.devsu.customer.service.customer.implementation;

import com.devsu.customer.domain.dto.Persona;
import com.devsu.customer.domain.repository.PersonaRepository;
import com.devsu.customer.service.customer.PersonaService;
import com.devsu.customer.util.exception.MyApplicationException;
import com.devsu.customer.util.utilities.DtoUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personaService")
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Persona registerPerson(String identificacion, String nombre, String direccion, String telefono,
                                  String genero, int edad) throws Exception {

        Persona persona = getPersonaByIdentificacion(identificacion);
        if(!DtoUtils.isPersistence(persona)) {
            persona = Persona.builder()
                    .identificacion(identificacion)
                    .build();
        }
        persona.setNombre(nombre);
        persona.setGenero(genero);
        persona.setEdad(edad);
        persona.setTelefono(telefono);
        persona.setDireccion(direccion);

        return personaRepository.save(persona);
    }

    public Persona getPersonaByIdentificacion(String idenitificacion) {
        return personaRepository.findByIdentificacion(idenitificacion);
    }
}
