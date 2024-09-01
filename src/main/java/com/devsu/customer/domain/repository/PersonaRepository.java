package com.devsu.customer.domain.repository;

import com.devsu.customer.domain.dto.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository(value = "personaRepository")
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    @Query(value = "Select p "
            + "from Persona p "
            + "where p.identificacion = :identificacion ")
    Persona findByIdentificacion(@Param("identificacion") String identificacion);

}
