package Buissness.Entities;

import java.io.Serializable;

public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	private String productId;
	private String productName;
	private double productPrice;
	private int productReorder;
	private int quantity;

	public Product(String productID, String name, double price, int quantity, int reorder) {
		productId = productID;
		productName = name;
		productPrice = price;
		productReorder = reorder;
		this.quantity = quantity;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double price) {
		this.productPrice = price;
	}

	public int getProductReorder() {
		return productReorder;
	}

	public void setProductReorder(int productReorder) {
		this.productReorder = productReorder;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public boolean checkreorder() {
		if (this.getQuantity() < this.getProductReorder()) {

			return true;
		} else
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
		Product other = (Product) object;
		if (productId == null) {
			if (other.productId != null) {
				return false;
			}
		} else if (!productId.equals(other.productId)) {
			return false;
		}
		return true;
	}
}
