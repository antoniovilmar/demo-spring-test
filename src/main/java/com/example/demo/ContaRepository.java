package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<ContaProjection> findByNumeroConta(Long numeroConta);
}
