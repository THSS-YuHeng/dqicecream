package dqcup.repair.attrrepair;

public class APMTrepair {
	static public String repair(String input) {
		int cast = 0;
		if( input.length() == 3) {
			char num1 = '\0', num2 = '\0';
			char c = '\0';
			for(int i = 0; i < input.length(); i++) {
				char temp = input.charAt(i);
				if(Character.isDigit(temp)) {
					if( num1 == '\0') {
						num1 = temp;
						cast++;
					}
					else if( num2 == '\0') {
						num2 = temp;
						cast++;
					}
				}
				else if (Character.isAlphabetic(temp)){
					if( c == '\0') {
						c = temp;
						cast++;
					}
				}
			}
			if (cast == 3) {
				char[] retcs = new char[3];
				retcs[0] = num1;
				retcs[1] = c;
				retcs[2] = num2;
				String ret = String.valueOf(retcs);
				return ret;
			} else {
				return " ";
			}			
		}	
		return " ";
	}
}
