package br.net.msconta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.net.msconta.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Integer> {

}
