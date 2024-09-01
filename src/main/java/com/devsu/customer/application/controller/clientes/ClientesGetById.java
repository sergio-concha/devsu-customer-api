package com.devsu.customer.application.controller.clientes;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.service.customer.ClienteService;
import com.devsu.customer.util.exception.MyApplicationException;
import com.devsu.customer.util.generic.dao.Response;
import com.devsu.customer.util.utilities.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/clientes")
@RequiredArgsConstructor
public class ClientesGetById {

    private static final Logger LOG = LogManager.getLogger(ClientesGetById.class);

    private final ClienteService clienteService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<Response> getById(@RequestParam Long id) {
        LOG.info("API [ DELETE: /get ] INI => Process service delete customer");
        Response response = new Response();
        try {
            Cliente cliente = clienteService.getCustomerById(id);
            Map<String, Object> customerResponse = new HashMap<>();
            if(DtoUtils.isPersistence(cliente)) {
                customerResponse = cliente.toHashMap();
            }

            response.putItem("customer", customerResponse);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ DELETE: /get ] END => Process service delete customer");
        }
    }
}
