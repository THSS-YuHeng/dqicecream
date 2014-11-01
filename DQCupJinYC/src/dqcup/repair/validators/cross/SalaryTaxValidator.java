package dqcup.repair.validators.cross;

import java.util.BitSet;

public class SalaryTaxValidator {

	public static boolean test(String salary, String tax, String ssn,BitSet errorflagSet) {
		try {
			int iSalary = Integer.valueOf(salary);
			int iTax = Integer.valueOf(tax);
			if( ssn.equals("  ")) {
				//System.out.println("Wrong ssn");
				return true;
			}
			else if( ssn.compareTo("000000000") == 0) {
				//System.out.println(salary + "\t" + tax + "\t" + "Zero ssn");
				return true;
			}
			else {
				return true;
			}
		} catch (Exception e) {
			//System.out.println("Exception");
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
