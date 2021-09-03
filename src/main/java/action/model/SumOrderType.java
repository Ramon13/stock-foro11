package action.model;

public enum SumOrderType {
	BY_ITEM,
	BY_LOCALE;
	
	public static SumOrderType fromString(String sumOrderTypeStr) {
		for (SumOrderType type : SumOrderType.values())
			if (type.toString().equalsIgnoreCase(sumOrderTypeStr))
				return type;
		
		throw new IllegalStateException("The type was not found");
	}
}
