package dqcup.repair.validators.cross;

import java.util.BitSet;

import dqcup.repair.attrs.rawAttrs;

public class STADDSTNUMAPMTValidator {
	private static boolean _14pure_number(String num) {
		try {
			int iStadd = Integer.parseInt(num);
			if (iStadd > 10000 && iStadd < 0)
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	public static boolean test(String STADD, String STNUM, String APMT,
			BitSet errorflagSet) {
		boolean returnflag = true;
		if (errorflagSet == null)
			errorflagSet = new BitSet(16);
		// TODO Auto-generated constructor stub
		// STADD属性为“PO Box xxxx”
		if (STADD.startsWith("PO Box ")) {
			// STNUM 和 APMT 都应该为空
			if (!STNUM.isEmpty()) {
				errorflagSet.set(rawAttrs.STNUM_INDEX);
				returnflag &= false;
			}
			if (!APMT.isEmpty()) {
				errorflagSet.set(rawAttrs.APMT_INDEX);
				returnflag &= false;
			}
			// 为“PO Box xxxx”,其中"xxxx"为1-4位纯数字
			String[] stadds = STADD.split("\\s");
			if (stadds.length != 3) {
				errorflagSet.set(rawAttrs.STADD_INDEX);
				returnflag &= false; // 应该有三部分
			}
			if (!_14pure_number(stadds[2])) {
				errorflagSet.set(rawAttrs.STADD_INDEX);
				returnflag &= false; // 后面应该是四位以内的纯数字
			}
		} else {
			if (STNUM.isEmpty()) {
				errorflagSet.set(rawAttrs.STNUM_INDEX);
				returnflag &= false;
			}
			if (APMT.isEmpty()) { // 这两个属性应该不为空
				errorflagSet.set(rawAttrs.APMT_INDEX);
				returnflag &= false;
			} else {
				// Stadd is not PO Box，而且STNUM和APMT已经不是空元素了
				if (APMT.length() != 3 || !Character.isDigit(APMT.charAt(0))
						|| !Character.isLowerCase(APMT.charAt(1))
						|| !Character.isDigit(APMT.charAt(2))) {
					errorflagSet.set(rawAttrs.APMT_INDEX);
					return false; // APMT应该是 数字、小写字母、数字的形式
				}
				for (int i = 0; i < STADD.length(); i++) {
					char c = STADD.charAt(i);
					if (Character.isLetter(c) || c == ' ' || c == ','
							|| c == '.') {
						continue;
					} else {
						errorflagSet.set(rawAttrs.STADD_INDEX);
						return false;
					}
				}
				return true;
			}
		}
		return returnflag;
	}
}
