package dqcup.repair.validators.cross;

import java.util.BitSet;

public class BirthAgeValidator {

	public static boolean test(String birth, String age, BitSet errorflagSet) {
		String[] birthss = birth.split("-");
		int iage = Integer.parseInt(age);
		int month = Integer.parseInt(birthss[0]);
		int day = Integer.parseInt(birthss[1]);
		int year = Integer.parseInt(birthss[2]);
		System.out.println(iage+year);
		if( iage + year != 2013) {
			return false;
		}
		else {
			return true;
		}
	}
}
