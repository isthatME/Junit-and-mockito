package br.com.aula;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import br.com.aula.exception.ContaNaoExistenteException;
import br.com.aula.exception.ContaSemSaldoException;
import org.junit.Test;

import java.util.Arrays;

public class ContaTest {

	@Test
	public void deveCreditar() {

		// Cenario
		Cliente cliente = new Cliente("João");
		Conta c = new Conta(cliente, 123, 10, TipoConta.CORRENTE);

		// Ação
		c.creditar(5);

		// Verificação
		assertEquals(15, c.getSaldo());
		assertThat(c.getSaldo(), is(15));
	}

	@Test
	public void deveEfetuarTransferenciaEntreContaCorrentePoupanca() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}

	@Test
	public void deveVerificarExistenciaDeContaOrigemEDestino() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 0, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		if(contaOrigem != null && contaDestino != null) {
			banco.efetuarTransferencia(123, 456, 100);
		}

		// Verificação
		assertEquals(-100, contaOrigem.getSaldo());
		assertEquals(100, contaDestino.getSaldo());
	}
	@Test
	public void deveVerificarContaOrigemSaldoPositivo () throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 100, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertTrue(contaOrigem.getSaldo() >= 0);
	}

	@Test
	public void naoDeveTransferirValorNegativo() throws ContaSemSaldoException, ContaNaoExistenteException {

		// Cenario
		Cliente cliente = new Cliente("Joao");
		Conta contaOrigem = new Conta(cliente, 123, 100, TipoConta.CORRENTE);

		Cliente cliente2 = new Cliente("Maria");
		Conta contaDestino = new Conta(cliente2, 456, 0, TipoConta.POUPANCA);

		Banco banco = new Banco(Arrays.asList(contaOrigem, contaDestino));

		// Ação
		int valorSaldoAnterior = contaDestino.getSaldo();
		banco.efetuarTransferencia(123, 456, 100);

		// Verificação
		assertTrue(contaDestino.getSaldo() >= valorSaldoAnterior);
	}
}
