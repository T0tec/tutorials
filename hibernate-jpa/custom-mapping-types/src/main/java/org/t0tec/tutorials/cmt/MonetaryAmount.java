package org.t0tec.tutorials.cmt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class MonetaryAmount implements Serializable {
	private final BigDecimal value;
	private final Currency currency;

	public MonetaryAmount(BigDecimal value, Currency currency) {
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public Currency getCurrency() {
		return currency;
	}

	public static MonetaryAmount fromString(String amount, String currencyCode) {
		return new MonetaryAmount(new BigDecimal(amount), Currency.getInstance(currencyCode));
	}

	public static MonetaryAmount convert(MonetaryAmount amount, Currency toConcurrency) {
		// TODO: This requires some conversion magic and is not correctly implemented: 1.00EUR=1.222927USD (21/12/2014)
		if (amount.getCurrency().equals(Currency.getInstance("EUR"))) {
			BigDecimal bd = (amount.getValue()).multiply(new BigDecimal(1.222927));
			return new MonetaryAmount(bd, toConcurrency);
		}
		
		return new MonetaryAmount(amount.getValue(), toConcurrency);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof MonetaryAmount))
			return false;

		final MonetaryAmount monetaryAmount = (MonetaryAmount) o;

		if (!currency.equals(monetaryAmount.currency))
			return false;
		if (!value.equals(monetaryAmount.value))
			return false;

		return true;
	}
}