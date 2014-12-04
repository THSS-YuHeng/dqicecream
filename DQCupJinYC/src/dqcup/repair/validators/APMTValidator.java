package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class APMTValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.APMT_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.APMT;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if(value == null || value.equals(""))
			return true;
		else if( value.length() != 3) {
			return false;
		}
		else {
			char c1 = value.charAt(0);
			char c2 = value.charAt(1);
			char c3 = value.charAt(2);
			if( Character.isDigit(c1) && Character.isLowerCase(c2) && Character.isDigit(c3)) {
				return true;
			}
			return false;
		}
	}
}
