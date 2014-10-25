package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class StnumValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.STNUM_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.STNUM;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		return true;
	}

}
