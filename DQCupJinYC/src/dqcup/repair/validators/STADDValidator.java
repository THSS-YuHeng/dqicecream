package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class STADDValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.STADD_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.STADD;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		return true;
	}

}
