package com.devsu.customer.tester.clientes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.domain.dto.Persona;
import com.devsu.customer.domain.repository.ClienteRepository;
import com.devsu.customer.service.customer.PersonaService;
import com.devsu.customer.service.customer.implementation.ClienteServiceImpl;
import com.devsu.customer.service.customer.implementation.PersonaServiceImpl;
import com.devsu.customer.util.exception.MyApplicationException;
import com.devsu.customer.util.utilities.DtoUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

//@SpringBootTest(classes = {ClientesServiceTest.class})
public class ClientesServiceTest {
    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PersonaService personaService;

    @InjectMocks
    private ClienteServiceImpl clienteService;


    private Cliente cliente;
    private Persona person;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        person = Persona.builder()
                .id(4L)
                .identificacion("0918254785")
                .nombre("Jose Lema")
                .direccion("Otavalo sn y principal")
                .genero("M")
                .edad(34)
                .telefono("098254785")
                .build();

        cliente = Cliente.builder()
                .id(5L)
                .persona(person)
                .contrasena("1234")
                .estado("A")
                .clienteId(null)
                .build();
    }

    @Test
    public void testCrearCliente() throws Exception {
//        doNothing().when(personaService).registerPerson(person.getIdentificacion(), person.getNombre(), person.getDireccion(),
//                person.getTelefono(), person.getGenero(), person.getEdad());

        // Configurar el comportamiento del mock
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Llamar al metodo de servicio
        Cliente clienteCreado = clienteService.saveCustomer(person.getIdentificacion(), person.getNombre(), person.getDireccion(),
                person.getTelefono(), cliente.getContrasena(), person.getEdad(), person.getGenero());

        // Verificar que el metodo del repositorio se haya llamado una vez
        verify(personaService, times(1)).registerPerson(person.getIdentificacion(), person.getNombre(), person.getDireccion(),
                person.getTelefono(), person.getGenero(), person.getEdad());
        verify(clienteRepository, times(1)).save(cliente);

        // Validar el resultado
        assertNotNull(clienteCreado);
        assertEquals(person.getNombre(), clienteCreado.getPersona().getNombre());
    }

    @Test
    public void testObtenerClientePorId() {
        // Configurar el comportamiento del mock
        when(clienteRepository.findById(5L)).thenReturn(Optional.of(cliente));

        // Llamar al método de servicio
        Cliente clienteObtenido = clienteService.getCustomerById(5L);

        // Verificar que el método del repositorio se haya llamado una vez
        verify(clienteRepository, times(1)).findById(5L);

        // Validar el resultado
        assertTrue(DtoUtils.isPersistence(clienteObtenido));
        assertEquals(cliente.getId(), clienteObtenido.getId());
    }

    @Test
    public void testEliminarCliente() throws MyApplicationException {
        // Llamar al método de servicio
        clienteService.deleteCustomer(1L);

        // Verificar que el método del repositorio se haya llamado una vez
        verify(clienteRepository, times(1)).deleteById(5L);
    }
}
