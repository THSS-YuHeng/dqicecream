package dqcup.repair.validators;

public class FnameValidator implements Validator {

	@Override
	public boolean test(String value) {
		// TODO Auto-generated method stub
		if (value == null || value.length() == 0) {
			return false;
		}
		int len = value.length();
		if (!Character.isUpperCase(value.charAt(0))) {
			return false;
		}
		for (int i = 1; i < len; i++) {
			char c = value.charAt(i);
			if (!Character.isLowerCase(c) && c != ',' && c != '.') {
				return false;
			}
		}
		return true;
	}
}
