import java.lang.IllegalArgumentException;

public class Utilities {

	public static void check(boolean cond) {
		check(cond, "");
	}

	public static void check(boolean cond, String mess) {
		if (!cond) {
			throw new IllegalArgumentException(mess);
		}
	}

	public static void print(boolean debug, String mess, Object ... args) {
		if (debug) {
			System.out.printf(mess, args);
		}
	}
}
