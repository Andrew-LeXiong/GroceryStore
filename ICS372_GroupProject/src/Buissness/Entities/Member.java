package Buissness.Entities;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import business.iterator.FilterdIterator;

public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String address;
	private String phone;
	private String id;
	private double feePaid;
	private Calendar date;
	private static int idCounter;
	private static final String Member_String = "M";
	private String date1;
	private List<Transactions> transactions = new LinkedList<Transactions>();

	public Member(String name, String address, String phone, double fee) {
		feePaid = fee;
		this.name = name;
		this.address = address;
		this.phone = phone;
		id = Member_String + ++idCounter;
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

	public double getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(double feePaid) {
		this.feePaid = feePaid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Member.idCounter = idCounter;
	}

	public static String getMemberString() {
		return Member_String;
	}

	public void Checkout(Product product, int amount) {
		transactions.add(new Transactions(product, amount));
		System.out.println("Printing the transactions...");
	}

	public Iterator<Transactions> getTransactionsOnDate(Calendar date, Calendar date2) {
		return new FilterdIterator(transactions.iterator(), transactions -> transactions.onDate(date, date2));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Member other = (Member) object;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public static void save(ObjectOutputStream output) throws IOException {
		output.writeObject(idCounter);
	}

	public static void retrieve(ObjectInputStream input) throws IOException, ClassNotFoundException {
		idCounter = (int) input.readObject();
	}

}
