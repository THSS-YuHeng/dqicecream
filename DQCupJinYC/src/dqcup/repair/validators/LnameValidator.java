package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class LnameValidator implements Validator {

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return false;
		}
		int len = value.length();
		if (!Character.isUpperCase(value.charAt(0))) {
			return false;
		}
		for (int i = 1; i < len; i++) {
			char c = value.charAt(i);
			if (!Character.isLowerCase(c) && c != ',' && c != '.') {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.LNAME_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.LNAME;
	}
}
