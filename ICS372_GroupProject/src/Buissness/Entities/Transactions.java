package Buissness.Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Transactions implements Serializable {
	private static final long serialVersionUID = 1L;
	private String itemName;
	private int quantity;
	private double price;
	private double totalPrice;
	private Calendar date;
	private String date1;

	public Transactions(Product product, int amount) {
		itemName = product.getProductName();
		price = product.getProductPrice();
		quantity = amount;
		totalPrice = product.getProductPrice() * quantity;
		date = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
		date1 = sdf.format(date.getTime());
	}

	public String getDate1() {
		return date1;
	}

	public void setDate1(String date1) {
		this.date1 = date1;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean onDate(Calendar date1, Calendar date2) {
		// This checks just if the year is less than date2 and greate than date 2
		if ((this.date.get(Calendar.YEAR) > date1.get(Calendar.YEAR)
				&& (this.date.get(Calendar.YEAR) < date2.get(Calendar.YEAR)))) {
			return true;
		}
		// IF statements check if the year is equal to the first date then check to make
		// sure the month is greater
		else if ((this.date.get(Calendar.YEAR) == date1.get(Calendar.YEAR))) {
			if (this.date.get(Calendar.MONTH) > date1.get(Calendar.MONTH)) {
				return true;
			}
		}
		// If statement checks if the year is equal to the second date then check to
		// make sure the date month is less than the second date
		else if ((this.date.get(Calendar.YEAR) == date2.get(Calendar.YEAR))) {
			if (this.date.get(Calendar.MONTH) < date2.get(Calendar.MONTH)) {
				return true;
			}
		}
		// if the year and months is equal to date 1 check if the day is greater than
		// date1;
		else if (this.date.get(Calendar.YEAR) == date1.get(Calendar.YEAR)
				&& (this.date.get(Calendar.MONTH)) == date1.get(Calendar.MONTH)) {
			if (this.date.get(Calendar.DATE) > date1.get(Calendar.DATE)) {
				return true;
			}
		}
		// if the year and month is equal to date 2 check to see if the day is less than
		// date2
		else if (this.date.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
				&& (this.date.get(Calendar.MONTH)) == date2.get(Calendar.MONTH)) {
			if (this.date.get(Calendar.DATE) < date2.get(Calendar.DATE)) {
				return true;
			}
		}

		return false;
	}

	public Calendar getDate() {
		return date;
	}

}
