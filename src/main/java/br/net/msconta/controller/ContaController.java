package br.net.msconta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.msconta.model.Conta;
import br.net.msconta.repository.ContaRepository;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Conta> getAllConta() {
        return contaRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public Conta createConta(@RequestBody Conta conta) {
        Conta savedConta = contaRepository.save(conta);

        return savedConta;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public Conta Conta(@PathVariable Integer id, @RequestBody Conta contaData) {
        Conta conta = contaRepository.findById(id).orElse(null);
        if (conta != null) {
            conta.setNumeroConta(contaData.getNumeroConta());
            conta.setDataCriacao(contaData.getDataCriacao());
            conta.setLimite(contaData.getLimite());
            conta.setIdCliente(contaData.getIdCliente());
            conta.setIdGerente(contaData.getIdGerente());
            conta.setSaldo(contaData.getSaldo());

            return contaRepository.save(conta);
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public void deleteConta(@PathVariable Integer id) {
        contaRepository.deleteById(id);
    }
}
