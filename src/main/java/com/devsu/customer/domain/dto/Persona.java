package com.devsu.customer.domain.dto;

import com.devsu.customer.util.generic.dao.GenericDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Setter
@Getter
@Entity
@Table(name="PERSONA", schema = "public")
@NoArgsConstructor
public class Persona extends GenericDto<Persona> {

    @Serial
    private static final long serialVersionUID = 4633771914311361138L;

    @Id
    @SequenceGenerator(name = "Persona_sg", sequenceName = "PERSONA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Persona_sg")
    private Long id;

    @Column(name="IDENTIFICACION", length = 50, nullable=false)
    private String identificacion;

    @Column(name="NOMBRE", length=255, nullable=true)
    private String nombre;

    /**
     * M : Masculino <br>
     * F : Femenino
     */
    @Column(name="GENERO", length=2, nullable=true)
    private String genero;

    @Column(name="EDAD", nullable=true)
    private Integer edad;

    @Column(name="TELEFONO", length=10, nullable=true)
    private String telefono;

    @Column(name="DIRECCION", length=255, nullable=true)
    private String direccion;

    @Builder
    public Persona(Long id, String identificacion, String nombre, String genero, Integer edad, String telefono, String direccion) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String genderLabel() {
        String result = "Masculino";
        if("F".equals(this.genero)) {
            result = "Femenino";
        }
        return  result;
    }

}
