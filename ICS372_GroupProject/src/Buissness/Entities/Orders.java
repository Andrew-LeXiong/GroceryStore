package Buissness.Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Orders implements Serializable {
	private static final long serialVersionUID = 1L;
	private Product item;
	private String orderID;
	private int quantity;
	private Calendar date;
	private String date1;
	private static int orderCounter;
	private static final String Order_String = "O";

	public Orders(Product item) {
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
		this.item = item;
		quantity = item.getProductReorder() * 2;
		orderID = Order_String + ++orderCounter;
		date = new GregorianCalendar();
		date1 = sdf.format(date.getTime());
	}

	public Calendar getDate() {
		return date;
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getItem() {
		return item;
	}

	public void setItem(Product item) {
		this.item = item;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((orderID == null) ? 0 : orderID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (getClass() != object.getClass()) {
			return false;
		}
		Orders other = (Orders) object;
		if (orderID == null) {
			if (other.orderID != null) {
				return false;
			}
		} else if (!orderID.equals(other.orderID)) {
			return false;
		}
		return true;
	}

	/**
	 * Display the order id, corresponding product name, date of order, and quantity
	 * ordered.
	 */
	@Override
	public String toString() {
		return "Order [Order id =" + orderID + ", Product =" + item.getProductName() + ", date of order="
				+ ", quantity ordered =" + "]";
	}
}
