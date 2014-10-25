package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class ZipValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.ZIP_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.ZIP;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if (value == null || value.length() != 5) return false;
		try {
			Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
