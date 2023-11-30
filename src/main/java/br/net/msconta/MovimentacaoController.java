package br.net.msconta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimentacao")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    
    @Autowired
    private ContaRepository contaRepository;

    @CrossOrigin(origins= "*")
    @GetMapping
    public List<Movimentacao> getAllMovimentacao() {
        return movimentacaoRepository.findAll();
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping
    public Movimentacao createMovimentacao(@RequestBody Movimentacao movimentacao) { 
    	Movimentacao savedMovimentacao= movimentacaoRepository.save(movimentacao);
        
        return savedMovimentacao;
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping("/deposito/{idConta}")
    public Movimentacao deposito(@PathVariable Integer idConta, @RequestBody Movimentacao movimentacao) {
        Conta conta = contaRepository.findById(idConta).orElse(null);
        
        if (conta != null) {
            Double novoSaldo = conta.getSaldo() + movimentacao.getValor();
            conta.setSaldo(novoSaldo);
            contaRepository.save(conta);
            
            movimentacao.setTipo("DEPOSITO");
            movimentacao.setIdContaDestino(idConta);
            movimentacao.setDataHora(LocalDateTime.now());
            return movimentacaoRepository.save(movimentacao);
        }
        return null;
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping("/saque/{idConta}")
    public Movimentacao saque(@PathVariable Integer idConta, @RequestBody Movimentacao movimentacao) {
        Conta conta = contaRepository.findById(idConta).orElse(null);

        if (conta != null) {
            if (conta.getSaldo() >= movimentacao.getValor()) {
                Double novoSaldo = conta.getSaldo() - movimentacao.getValor();
                conta.setSaldo(novoSaldo);
                contaRepository.save(conta);

                movimentacao.setTipo("SAQUE");
                movimentacao.setIdContaOrigem(idConta);
                movimentacao.setDataHora(LocalDateTime.now());
                return movimentacaoRepository.save(movimentacao);
            } else {
            	throw new SaldoInsuficienteException("Saldo insuficiente para o saque");
            }
        }
        return null;
    }
    
    @CrossOrigin(origins= "*")
    @PostMapping("/transferencia/{idContaOrigem}/{numeroContaDestino}")
    public Movimentacao transferencia(
            @PathVariable Integer idContaOrigem,
            @PathVariable Integer numeroContaDestino,
            @RequestBody Double valorTransferencia) {

        Conta contaOrigem = contaRepository.findById(idContaOrigem).orElse(null);
        Conta contaDestino = contaRepository.findById(numeroContaDestino).orElse(null);

        if (contaOrigem != null && contaDestino != null && contaOrigem.getSaldo() >= valorTransferencia) {
            Double novoSaldoOrigem = contaOrigem.getSaldo() - valorTransferencia;
            contaOrigem.setSaldo(novoSaldoOrigem);
            contaRepository.save(contaOrigem);

            Double novoSaldoDestino = contaDestino.getSaldo() + valorTransferencia;
            contaDestino.setSaldo(novoSaldoDestino);
            contaRepository.save(contaDestino);

            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setTipo("TRANSFERENCIA");
            movimentacao.setIdContaOrigem(idContaOrigem);
            movimentacao.setIdContaDestino(contaDestino.getId());
            movimentacao.setValor(valorTransferencia);
            movimentacao.setDataHora(LocalDateTime.now());
            return movimentacaoRepository.save(movimentacao);
        } else {
            throw new TransferenciaInvalidaException("Transferência não pôde ser concluída");
        }
    }
    
    public class TransferenciaInvalidaException extends RuntimeException {
        public TransferenciaInvalidaException(String message) {
            super(message);
        }
    }
    
    public class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
    }

    @CrossOrigin(origins= "*")
    @PutMapping("/{id}")
    public Movimentacao Movimentacao(@PathVariable Integer id, @RequestBody Movimentacao movimentacaoData) {
    	Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);
        if (movimentacao != null) {
        	movimentacao.setDataHora(movimentacaoData.getDataHora());
        	movimentacao.setTipo(movimentacaoData.getTipo());
        	movimentacao.setValor(movimentacaoData.getValor());
        	movimentacao.setIdContaOrigem(movimentacaoData.getIdContaOrigem());
        	movimentacao.setIdContaDestino(movimentacaoData.getIdContaDestino());
        	movimentacao.setIdClienteOrigem(movimentacaoData.getIdClienteOrigem());
        	movimentacao.setIdClienteDestino(movimentacaoData.getIdClienteDestino());

            return movimentacaoRepository.save(movimentacao);
        }
        return null;
    }


    @CrossOrigin(origins= "*")
    @DeleteMapping("/{id}")
    public void deleteMovimentacao(@PathVariable Integer id) {
    	movimentacaoRepository.deleteById(id);
    }
}
