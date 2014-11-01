package dqcup.repair.validators.cross;

import java.util.BitSet;

public class STADDSTNUMAPMTValidator {

	public static boolean test(String STADD, String STNUM, String APMT, BitSet errorflagSet) {
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
