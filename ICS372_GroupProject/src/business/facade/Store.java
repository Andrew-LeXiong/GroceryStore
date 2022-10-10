package business.facade;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Buissness.Entities.Member;
import Buissness.Entities.Orders;
import Buissness.Entities.Product;
import Buissness.Entities.Transactions;
import business.iterator.SafeIterator;
import business.test.AutomatedTester;

public class Store implements Serializable {
	private static final long serialVersionUID = 1L;
	private static AutomatedTester test =new AutomatedTester();
	private ProductList listOfProducts = new ProductList();
	private MemberList listOfMembers = new MemberList();
	private orderList listOfOrders = new orderList();
	private static Store store;

	private class ProductList implements Iterable<Product>, Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private List<Product> listOfProducts = new LinkedList<Product>();

		@Override
		public Iterator<Product> iterator() {
			return listOfProducts.iterator();
		}

		public Orders reorder(orderList list, Product product) {
			if (product.checkreorder()) {
				Orders returnOrder=list.insert(product);
				return returnOrder;
			}
			else 
				return null;
		}

		public boolean insertProduct(Product newProduct) {
			listOfProducts.add(newProduct);
			return true;
		}

		public boolean changeQuanity(Orders order) {
			Product product = this.searchID(order.getItem().getProductId());
			if (product != null) {
				product.setQuantity(product.getQuantity() + order.getQuantity());
				return true;
			}
			return false;
		}

		public Product search(String newProduct) {
			for (Iterator<Product> iterator = listOfProducts.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				if (product.getProductName().equalsIgnoreCase(newProduct)) {
					return product;
				}
			}
			return null;
		}

		public Product searchID(String newProduct) {
			for (Iterator<Product> iterator = listOfProducts.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				if (product.getProductId().equalsIgnoreCase(newProduct)) {
					return product;
				}
			}
			return null;
		}

		public Product changePrice(String Id, double price) {
			Product product = this.searchID(Id);
			if (product != null) {
				product.setProductPrice(price);
				return product;
			}
			return null;
		}

		public List<Product> searchAll(String productName) {
			List<Product> productNames = new LinkedList<Product>();
			for (Iterator<Product> iterator = listOfProducts.iterator(); iterator.hasNext();) {
				Product product = iterator.next();
				if (product.getProductName().toLowerCase().contains(productName.toLowerCase())) {
					productNames.add(product);
				}
			}
			return productNames;
		}

