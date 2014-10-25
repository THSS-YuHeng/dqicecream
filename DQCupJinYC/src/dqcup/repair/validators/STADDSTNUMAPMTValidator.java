package dqcup.repair.validators;

public class STADDSTNUMAPMTValidator {

	public static boolean test(String STADD, String STNUM, String APMT) {
		// TODO Auto-generated constructor stub
		if (STADD.startsWith("PO Box ")) {
			return STNUM.isEmpty() && APMT.isEmpty();
		} else if (STNUM.isEmpty() || APMT.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
