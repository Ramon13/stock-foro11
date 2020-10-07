package domain.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import entity.OrderItem;

public class OrderItemsSerializer implements JsonSerializer<List<OrderItem>> {

	@Override
	public JsonElement serialize(List<OrderItem> src, Type typeOfSrc, JsonSerializationContext context) {

		JsonObject jsonOrderItem = null;
		JsonObject jsonItem = null;
		JsonObject jsonOrder = null;
		JsonArray jsonArray = new JsonArray();
		for (OrderItem oi : src) {
			jsonOrderItem = new JsonObject();
			jsonOrderItem.addProperty("id", oi.getId());
			jsonOrderItem.addProperty("amount", oi.getAmount());
				
			jsonItem = new JsonObject();
			jsonItem.addProperty("id", oi.getItem().getId());
			jsonItem.addProperty("name", oi.getItem().getName());
			jsonItem.addProperty("mainImage", oi.getItem().getMainImage());
			jsonItem.addProperty("amount", oi.getItem().getAmount());
			
			JsonArray monthArray = new JsonArray();
			for (Integer i : oi.getItem().getSumByMonth()) {
				monthArray.add(i);
			}
			jsonItem.add("sumByMonth", monthArray);
			
			jsonOrder = new JsonObject();
			jsonOrder.addProperty("status", oi.getOrder().getStatus());
			jsonOrder.addProperty("finalDate", oi.getOrder().getFinalDate().toString());
			
			jsonOrderItem.add("item", jsonItem);
			jsonOrderItem.add("order", jsonOrder);
			jsonArray.add(jsonOrderItem);
		}
		
		return jsonArray;
	}


}
