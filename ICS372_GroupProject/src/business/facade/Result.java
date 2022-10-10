package business.facade;

public class Result extends DataTransfer {
	public static final int ITEM_NOT_FOUND = 1;
	public static final int OPERATION_COMPLETED = 2;
	public static final int OPERATION_FAILED = 3;
	public static final int NO_SUCH_MEMBER = 4;

	private int resultCode;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
}
