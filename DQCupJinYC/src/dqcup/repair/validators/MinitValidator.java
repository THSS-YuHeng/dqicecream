package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class MinitValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.MINIT_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.MINIT;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if(value == null || value.length() == 0){
			return true;
		}
		else if(value.length() == 1) {
			return Character.isUpperCase(value.charAt(0));
		}
		else {
			return false;
		}
	}

}
