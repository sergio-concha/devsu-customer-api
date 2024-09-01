package com.devsu.customer.service.customer.implementation;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.domain.dto.Persona;
import com.devsu.customer.domain.repository.ClienteRepository;
import com.devsu.customer.service.customer.ClienteService;
import com.devsu.customer.service.customer.PersonaService;
import com.devsu.customer.util.exception.MyApplicationException;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("cienteService")
public class ClienteServiceImpl implements ClienteService {
    private static final Logger LOG = LogManager.getLogger(ClienteServiceImpl.class);


    private final ClienteRepository clienteRepository;
    private final PersonaService personaService;

    @Autowired
    public ClienteServiceImpl(ClienteRepository clienteRepository, PersonaService personaService) {
        this.clienteRepository = clienteRepository;
        this.personaService = personaService;
    }

    @Transactional
    public Cliente getCustomerById(Long id) {
        Optional<Cliente> customer = clienteRepository.findById(id);
        return customer.orElseGet(Cliente::new);
    }

    @Transactional
    public List<Cliente> getCustomerAll() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Cliente saveCustomer(String identificacion, String nombre, String direccion, String telefono,
                                String contrasena, int edad, String genero)  throws Exception {
        LOG.info("Consulta la identificacion del cliente {}", identificacion);
        Persona persona = personaService.registerPerson(identificacion, nombre, direccion, telefono, genero, edad);
        Cliente cliente = Cliente.builder()
                .contrasena(contrasena)
                .clienteId(null)
                .estado("A")
                .persona(persona)
                .build();
        cliente = clienteRepository.save(cliente);
        LOG.info("Cliente {} - {} se guardo con exito.", cliente.getId(), nombre);
        return cliente;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Cliente updateCustomer(Long id, String identificacion, String nombre, String direccion, String telefono,
                                  String contrasena, int edad, String genero, String estado) throws Exception {
        LOG.info("Valida si el cliente {} existe", nombre);
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) {
            LOG.warn("Cliente {} no existe.", identificacion);
            throw new MyApplicationException("E001", "Cliente " + nombre + " no existe");
        }
        Persona persona = personaService.registerPerson(identificacion,nombre, direccion, telefono, genero, edad);

        Cliente saveCliente = cliente.get();
        saveCliente.setContrasena(contrasena);
        saveCliente.setPersona(persona);
        saveCliente.setEstado(estado);

        return clienteRepository.save(saveCliente);
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public void deleteCustomer(Long id) throws MyApplicationException {
        LOG.info("Se valida el cliente con id {}.", id);
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) {
            LOG.warn("El cliente con id {} no existe.", id);
            throw new MyApplicationException("E001", "Cliente no existe.");
        }
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            LOG.error("Error al eliminar cliente: {}", e.getMessage());
            throw new MyApplicationException("E002", "Error al eliminar cliente.", e);
        }
    }

}
