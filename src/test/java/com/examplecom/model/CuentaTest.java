package com.examplecom.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.example.exceptions.SaldoInsuficienteException;
import com.example.model.Banco;
import com.example.model.Cuenta;

public class CuentaTest {
	
	Cuenta cuenta, cuenta2;
	
	@BeforeEach
	void initBeforEach() {
		System.out.println("Iniciando Test");
		cuenta = new Cuenta("Albert", new BigDecimal("1000.1234"));
		
	}
	
	
	@AfterEach
	void endAfterEach() {
		System.out.println("Finalizando Test");
	}
	
	@Test
	@Disabled
	@DisplayName("Probando nombre del cliente de una cuenta")
	 void testNombre() {
		
			
		Assertions.assertEquals("Albert", cuenta.getPersona());
		
	}
	
	@Test
	@Tag("cuenta")
	void testSaldo() {
		
		Assertions.assertEquals(1000.1234, cuenta.getSaldo().doubleValue());
	}
	
	
	@Test
	@Tag("cuenta")
	@DisplayName("Probando que 2 cuentas son las mismas")
	void testSameObject() {
		
		cuenta2 = new Cuenta("Albert", new BigDecimal("1000.1234"));
		Assertions.assertEquals(cuenta, cuenta2);
		
	}
	
	@Test
	void testDebito() {
		
		cuenta.debido(new BigDecimal("100"));

		Assertions.assertNotNull(cuenta);
		Assertions.assertEquals(900, cuenta.getSaldo().intValue());
		
	}
	
	@Test
	@Tag("cuenta")
	void testCredito() {
		cuenta.credito(new BigDecimal("100"));
		Assertions.assertNotNull(cuenta);
		Assertions.assertEquals(new BigDecimal("1100.1234") , cuenta.getSaldo());
		
	}
	
	
	@Test
	@DisplayName("Probando saldo insuficiente en una operaci�n debito")
	void testSaldoInsuficiente() {

		
		Exception ex = assertThrows(SaldoInsuficienteException.class, ()-> {
			cuenta.debido(new BigDecimal("1500"));
		});
		
		assertTrue("No hay saldo suficiente".equals(ex.getMessage()));
	}
	
	@Test
	@DisplayName("Probando una transferencia entre 2 cuentas")
	void testTransferencia() {
		Cuenta cuenta1 = new Cuenta("Albert", new BigDecimal("2500.1234"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1000.1234"));
		Banco banco = new Banco("BCP");
		banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));
		Assertions.assertEquals("3000.1234", cuenta1.getSaldo().toPlainString());
		Assertions.assertEquals("500.1234", cuenta2.getSaldo().toPlainString());
		
		
	}
	
	@Test
	void testInsertCuentas() {
		
		Cuenta cuenta1 = new Cuenta("Albert", new BigDecimal("2500.1234"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1000.1234"));
		Banco banco = new Banco("BCP");
		banco.addCuenta(cuenta1);
		banco.addCuenta(cuenta2);
		
		assertEquals(2, banco.getCuentas().size());
		
	}
	
	
	@Test
	void testRelacionBancoCuenta() {
		
		Cuenta cuenta1 = new Cuenta("Albert", new BigDecimal("2500.1234"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1000.1234"));
		Banco banco = new Banco("BCP");
		banco.addCuenta(cuenta1);
		banco.addCuenta(cuenta2);
		
		assertTrue(banco.getCuentas().stream()
				.anyMatch(c -> c.getPersona().equals("Albert")));
		
	}
	
	
	@Test
	void testRelacionBancoCuenta2() {
		
		Cuenta cuenta1 = new Cuenta("Albert", new BigDecimal("2500.1234"));
		Cuenta cuenta2 = new Cuenta("Pepe", new BigDecimal("1000.1234"));
		Banco banco = new Banco("BCP");
		banco.addCuenta(cuenta1);
		banco.addCuenta(cuenta2);
		
		assertAll(
			() -> assertEquals("BCP", cuenta1.getBanco().getNombre(),
					() -> "El nombre del banco de la cuenta no corresponde"),
			() -> assertEquals(2, banco.getCuentas().size(),
					() -> "El total de cuentas del banco no coincide con lo esperado"),
			() -> assertTrue(banco.getCuentas().stream()
					.anyMatch(c -> c.getPersona().equals("Albert")),
					() -> "No se encontr� la cuenta correspondiente al cliente Albert")
			
		);
		
		
	}
	
	@BeforeAll
	static void initBeforeAll() {
		System.out.println("Iniciando Testing");
	}
	
	@AfterAll
	static void initAfterAll() {
		System.out.println("Finalizando Testing");
	}
	
	@Test
	@Disabled
	@DisplayName("Test de variables de ambiente")
	void testInfoEnv() {
		Map<String, String> mapa = System.getenv();
		mapa.forEach((k, v) -> System.out.println(String.format("%s - %s", k, v)));
	}
	
	@Nested
	class ParametersTest{
		@Tag("param")
		@ParameterizedTest
		@DisplayName("Test cuenta Debido con valores parametrizados")
		@ValueSource(strings = {"100", "200", "300", "400", "800", "1000","1000.1234"})
		void testCuentaDebito(String monto) {
			cuenta.debido(new BigDecimal(monto));
			Assertions.assertNotNull(cuenta);
			assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
			
		}
		
		@Tag("param")
		@ParameterizedTest
		@DisplayName("Test cuenta Debido con 2 valores parametrizados")
		@CsvSource({"200,100", "210,200", "300.1,300", "400,400", "1000,800", "1000.1234,1000.1234"})
		void testCuentaDebito(String saldo, String monto) {
			cuenta.setSaldo(new BigDecimal(saldo));
			cuenta.debido(new BigDecimal(monto));
			Assertions.assertNotNull(cuenta);
			assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) >= 0);
			
		}
		
	}
	
	@Test
	void timeoutTest() {
		Assertions.assertTimeout(Duration.ofSeconds(5), () -> {
			TimeUnit.SECONDS.sleep(3);
		});
		
	}
	
	
	
	

}
