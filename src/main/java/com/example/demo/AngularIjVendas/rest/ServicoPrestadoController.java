package com.example.demo.AngularIjVendas.rest;

import com.example.demo.AngularIjVendas.model.entity.Cliente;
import com.example.demo.AngularIjVendas.model.entity.ServicoPrestado;
import com.example.demo.AngularIjVendas.model.repository.ClienteRepository;
import com.example.demo.AngularIjVendas.model.repository.ServicoPrestadoRepository;
import com.example.demo.AngularIjVendas.rest.dto.ServicoPrestadoDTO;
import com.example.demo.AngularIjVendas.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;

    @GetMapping
    public List<ServicoPrestado> obterTodos() {
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody ServicoPrestadoDTO dto) {
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inesistente."));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));

        return repository.save(servicoPrestado);
    }

    @GetMapping("/pesquisar")
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }

    @GetMapping("{id}")
    public ServicoPrestado acharPorId(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço Prestado não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        repository.findById(id)
                .map(servicoPrestado -> {
                    repository.delete(servicoPrestado);
                    return Void.TYPE;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço Prestado não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid ServicoPrestado servicoAtualizado) {
        repository.findById(id)
                .map(servicoPrestado -> {
                    servicoPrestado.setDescricao(servicoAtualizado.getDescricao());
                    servicoPrestado.setData(servicoAtualizado.getData());
                    servicoPrestado.setValor(servicoAtualizado.getValor());
                    servicoPrestado.setCliente(servicoAtualizado.getCliente());
                    return repository.save(servicoPrestado);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Serviço Prestado não encontrado"));
    }
}
