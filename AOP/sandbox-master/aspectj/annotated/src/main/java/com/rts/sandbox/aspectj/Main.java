package com.rts.sandbox.aspectj;


public class Main {
	
	Money initialAmount1;
	Money initialAmount2;
	static Money toTransfer;
	SecureBank bank;
	static Account account1;
	static Account account2;

	public void setUp() {
		initialAmount1 = new Money(1000);
		initialAmount2 = new Money(100);
		toTransfer = new Money(500);
		bank = new AnnotatedBank();
		account1 = new StandardAccount("account1");
		account2 = new StandardAccount("account2");
		bank.register(account1, initialAmount1);
		bank.register(account2, initialAmount2);
	}
	
	public static void main(String[] args) {
		Main main = new Main();		
		main.setUp();
		main.bank.transfer(account2, account1, toTransfer);

	}

}
