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
		String[] ss = value.split("-");
		if( ss.length != 3) {
			return false;
		}
		else{
			for( String s : ss){
				try {
					Integer.parseInt(s);
				}
				catch (NumberFormatException e) {
					return false;
				}
			}
		}
		return true;
	}

}
