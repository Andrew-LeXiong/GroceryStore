package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.StringTokenizer;

import Buissness.Entities.Member;
import Buissness.Entities.Product;
import Buissness.Entities.Transactions;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;

public class UserInterfaces {
	private static UserInterfaces userInterface;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private static Store store;
	private static final int EXIT = 0;
	private static final int Enroll_Member = 1;
	private static final int Remove_Member = 2;
	private static final int Add_Product = 3;
	private static final int Check_Out = 4;
	private static final int Process_Shipment = 5;
	private static final int Change_Price = 6;
	private static final int Retrieve_Product = 7;
	private static final int Retrieve_Member = 8;
	private static final int Get_Transactions = 9;
	private static final int Orders = 10;
	private static final int Get_Members = 11;
	private static final int GET_Products = 12;
	private static final int SAVE = 13;
	private static final int HELP = 14;

	private UserInterfaces() {
		if (yesOrNo("Load bed data  and  use it?")) {
			testbed();
		} else {
			if(yesOrNo("Look for saved data and use it")) {
				retrieve();
			}
			else {
			store = Store.instance();
		}
		}
	}

	public static UserInterfaces instance() {
		if (userInterface == null) {
			return userInterface = new UserInterfaces();
		} else {
			return userInterface;
		}
	}

	public String getName(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				return line;
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	public String getToken(String prompt) {
		do {
			try {
				System.out.println(prompt);
				String line = reader.readLine();
				StringTokenizer tokenizer = new StringTokenizer(line, "\n\r\f");
				if (tokenizer.hasMoreTokens()) {
					return tokenizer.nextToken();
				}
			} catch (IOException ioe) {
				System.exit(0);
			}
		} while (true);
	}

	private void retrieve() {
		try {
			if (store == null) {
				store = Store.retrieve();
				if (store != null) {
					System.out.println(" The store has been successfully retrieved from the file StoreData \n");
				} else {
					System.out.println(" File doesn't exist; creating new Store");
					store = Store.instance();
				}
			}
		} catch (Exception cnfe) {
			cnfe.printStackTrace();
		}
	}
	public void testbed() {
		if(store==null) {
			store=Store.testBed();
			if(store != null) {
				System.out.println("Bed test sucessful");
			}
			else {
				System.out.println("fail");
			}
		}
	}

	private boolean yesOrNo(String prompt) {
		String more = getToken(prompt + " (Y|y)[es] or anything else for no");
		if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
			return false;
		}
		return true;
	}

	/**
	 * 1. Add a member to the store list The while loop will ensure that the
	 * NumberFormatException of user input for member fee is satisfied.
	 */
	public void EnrollMember() {
		while (true) {
			try {
				System.out.println("\n[Enrolling a member]");
				Request.instance().setMemberName(getName("Enter member name"));
				Request.instance().setMemberAddress(getName("Enter address"));
				Request.instance().setMemberPhone(getName("Enter phone"));
				Request.instance().setFeePaid(getName("Enter the fee paid"));
				Result result = store.addMember(Request.instance());

				if (result.getResultCode() != Result.OPERATION_COMPLETED) { // TODO: retest
					System.out.println(" Could not add member.");
				} else {
					System.out.println(" Member, " + result.getMemberName() + ", with the ID " + result.getMemberId()
							+ " has been added to the system.");
				}
				break;
			} catch (NumberFormatException feeValue) {
				System.out.println(" Please enter a numberical value for the member fee.");
				continue;
			}
		}
	}

	/**
	 * 2. This method remove Members from a list;
	 */
	public void removeMember() {
		System.out.println("\n[Remove a member]");
		Request.instance().setMemberId(getName("Enter the member's ID: "));
		Result result = store.instance().removeMember(Request.instance());

		if (result.getResultCode() == Result.OPERATION_COMPLETED) {
			System.out.println(" This member has been removed.");
		} else {
			System.out.println(" Invalid, no member was found with this ID.");
		}
	}

	/**
	 * 3. Add a product to the store
	 */
	public void addProduct() { // TODO: reorder
		while (true) {
			try {
				System.out.println("\n[Add a product]");
				Request.instance().setProductId(getName("Enter product ID"));
				Request.instance().setProductName(getName("Enter product name"));
				Request.instance().setProductPrice(getName("Enter product price"));
				Request.instance().setProductQuantity(getName("Enter the product stock at hand"));
				Request.instance().setProductReorder(getName("Enter the product reorder amount"));
				Result result = store.addProduct(Request.instance());

				if (result.getResultCode() != Result.OPERATION_COMPLETED) { // TODO: retest
					System.out.println(" Could not add product.");
				} else {
					System.out.println(" Product has been successfully added to the system.");
				}
				break;
			} catch (NumberFormatException numberValue) {
				System.out.println(" Please enter a numberical value for the specific fields.");
				continue;
			}
		}
	}

