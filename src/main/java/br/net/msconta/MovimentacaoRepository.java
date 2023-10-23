package br.net.msconta;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByContaAndDataBetween(Conta conta, LocalDateTime dataInicio, LocalDateTime dataFim);
}
