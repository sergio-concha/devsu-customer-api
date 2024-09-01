package com.devsu.customer.domain.repository;

import com.devsu.customer.domain.dto.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository(value = "clienteRepository")
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Override
    @Query(value = "Select c " +
            "from Cliente c " +
            "join fetch c.persona p " +
            "where c.id = :id ")
    Optional<Cliente> findById(@Param("id") Long id);

    @Override
    @Query(value = "Select c " +
            "from Cliente c " +
            "join fetch c.persona p")
    List<Cliente> findAll();
}