	/**
	 * 4. Checks out the member's items
	 */
	public void Checkout() { // TODO: double check
		System.out.println("\n[Checkout member items]");
		Request.instance().setMemberId(getName("Enter the member ID"));
		Request.instance().setTotalprice(0);
			do {
				Request.instance().setProductId(getName("Enter the product id"));
				Request.instance().setCheckoutQuantity(getName("Enter the amount of item"));
				Result result = store.checkout(Request.instance());
				if (result.getResultCode() == Result.NO_SUCH_MEMBER) {
					System.out.println(" Member not found in system.");
				}
				if (result.getResultCode() == Result.ITEM_NOT_FOUND) {
					System.out.println(" Item not found.");
					break;
				}

				if (result.getResultCode() == Result.OPERATION_FAILED) {
					System.out.println(" We are low on stock, check out too many.");
				} else {
					System.out.printf("%-20s %-10s %-10s %-10s %n", "Item", "Quantity", "Price", "Total");
					System.out.printf("%-20s %-10d $%-10.2f $%-10.2f %n", result.getProductName(),
							Request.instance().getCheckoutQuantity(), result.getProductPrice(),
							result.getPriceForUnit(), result.getTotalprice());
					Request.instance().setTotalprice(result.getPriceForUnit() + Request.instance().getTotalprice());
					if(!result.getOrderProductId().equalsIgnoreCase("none")) {
						System.out.println("Products reorder! [order number, name, quantity]");
						System.out.println(result.getOrderProductId() +"\t"+ result.getOrderproductName() +"\t"+ result.getOrdercheckoutQuantity());
					}
				}
			} while (yesOrNo("Checkout more items"));
			System.out.printf("Total price for cart is $%.2f %n", Request.instance().getTotalprice());
			// TODO: system reorders and display message
		}
	

	/**
	 * 5. Process Shipment
	 */
	public void processShipment() { // TODO: Double check
		System.out.println("\n[Process Shipments]");
		do {
			Request.instance().setOrderID(getName("Enter the order number"));
			Result result = store.processShipment(Request.instance());

			if (result.getResultCode() == Result.OPERATION_COMPLETED) {
				System.out.println(" Shipment processed.");
				System.out.println(
						result.getProductId() + "\t" + result.getProductName() + "\t" + result.getProductPrice() + "\t"
								+ result.getProductQuantity() + "\t " + result.getProductReorder());
			} else
				System.out.println(" Processing shipment failed.");
		} while (yesOrNo("Process more shipments?"));
	}

