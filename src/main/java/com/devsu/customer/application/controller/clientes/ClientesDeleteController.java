package com.devsu.customer.application.controller.clientes;

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

@RestController
@RequestMapping("/devsu/api/v1/clientes")
@RequiredArgsConstructor
public class ClientesDeleteController {

    private static final Logger LOG = LogManager.getLogger(ClientesDeleteController.class);

    private final ClienteService clienteService;

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<Response> delete(@RequestBody Request request) {
        LOG.info("API [ DELETE: /delete ] INI => Process service delete customer");
        Response response = new Response();
        try {
            Long id = request.getParam("id", Long.class);

            clienteService.deleteCustomer(id);

            response.putItem("message", "Cliente eliminado con exito!");

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ DELETE: /delete ] END => Process service delete customer");
        }
    }
}
