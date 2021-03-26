package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
	
	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate futureDate = LocalDate.now().plusYears(10L);
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(futureDate));
	}

	@Test
	public void deveRetornarTrueParaDataAtual() {
		LocalDate presentDate = LocalDate.now();
		Assert.assertTrue(DateUtils.isEqualOrFutureDate(presentDate));
	}

	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		LocalDate pastDate = LocalDate.now().minusYears(10L);
		Assert.assertFalse(DateUtils.isEqualOrFutureDate(pastDate));
	}

}
