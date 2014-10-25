package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class TaxValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.TAX_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.TAX;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
