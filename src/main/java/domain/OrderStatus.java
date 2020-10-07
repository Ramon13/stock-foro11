package domain;

import entity.Order;

public enum OrderStatus {

	FINALIZED ('F'),
	CANCELED_BY_ADMIN ('C'),
	CANCELED_BY_USER ('U'),
	PENDING('P'),
	RELEASED('R');
	
	private Character value;
	
	OrderStatus(Character value) {
		this.value = value;
	}
	
	public Character getValue() {
		return value;
	}
	
	public static boolean isFinalizedOrder(Order order) {
		return order.getStatus().equals(FINALIZED.getValue());
	}
	
	public static boolean isReleazedOrder(Order order) {
		return order.getStatus().equals(RELEASED.getValue());
	}
}
