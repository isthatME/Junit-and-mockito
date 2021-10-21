package br.com.aula;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.aula.exception.ContaJaExistenteException;
import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;

public class BancoTest {

	@Test
	public void deveCadastrarConta() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta);

		// Verificação
		assertEquals(1, banco.obterContas().size());
	}

	@Test(expected = ContaJaExistenteException.class)
	public void naoDeveCadastrarContaNumeroRepetido() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta conta2 = new Conta(cliente2, 123, 0, TipoConta.POUPANCA);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		banco.cadastrarConta(conta2);

		Assert.fail();
	}

	@Test
	public void naoDeveCadastrarContaComNumeroInvalido() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Banco banco = new Banco();

		// Ação
		if(conta1.getNumeroConta() >= 0) {
			banco.cadastrarConta(conta1);
		}
		assertEquals(1, banco.obterContas().size());
	}

	@Test
	public void naoDeveCadastrarContaClienteExistente() throws ContaJaExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta conta1 = new Conta(cliente, 123, 0, TipoConta.CORRENTE);
		Cliente cliente2 = new Cliente("Joao");
		Conta conta2 = new Conta(cliente2, 312, 0, TipoConta.CORRENTE);

		Banco banco = new Banco();

		// Ação
		banco.cadastrarConta(conta1);
		List<Conta> contas = banco.obterContas();
		int quantidadeContasIguais = 0;
		for (Conta c : contas) {
			boolean isNomeClienteIgual = c.getCliente().getNome().equals(cliente2.getNome());
			if (isNomeClienteIgual) {
				quantidadeContasIguais = 1;
			}
		}
		if(quantidadeContasIguais == 0) {
			banco.cadastrarConta(conta2);
		}
		//Verificação
		assertEquals(1, banco.obterContas().size());
	}

	@Test
	public void deveEfetuarTransferenciaContasCorrentes() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.CORRENTE);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}


}
