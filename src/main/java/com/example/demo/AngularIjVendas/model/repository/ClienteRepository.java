package com.example.demo.AngularIjVendas.model.repository;

import com.example.demo.AngularIjVendas.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
