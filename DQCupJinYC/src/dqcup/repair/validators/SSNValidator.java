package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class SSNValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.SSN_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.SSN;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if (value == null || value.length() != 9) return false;
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
