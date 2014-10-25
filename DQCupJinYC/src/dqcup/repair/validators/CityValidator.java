package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class CityValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.CITY_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.CITY;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return false;
		}
		int length = value.length();
		if(!Character.isUpperCase(value.charAt(0))){
			return false;
		}
		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);
			if (!Character.isLetter(c) && c != '\'' && c != '/' && c != '.' && c != ' ' && c != '-') {
				return false;
			}
		}
		return true;
	}

}
