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
	
	public static OrderStatus getByValue(Character value) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.getValue() == value)
				return status;
		}
		
		return PENDING;
	}
	
	public static boolean isFinalizedOrder(Order order) {
		return order.getStatus().equals(FINALIZED.getValue());
	}
	
	public static boolean isReleasedOrder(Order order) {
		return order.getStatus().equals(RELEASED.getValue());
	}
	
	public static boolean isFinalizedOrReleased(Order order) {
		return isFinalizedOrder(order) || isReleasedOrder(order);
	}
	
	public static boolean isNotCanceledOrder(Order order) {
		return order.getStatus().equals(CANCELED_BY_ADMIN.getValue()) == Boolean.FALSE
				&& order.getStatus().equals(CANCELED_BY_USER.getValue()) == Boolean.FALSE;
	}
	
}
