package br.net.msconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @CrossOrigin(origins= "*")
    @GetMapping
    public List<Conta> getAllConta() {
        return contaRepository.findAll();
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping
    public Conta createConta(@RequestBody Conta conta) { 
    	Conta savedConta= contaRepository.save(conta);
        
        return savedConta;
    }

    @CrossOrigin(origins= "*")
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


    @CrossOrigin(origins= "*")
    @DeleteMapping("/{id}")
    public void deleteConta(@PathVariable Integer id) {
    	contaRepository.deleteById(id);
    }
}
