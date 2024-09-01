package com.devsu.customer.domain.dto;

import com.devsu.customer.util.generic.dao.GenericDto;
import com.devsu.customer.util.utilities.DtoUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name="CLIENTE", schema = "public")
@NoArgsConstructor
public class Cliente extends GenericDto<Cliente> {

    @Serial
    private static final long serialVersionUID = 4633771914311362138L;

    @Id
    @SequenceGenerator(name = "Cliente_sg", sequenceName = "CLIENTE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cliente_sg")
    private Long id;

    @JoinColumn(name="PERSONA_ID", nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Persona persona;

    @Column(name="CLIENTEID", length = 8)
    private String clienteId;

    @Column(name="CONTRASENA", length = 50, nullable=false)
    private String contrasena;

    /**
     * A: Activo <br>
     * I: Inactivo
     */
    @Column(name="ESTADO", length = 2, nullable=false)
    private String estado;

    @Builder
    public Cliente(Long id, Persona persona, String clienteId, String contrasena, String estado) {
        this.id = id;
        this.persona = persona;
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = estado;
    }

    public String statusLabel() {
        String status = "Activo";
        if("I".equals(this.estado)) {
            status = "Inactivo";
        }
        return status;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("clienteId", this.clienteId);
        map.put("contrasena", this.contrasena);
        map.put("estado", this.estado);
        map.put("estadoLabel", statusLabel());
        if(DtoUtils.isPersistence(this.persona)) {
            map.put("identificacion", this.persona.getIdentificacion());
            map.put("direccion", this.persona.getDireccion());
            map.put("nombre", this.persona.getNombre());
            map.put("edad", this.persona.getEdad());
            map.put("genero", this.persona.getGenero());
            map.put("generoLabel", this.persona.genderLabel());
            map.put("telefono", this.persona.getTelefono());
        }
        return map;
    }
}
