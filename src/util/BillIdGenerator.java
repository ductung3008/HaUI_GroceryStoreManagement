package util;

public class BillIdGenerator {
	private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final int PASSWORD_LENGTH = 8;

	public static String generateBillId() {
		StringBuilder billId = new StringBuilder(PASSWORD_LENGTH);

		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (Math.random() * CHARS.length());
			billId.append(CHARS.charAt(index));
		}

		return billId.toString();
	}
}
