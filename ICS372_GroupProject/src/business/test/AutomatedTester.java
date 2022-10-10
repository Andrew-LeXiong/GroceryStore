package business.test;



import Buissness.Entities.Member;
import Buissness.Entities.Orders;
import Buissness.Entities.Product;
import business.facade.Request;
import business.facade.Result;
import business.facade.Store;

public class AutomatedTester {
	private Store store;
	private String[] firstnames={"Johnthan Deer", "Brittney trout","Prince King","Denver Thompson","Jackson Kim"};
	private String[] address= {"35th street", "32nd ave", "Easter ave"," Johnson st", "12453 Jackson ave"};
	private String[] phoneNumber= {"303-452-4324", "720-573-4833","651-483-4832","651-465-9482","303-873-3832"};
	private String[] feepaid= {"50","75","25","60","80"};
	private Member[] members= new Member[5];
	private String[] products= {"Milk","Apple","Bannana","Apple Juice","Skim Milk","Fat Free Milk","Coconut Milk",
			"Tomato","Yellow Onion","Bell Pepper","Lucky Charms","Pops","Apple Jacks","Cap'n Crunch","Cookie Crisp",
			"Cheerio","Chex","Choco Crunch","Coco Puffs","Trix"};
	private String [] productId= {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
	private String[] price= {"1.50",".50","1.20","1.50","2.00","1.75","2.10",".75",".66",".99","1.33","1.66","1.50",
			"1.99","1.25","1.60",".99","1.89","1.25","1.75"};
	private String[] reorder= {"75","100","100","50","75","150","30","200","125","150","125","80","90","120","75","100",
			"66","132","150","133"};
	private Product[] product = new Product[20];
	private String[] orderNumbers={"o1","o2","o3","o4","o5","o6","o7","o8","o9","o10"};
	private Orders[] order= new Orders[10];
		public void testaddMember() {
			for (int count = 0; count < members.length; count++) {
				Request.instance().setMemberAddress(address[count]);
				Request.instance().setMemberName(firstnames[count]);
				Request.instance().setMemberPhone(phoneNumber[count]);
				Request.instance().setFeePaid(feepaid[count]);
				business.facade.Result result= Store.instance().addMember(Request.instance());
				assert result.getResultCode() == Result.OPERATION_COMPLETED;
				assert result.getMemberName().equals(firstnames[count]);
				assert result.getMemberPhone().equals(phoneNumber[count]);
			}
		}
		public void testaddProducts(){
			for (int count=0; count<product.length;count ++) {
				Request.instance().setProductId(productId[count]);
				Request.instance().setProductName(products[count]);
				Request.instance().setProductPrice(price[count]);
				Request.instance().setProductReorder(reorder[count]);
				Result result=Store.instance().addProduct(Request.instance());
				if (result.getResultCode() != Result.OPERATION_COMPLETED) {
			          System.out.println("Could not add product");
			      } else {
			          System.out.println(result.getProductName() + "'s id is " + result.getProductId());
			      }
			}
		}
		public void testremoveMember() {
			Request.instance().setMemberId("M1");
			Result result=Store.instance().removeMember(Request.instance());
			if(result.getResultCode() == Result.OPERATION_COMPLETED) {
	    		System.out.println("Member removed");
	    	}
	    	else {
	    		System.out.println("Member not found");
	    	}
		
		}
		public void testproccessShipment() {
			for(int count=0; count < order.length;count++) {
				Request.instance().setOrderID(orderNumbers[count]);
				Result result= Store.instance().processShipment(Request.instance());
				if(result.getResultCode()== Result.OPERATION_COMPLETED) {
					  System.out.println("Shipment processed");
					  System.out.println(result.getProductId() + "\t"  + result.getProductName() + "\t"  + result.getProductPrice()
			  		+ "\t" + result.getProductQuantity() +"\t "+  result.getProductReorder());
				  }
				  else System.out.println("Shipment failed");
			}
			
		}
		public void testChangePrice() {
			Request.instance().setProductId("2");
	    	Request.instance().setProductPrice("1.66");
	    	Result result= Store.instance().changePrice(Request.instance());
	    	
	    	if(result.getResultCode() != Result.OPERATION_COMPLETED) {
	    		System.out.println("product not found");
	    	}
	    	else {
	    		System.out.println("Price changed");
	    		System.out.println(result.getProductId() + "\t"  + result.getProductName() + "\t"  + result.getProductPrice()
	    		+ "\t" + result.getProductQuantity() +"\t "+  result.getProductReorder());
	    		}
	    		
	    	
	    }
		public void TestCheckout() {
			 Request.instance().setMemberId("m2");
			 for(int count=0; count < 6;count++){
			 Request.instance().setProductId(productId[count]);
			  Request.instance().setCheckoutQuantity("6");
			  System.out.println(Request.instance().getCheckoutQuantity());
			  System.out.println(Request.instance().getProductId());
			  Result result= Store.instance().checkout(Request.instance());
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
				}
				}
			 System.out.printf("Total price for cart is $%.2f %n", Request.instance().getTotalprice());
		}
		public void testAll() {
			testaddMember();
			
			testaddProducts();
			testproccessShipment();
			
			testremoveMember();
			testChangePrice();
			TestCheckout();
		}
		public Store getStore() {
			return store;
		}
		public void setStore(Store store) {
			this.store = store;
		}
		public static void main(String[] args) {
			new AutomatedTester().testAll();
		}
}
