package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class SalaryValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.SALARY_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.SALARY;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if(value == null || value.length() == 0) return false;
		try {
			int sal = Integer.parseInt(value);
			if ((sal >= 500 && sal <= 20500)||sal==0){
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
}