		public boolean CheckOut(String productID, String MemberID, MemberList list, int quantity, orderList olist) {
			Member member = list.searchID(MemberID);
			if (member != null) {
				Product product = this.searchID(productID);
				if (product != null) {
					if (product.getQuantity() >= quantity) {
						product.setQuantity(product.getQuantity() - quantity);
						member.Checkout(product, quantity);
						return true;
					}
				}
			}
			return false;
		}
	}

	private class MemberList implements Iterable<Member>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Member> members = new LinkedList<Member>();

		@Override
		public Iterator<Member> iterator() {
			return members.iterator();
		}

		public boolean insertMember(Member member) {
			members.add(member);
			return true;
		}

		public Member search(String memberName) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getName().equalsIgnoreCase(memberName)) {
					return member;
				}
			}
			return null;
		}

		public Member searchID(String memberName) {
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getId().equalsIgnoreCase(memberName)) {
					return member;
				}
			}
			return null;
		}

		public boolean removeFrom(String memberId) {
			/**
			 * 
			 * for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
			 * Member member = iterator.next(); if
			 * (member.getId().equalsIgnoreCase(memberName)) { members.remove(member);
			 * return true; } } return false;
			 */
			Member member = this.searchID(memberId);
			if (member != null) {
				members.remove(member);
				return true;
			} else
				return false;
		}

		public List<Member> searchAll(String memberID) {
			List<Member> membersName = new LinkedList<Member>();
			for (Iterator<Member> iterator = members.iterator(); iterator.hasNext();) {
				Member member = iterator.next();
				if (member.getName().toLowerCase().contains(memberID.toLowerCase())) {
					membersName.add(member);
				}
			}
			return membersName;
		}
	}

	private class orderList implements Iterable<Orders>, Serializable {
		private static final long serialVersionUID = 1L;
		private List<Orders> listOrders = new LinkedList<Orders>();

		@Override
		public Iterator<Orders> iterator() {
			return listOrders.iterator();

		}

		public Orders insert(Product product) {
			Orders order= new Orders(product);
			listOrders.add(order);
			return order;

		}

		public Orders searchOrder(String orderID) {
			for (Iterator<Orders> iterator = listOfOrders.iterator(); iterator.hasNext();) {
				Orders order = iterator.next();
				if (order.getOrderID().equalsIgnoreCase(orderID)) {
					return order;
				}
			}
			return null;
		}

		public void removeOrder(String order) {
			Orders orders = this.searchOrder(order);
			if (orders != null) {
				listOrders.remove(orders);
			}
		}
	}

	private Store() {
	}

	/**
	 * Supports the singleton pattern
	 * 
	 * @return the singleton object
	 */

	public static Store instance() {
		if (store == null) {
			return store = new Store();
		} else {
			return store;
		}
	}

	public Result addMember(Request request) {
		Result result = new Result();
		Member member = new Member(request.getMemberName(), request.getMemberAddress(), request.getMemberPhone(),
				request.getFeePaid());
		if (listOfMembers.insertMember(member)) {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setMemberFields(member);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	public Result addProduct(Request request) {
		Result result = new Result();
		Product product = new Product(request.getProductId(), request.getProductName(), request.getProductPrice(),
				request.getProductQuantity(), request.getProductReorder());
		if (listOfProducts.search(request.getProductName()) == null) {
			if (listOfProducts.insertProduct(product)) {
				listOfProducts.reorder(listOfOrders, product);
				result.setResultCode(Result.OPERATION_COMPLETED);
				result.setProductFields(product);
				return result;
			}
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * This method changes the prices of an item
	 * 
	 * @param request store instance used to get the product it
	 * @return Returns if the operation is successful or failed.
	 */
	public Result changePrice(Request request) {
		Result result = new Result();
		Product product = listOfProducts.changePrice(request.getProductId(), request.getProductPrice());
		if (product == null) {
			result.setResultCode(Result.OPERATION_FAILED);
		} else {
			result.setResultCode(Result.OPERATION_COMPLETED);
			result.setProductFields(product);
		}
		return result;
	}

	/**
	 * The method that returns a linked list of transaction
	 * 
	 * @param requeststore instance used to get the memberID
	 * @return returns a list of transations fallen between dates.
	 */
	public Iterator<Transactions> getTransactions(Request request) {
		Member member = listOfMembers.searchID(request.getMemberId());
		if (member == null) {
			return new LinkedList<Transactions>().iterator();
		}
		return member.getTransactionsOnDate(request.getDate1(), request.getDate2());
	}

	/**
	 * This method is to check out a certain product
	 * 
	 * @param request Uses the store instance and will use productId to find the
	 *                product and the memeber Id to find the right member
	 * @return Returns if the opertaion is completed, item not found, or operation
	 *         failed
	 */
	public Result checkout(Request request) {
		Result result = new Result();
		Member member = listOfMembers.searchID(request.getMemberId());
		Product product = listOfProducts.searchID(request.getProductId());
		Boolean subtract = listOfProducts.CheckOut(request.getProductId(), request.getMemberId(), listOfMembers,
				request.getCheckoutQuantity(), listOfOrders);

		if (member == null) {
			result.setResultCode(Result.NO_SUCH_MEMBER);
			return result;
		}
		if (product == null) {
			result.setResultCode(Result.ITEM_NOT_FOUND);
			return result;
		}
		if (!subtract) {
			result.setResultCode(Result.OPERATION_FAILED);
			return result;
		} else {
			Orders order=listOfProducts.reorder(listOfOrders, product);
			if(order != null) {
			result.setOrderFields(order);
			}
			double price = product.getProductPrice() * request.getCheckoutQuantity();
			result.setProductFields(product);
			result.setPriceForUnit(product.getProductPrice() * request.getCheckoutQuantity());
			result.setTotalprice(request.getTotalprice() + price);
			result.setResultCode(Result.OPERATION_COMPLETED);
			return result;
		}
	}

	/**
	 * Removes a member from the members list
	 * 
	 * @param request store instance as request to get the membersId from it
	 * @return if the operation is complete or not.
	 */
	public Result removeMember(Request request) {
		Result result = new Result();
		if (listOfMembers.removeFrom(request.getMemberId())) {
			result.setResultCode(Result.OPERATION_COMPLETED);
		} else
			result.setResultCode(Result.NO_SUCH_MEMBER);
		return result;
	}

	/**
	 * This method processes the shipment.
	 * 
	 * @param request Takes in the store instance as a request and get the order ID
	 *                from it
	 * @return returns if the operation is completed or not
	 */
	public Result processShipment(Request request) {
		Result result = new Result();
		Orders orders = listOfOrders.searchOrder(request.getOrderID());
		if (orders != null) {
			if (listOfProducts.changeQuanity(orders)) {
				result.setResultCode(Result.OPERATION_COMPLETED);
				result.setProductFields(listOfProducts.searchID(orders.getItem().getProductId()));
				listOfOrders.removeOrder(orders.getOrderID());
				return result;
			}
		}
		result.setResultCode(Result.OPERATION_FAILED);
		return result;
	}

	/**
	 * This method search all the products that have a certain string in their name
	 * 
	 * @param request takes in the request for the store
	 * @return Returns a list iterator of products that contain certain string in
	 *         their name;
	 */
	public Iterator<Product> searchProducts(Request request) {
		Result result = new Result();
		List<Product> productsWithName = listOfProducts.searchAll(request.getProductName());
		return productsWithName.iterator();
	}

	/**
	 * This method searchs all the memebers that have a certain string in their
	 * name;
	 * 
	 * @param request It takes in the request for the store
	 * @return Returns a list iterator of the members that fall into that certain
	 *         specfic string.
	 */
	public Iterator<Member> searchMembership(Request request) {
		Result result = new Result();
		List<Member> memberList = listOfMembers.searchAll(request.getMemberName());
		return memberList.iterator();
	}

	/**
	 * Method to get a list of unfullfilled orders.
	 * 
	 * @return
	 */
	public Iterator<Result> getOrders() {
		return new SafeIterator<Orders>(listOfOrders.iterator(), SafeIterator.ORDER);
	}

	/**
	 * Method to return a list of products
	 * 
	 * @return
	 */
	public Iterator<Result> getProducts() {
		return new SafeIterator<Product>(listOfProducts.iterator(), SafeIterator.PRODUCT);
	}

	/**
	 * Method to return the list of Members
	 * 
	 * @return
	 */
	public Iterator<Result> getMembers() {
		return new SafeIterator<Member>(listOfMembers.iterator(), SafeIterator.MEMBER);
	}

	/**
	 * Retrieves a store instance if there exist one
	 * 
	 * @return returns the store if found if not returns null
	 */
	public static Store retrieve() {
		try {
			FileInputStream file = new FileInputStream("StoreData");
			ObjectInputStream input = new ObjectInputStream(file);
			store = (Store) input.readObject();
			Member.retrieve(input);
			return store;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
			return null;
		}
	}

	/**
	 * This method saves the Store instance
	 * 
	 * @return a boolean based on if we are able to save the store instance or not
	 */
	public static boolean save() {
		try {
			FileOutputStream file = new FileOutputStream("StoreData");
			ObjectOutputStream output = new ObjectOutputStream(file);
			output.writeObject(store);
			Member.save(output);
			file.close();
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
	}
	public static Store testBed() {
		test.testAll();
		store=test.getStore().instance();
		return store;
	}

}
