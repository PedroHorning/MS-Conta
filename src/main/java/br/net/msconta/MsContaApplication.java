package br.net.msconta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"br.net.msconta.repository.ContaRepository", "br.net.msconta.repository.MovimentacaoRepository"})
public class MsContaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsContaApplication.class, args);
	}

}
