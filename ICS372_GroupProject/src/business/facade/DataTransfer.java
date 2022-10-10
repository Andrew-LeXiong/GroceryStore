package business.facade;

import Buissness.Entities.Member;
import Buissness.Entities.Orders;
import Buissness.Entities.Product;

public class DataTransfer {
	private String memberName;
	private String memberAddress;
	private String memberPhone;
	private String memberId;
	private String productId;
	private String productName;
	private double productPrice;
	private double feePaid;
	private int productReorder;
	private int productQuantity;
	private double priceForUnit;
	private int checkoutQuantity;
	private double totalprice;
	private String orderID;
	private String date;
	private String orderProductId;
	private String orderproductName;
	private int ordercheckoutQuantity ;

	public DataTransfer() {
		reset();
	}

	public String getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(String orderProductId) {
		this.orderProductId = orderProductId;
	}

	public String getOrderproductName() {
		return orderproductName;
	}

	public void setOrderproductName(String orderproductName) {
		this.orderproductName = orderproductName;
	}

	public int getOrdercheckoutQuantity() {
		return ordercheckoutQuantity;
	}

	public void setOrdercheckoutQuantity(int ordercheckoutQuantity) {
		this.ordercheckoutQuantity = ordercheckoutQuantity;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public int getCheckoutQuantity() {
		return checkoutQuantity;
	}

	public void setCheckoutQuantity(String checkoutQuantity) {
		this.checkoutQuantity = Integer.parseInt(checkoutQuantity);
	}

	public double getPriceForUnit() {
		return priceForUnit;
	}

	public void setPriceForUnit(double priceForUnit) {
		this.priceForUnit = priceForUnit;
	}

	public double getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(String string) {
		this.feePaid = Double.parseDouble(string);
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public void setProductPrice(String productPrice) {
		this.productPrice = Double.parseDouble(productPrice);
	}

	public int getProductReorder() {
		return productReorder;
	}

	public void setProductReorder(String productReorder) {
		this.productReorder = Integer.parseInt(productReorder);
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = Integer.parseInt(productQuantity);
	}

	public void setMemberFields(Member member) {
		memberId = member.getId();
		memberName = member.getName();
		memberPhone = member.getPhone();
		memberAddress = member.getAddress();
		feePaid = member.getFeePaid();
	}

	public void setOrderFields(Orders order) {
		orderProductId = order.getOrderID();
		orderproductName = order.getItem().getProductName();
		ordercheckoutQuantity = order.getQuantity();
		date = order.getDate1();
	}

	public void setProductFields(Product product) {
		productId = product.getProductId();
		productQuantity = product.getQuantity();
		productName = product.getProductName();
		productReorder = product.getProductReorder();
		productPrice = product.getProductPrice();
	}

	public void reset() {
		productId = "none";
		productName = "none";
		feePaid = 0;
		productPrice = 0;
		productReorder = 0;
		productQuantity = 0;
		memberId = "none";
		memberName = "none";
		memberPhone = "none";
		memberAddress = "none";
		orderID="none";
		orderProductId ="none";
		orderproductName = "none";
		ordercheckoutQuantity =0;
	}
}
