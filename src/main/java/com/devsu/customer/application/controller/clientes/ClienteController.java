package com.devsu.customer.application.controller.clientes;

import com.devsu.customer.domain.dto.Cliente;
import com.devsu.customer.service.customer.ClienteService;
import com.devsu.customer.util.exception.MyApplicationException;
import com.devsu.customer.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private static final Logger LOG = LogManager.getLogger(ClienteController.class);

    private final ClienteService clienteService;

    @GetMapping(value = "/massiveSelect", produces = "application/json")
    public ResponseEntity<Response> massiveSelect() {
        LOG.info("API [ GET: /massiveSelect ] INI => Process service massiveSelect");
        Response response = new Response();
        try {
            // Get map of labels from DB
            List<Cliente> clientes = clienteService.getCustomerAll();
            List<Map<String, Object>> clientesResponse = clientes.stream().map(Cliente::toHashMap).toList();

            response.putItem("massiveSelect", clientesResponse);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ GET: /massiveSelect ] END => Process service massiveSelect");
        }
    }
}
