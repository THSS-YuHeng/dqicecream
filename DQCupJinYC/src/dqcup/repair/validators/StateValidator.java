package dqcup.repair.validators;

import dqcup.repair.attrs.rawAttrs;

public class StateValidator implements Validator {

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return rawAttrs.STATE_INDEX;
	}

	@Override
	public String getColName() {
		// TODO Auto-generated method stub
		return rawAttrs.STATE;
	}

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		String[] stateSet = {"AL","AK","AZ","AR",
							"CA","CO","CT","DE",
							"FL","GA","HI","ID",
							"IL","IN","IA","KS",
							"KY","LA","ME","MD",
							"MA","MI","MN","MS",
							"MO","MT","NE","NV",
							"NH","NJ","NM","NY",
							"NC","ND","OH","OK",
							"OR","PA","RI","SC",
							"SD","TN","TX","UT",
							"VT","VA","WA","WV",
							"WI","WY","DC"};
		
		for (String str : stateSet) {
			if (str.equals(value)) return true;
		}
		return false;
	}

}