	/**
	 * 6. Change the price to an item
	 */
	public void priceChange() {
		System.out.println("\n[Changing the price of a product]");
		Request.instance().setProductId(getName("Enter the product ID"));
		Request.instance().setProductPrice(getName("Enter the new price of the product"));
		Result result = store.changePrice(Request.instance());

		if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			System.out.println(" Product not found.");
		} else {
			System.out.println(" The price has been successfully changed.");
			System.out.printf("%-20s $%-5.2f %n", result.getProductName(), result.getProductPrice());
		}
	}

	/**
	 * 7. Retrieves all the products that contain a certain string.
	 */
	public void retrieveProducts() {
		System.out.println("\n[Retrieving product info]");
		Request.instance().setProductName(getName("Enter product name"));
		Iterator<Product> result = store.searchProducts(Request.instance());
		System.out.println("\nGetting products with the name " + Request.instance().getProductName() + ":");
		System.out.printf("%-10s %-20s %-10s %-10s %-10s %n", "ID", "Name", "Price", "In-stock", "Reorder level");

		while (result.hasNext()) {
			Product product = (Product) result.next();
			System.out.printf("%-10s %-20s $%-10.2f %-10d %-10d %n", product.getProductId(), product.getProductName(),
					product.getProductPrice(), product.getQuantity(), product.getProductReorder());
		}
		System.out.println(" End of product listings.");
	}

	/**
	 * 8. This method retrieves all members that have a certain string in their name
	 */
	public void retrieveMember() {
		System.out.println("\n[Retrieving member info]");
		Request.instance().setMemberName(getName("Enter member name"));
		Iterator<Member> result = store.searchMembership(Request.instance());
		System.out.println("\nGetting members with the name " + Request.instance().getMemberName() + ":");
		System.out.printf("%-5s %-20s %-30s %-15s %-5s %n", "ID", "Name", "Address", "Phone", "FeePaid");

		while (result.hasNext()) {
			Member member = (Member) result.next();
			System.out.printf("%-5s %-20s %-30s %-15s $%-5.2f %n", member.getId(), member.getName(),
					member.getAddress(), member.getPhone(), member.getFeePaid());
		}
		System.out.println(" End of member listings.");
	}

	/**
	 * 9. Get all the transactions of all the members between to specific dates.
	 */
	public void getTransactions() { // TODO: double check
		System.out.println("\n[Printing member's transactions]");
		Request.instance().setMemberId(getName("Enter member id"));
		Request.instance().setDate(getDate("Enter the first date as dd/mm/yy"));
		Request.instance().setDate2(getDate("Enter the second date as dd/mm/yy"));
		Iterator<Transactions> result = store.getTransactions(Request.instance());
		System.out.println("Orderd by (Date, Product name, Quantity, Price per unit, Total price)");

		while (result.hasNext()) {
			Transactions transaction = (Transactions) result.next();
			System.out.println(
					transaction.getDate1() + "  " + transaction.getItemName() + "   " + transaction.getQuantity()
							+ "   " + +transaction.getPrice() + "   " + transaction.getTotalPrice() + "\n");
		}
		System.out.println(" End of transactions.");
	}

	/**
	 * The formatter to make sure that when a date is entered it is entered properly
	 * 
	 * @param prompt
	 * @return
	 */
	public Calendar getDate(String prompt) {
		do {
			try {
				Calendar date = new GregorianCalendar();
				String item = getToken(prompt);
				DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.SHORT);
				date.setTime(dateFormat.parse(item));
				return date;
			} catch (Exception dateInput) {
				System.out.println("Please input a date as mm/dd/yy");
			}
		} while (true);
	}

	/**
	 * 10. Returns a list of orders that has not yet been fulfilled
	 */
	public void getOrders() { // TODO: double check
		Iterator<Result> iterator = store.getOrders();
		System.out.println("\n[Orders List]");
		System.out.printf("%-10s %-20s %-10s %-20s %n", "Order #", "Product", "Quantity", "date");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.printf("%-10s %-20s %-10d %20s %n", result.getOrderProductId(), result.getOrderproductName(),
					result.getOrdercheckoutQuantity(),result.getDate());
			
		}
		System.out.println(" End of outstanding orders listing.");
	}

	/**
	 * 11. Prints all the members that are registered with the store
	 */
	public void getMembers() { // TODO: add date join
		Iterator<Result> iterator = store.getMembers();
		System.out.println("\n[List of members]");
		System.out.printf("%-5s %-20s %-30s %-15s %-20s %n", "ID", "Name", "Address", "Phone","date Joined");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.printf("%-5s %-20s %-30s %-15s %20s %n", result.getMemberId(), result.getMemberName(),
					result.getMemberAddress(), result.getMemberPhone(),result.getDate());
		}
		System.out.println(" End of member listings.");
	}

	/**
	 * 12. Prints all the products in the store
	 */
	public void getProducts() {
		Iterator<Result> iterator = store.getProducts();
		System.out.println("\n[List of products]");
		System.out.printf("%-10s %-20s %-10s %-10s %-10s %n", "ID", "Name", "Price", "In-stock", "Reorder level");
		while (iterator.hasNext()) {
			Result result = iterator.next();
			System.out.printf("%-10s %-20s $%-10.2f %-10d %-10d %n", result.getProductId(), result.getProductName(),
					result.getProductPrice(), result.getProductQuantity(), result.getProductReorder());
		}
		System.out.println(" End of product listings.");
	}

	/**
	 * 13. Save data to system.
	 */
	private void save() {
		if (store.save()) {
			System.out.println(" Data has been successfully saved.");
		} else {
			System.out.println(" Error, data was not saved.");
		}
	}

	/**
	 * 14. Display all commands.
	 */
	public void help() {
		System.out.println("Enter a number between 0 and 12 as explained below:");
		System.out.println(EXIT + " to Exit\n");
		System.out.println(Enroll_Member + " to add a member");
		System.out.println(Remove_Member + " to  remove a member");
		System.out.println(Add_Product + " to  add a product");
		System.out.println(Check_Out + " to  Checkout ");
		System.out.println(Process_Shipment + " to  Process a Shipment ");
		System.out.println(Change_Price + " to  Change price of an object ");
		System.out.println(Retrieve_Product + " to  Retrieve a product info");
		System.out.println(Retrieve_Member + " to  Retrieve Member info ");
		System.out.println(Get_Transactions + " to  print transactions");
		System.out.println(Orders + " to  get all unfullfilled orders ");
		System.out.println(Get_Members + " to  print all members");
		System.out.println(GET_Products + " to  print all products");
		System.out.println(SAVE + " to  save data");
		System.out.println(HELP + " for help");
	}

	public int getCommand() {
		do {
			try {
				int value = Integer.parseInt(getToken("\nEnter command: " + "\n[enter " + HELP + " for help]"));
				if (value >= EXIT && value <= HELP) {
					return value;
				}
			} catch (NumberFormatException numberException) {
				System.out.println(" Invalid, please enter a number value.");
			}
		} while (true);
	}

	public void Process() {
		int command;
		help();
		while ((command = getCommand()) != EXIT) {
			switch (command) {
			case Enroll_Member:
				EnrollMember();
				break;
			case Remove_Member:
				removeMember();
				break;
			case Add_Product:
				addProduct();
				break;
			case Check_Out:
				Checkout();
				break;
			case Process_Shipment:
				processShipment();
				break;
			case Change_Price:
				priceChange();
				break;
			case Retrieve_Product:
				retrieveProducts();
				break;
			case Retrieve_Member:
				retrieveMember();
				break;
			case Get_Transactions:
				getTransactions();
				break;
			case Orders:
				getOrders();
				break;
			case Get_Members:
				getMembers();
				break;
			case GET_Products:
				getProducts();
				break;
			case SAVE:
				save();
				break;
			case HELP:
				help();
				break;
			}
		}
	}

	public static void main(String[] args) {
		UserInterfaces.instance().Process();
	}
}
