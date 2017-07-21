package player;

/**
 * A resource which passively increases over time. Examples are Health and Mana.
 * 
 * @author Thomas Edwards
 *
 */
public abstract class RechargableResource {

	private double maxAmount, currentAmount;
	private double regenRate;// regen rate in seconds

	public RechargableResource(double maxAmount, double currentAmount, double regenRate) {
		this.maxAmount = maxAmount;
		this.currentAmount = currentAmount;
		this.regenRate = regenRate;
	}

	public void regen() {
		currentAmount += regenRate;
		if (currentAmount > maxAmount)
			currentAmount = maxAmount;
	}

	public double getRegenRate() {
		return regenRate;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public double getCurrentAmount() {
		return currentAmount;
	}

	public void increaseRegenRate(double regenRateIncrease) {
		this.regenRate += regenRateIncrease;
	}

	public void increaseMaxAmount(double maxAmountIncrease) {
		this.maxAmount += maxAmountIncrease;
	}

	public void increaseCurrentAmount(double currentAmountIncrease) {
		this.currentAmount = currentAmountIncrease;
		if (currentAmount > maxAmount)
			currentAmount = maxAmount;
	}

	public void decreaseRegenRate(double regenRateDecrease) {
		this.regenRate -= regenRateDecrease;
		if (regenRate < 0)
			regenRate = 0;
	}

	public void decreaseMaxAmount(double maxAmountDecrease) {
		this.maxAmount += maxAmountDecrease;
		if (this.maxAmount < 1)
			maxAmount = 1;// least 'Max' amount of any resource is 1
	}

	public void decreaseCurrentAmount(double currentAmountDecrease) {
		this.currentAmount = currentAmountDecrease;
		if (currentAmount < 0)
			currentAmount = 0;
	}

}
