package business.facade;

import java.util.Calendar;

public class Request extends DataTransfer{
	private static Request request;
	private Calendar date1;
	private Calendar date2;
	/**
	 * This is a singleton class. Hence the private constructor.
	 */
	private Request() {

	}

	/**
	 * Returns the only instance of the class.
	 * 
	 * @return the only instance
	 */
	public static Request instance() {
		if (request == null) {
			request = new Request();
		}
		return request;
	}

	public Calendar getDate1() {
		return date1;
	}

	public void setDate(Calendar date) {
		this.date1 = date;
	}

	public Calendar getDate2() {
		return date2;
	}

	public void setDate2(Calendar date2) {
		this.date2 = date2;
	}

}
