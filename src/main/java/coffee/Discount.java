package coffee;

public class Discount {
	private String discountID;
	private double percentage;

	public Discount(String discountID, double percentage) throws InvalidDataException {
	
		if (percentage < 0 || percentage > 100) {
			throw new InvalidDataException("Discount percentage must be between 0 and 100");
		}
		this.discountID = discountID;
		this.percentage = percentage;
	}

	public boolean isApplicable() {
		return percentage > 0;
	}

	public void applyDiscount(Order order) {
		if (isApplicable()) {
			double originalAmount = order.calculateAmount();
			double discountAmount = originalAmount * (percentage / 100);
			order.setFinalAmount(originalAmount - discountAmount);
		}
	}

	public String getDiscountID() {
		return discountID;
	}

	public void setDiscountID(String discountID) throws InvalidDataException {
		if (discountID == null || discountID.trim().isEmpty()) {
			throw new InvalidDataException("Discount ID cannot be empty");
		}
		this.discountID = discountID;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) throws InvalidDataException {
		if (percentage < 0 || percentage > 100) {
			throw new InvalidDataException("Discount percentage must be between 0 and 100");
		}
		
		this.percentage = percentage;
	}
}
