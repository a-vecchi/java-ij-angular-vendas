package com.example.demo.AngularIjVendas.model.repository;

import com.example.demo.AngularIjVendas.model.entity.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {

    @Query(value = "select s.* from Servico_Prestado s " +
            " inner join Cliente c " +
            "    on c.id = s.id_cliente " +
            " where upper(c.nome) like upper(:nome) " +
            "   and MONTH(s.data) = :mes ", nativeQuery = true)
    public List<ServicoPrestado> findByNomeClienteAndMes(
            @Param("nome") String nome, @Param("mes") Integer mes);
}
