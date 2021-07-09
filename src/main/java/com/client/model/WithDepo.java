package com.client.model;

public class WithDepo {

	private double withdraw;
	private double deposit;

	public WithDepo(double withdraw, double deposit) {
		super();
		this.withdraw = withdraw;
		this.deposit = deposit;
	}

	public WithDepo() {
		super();
	}

	public double getWithdraw() {
		return withdraw;
	}

	public void setWithdraw(double withdraw) {
		this.withdraw = withdraw;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

}
