package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class BirthValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.BIRTH_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.BIRTH;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		// 7q10-1983
		int month = 0;
		int day = 0;
		int year = 0;
		if(value == null || value.length() == 0) return false;
		String[] ss = value.split("-");
		if( ss.length != 3) {
			return false;
		}
		else{
			for( String s : ss){
				try {
					if (month == 0) {
						month = Integer.parseInt(s);
					} else {
						if (day == 0) {
							day = Integer.parseInt(s);
						} else {
							year = Integer.parseInt(s);
						}
					}
				}
				catch (NumberFormatException e) {
					return false;
				}
			}
			if (month > 0 && month < 13 && day > 0 && day < 32 && year > 1929 && year < 1990) {
				return true;
			}
		}
		return false;
	}
}
