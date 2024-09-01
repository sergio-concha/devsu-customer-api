package com.devsu.customer.application.controller.clientes;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.service.customer.ClienteService;
import com.devsu.customer.util.exception.MyApplicationException;
import com.devsu.customer.util.generic.dao.Request;
import com.devsu.customer.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/clientes")
@RequiredArgsConstructor
public class ClientesUpdateController {

    private static final Logger LOG = LogManager.getLogger(ClientesRegisterController.class);

    private final ClienteService clienteService;

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<Response> update(@RequestBody Request request) {
        LOG.info("API [ PUT: /update ] INI => Process service register customer");
        Response response = new Response();
        try {
            Long id = request.getParam("id", Long.class);
            String contrasena = request.getParam("contrasena", String.class);
            String identificacion = request.getParam("identificacion", String.class);
            String direccion = request.getParam("direccion", String.class);
            String nombre = request.getParam("nombre", String.class);
            int edad = request.getParam("edad", Integer.class);
            String genero = request.getParam("genero", String.class);
            String telefono = request.getParam("telefono", String.class);
            String estado = request.getParam("estado", String.class);

            Cliente clientes = clienteService.updateCustomer(id, identificacion, nombre, direccion, telefono,
                    contrasena, edad, genero, estado);

            Map<String, Object> cliente = clientes.toHashMap();

            response.putItem("massiveSelect", cliente);

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ PUT: /update ] END => Process service register customer");
        }
    }
}
