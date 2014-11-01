package dqcup.repair.validators.cross;

import java.util.BitSet;

public class SalaryTaxValidator {

	public static boolean test(String salary, String tax, String ssn,BitSet errorflagSet) {
		try {
			int iSalary = Integer.valueOf(salary);
			int iTax = Integer.valueOf(tax);
			return simpleTest(iSalary, iTax);
		} catch (Exception e) {
			return false;
		}
	}
	
	private static boolean simpleTest(int salary, int tax) {
		if (salary == 0 && tax == 0) {
			return true;
		}
		if (salary < 500 || salary > 20500) {
			return false;
		}
		if (salary > 1500 && tax == 0) {
			return false;
		}
		if (salary < 1500 && tax > 0) {
			return false;
		}
		if (tax > salary) {
			return false;
		}
		return true;
	}
}
