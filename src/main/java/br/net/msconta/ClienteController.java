package br.net.msconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @CrossOrigin(origins= "*")
    @GetMapping
    public List<Cliente> getAllCliente() {
        return clienteRepository.findAll();
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) { 
    	Cliente savedCliente = clienteRepository.save(cliente);
        
        return savedCliente;
    }

    @CrossOrigin(origins= "*")
    @PutMapping("/{id}")
    public Cliente Cliente(@PathVariable Integer id, @RequestBody Cliente clienteData) {
    	Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
        	cliente.setNome(clienteData.getNome());
        	cliente.setCpf(clienteData.getCpf());

            return clienteRepository.save(cliente);
        }
        return null;
    }


    @CrossOrigin(origins= "*")
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Integer id) {
    	clienteRepository.deleteById(id);
    }
}

