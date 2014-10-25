package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class AgeValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.AGE_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.AGE;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		try {
			int age = Integer.parseInt(value);
			if(10 < age || age > 90){
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

}
