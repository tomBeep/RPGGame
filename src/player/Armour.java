package player;

public class Armour {

	double amount = 0;

	public Armour() {
		// TODO
	}

	public double getArmour() {

		return amount;
	}

	public void setArmour(double amount) {

		this.amount = amount;
	}

	/**
	 * Method which calculates the amount of reduction which armor causes. ATM it is simply damage/armour = final
	 * 
	 * @param incomingDamage
	 * @return the damage after reductions in armour.
	 */
	public double reduceDamage(double incomingDamage) {

		return incomingDamage / amount;
	}

}
