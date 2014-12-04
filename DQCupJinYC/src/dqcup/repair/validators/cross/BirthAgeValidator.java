package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.LinkedList;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class BirthAgeValidator {

	public static void test(HashMap<String, LinkedList<Tuple>> correctTable, LinkedList<Tuple> linkedList, HashMap<String, ErrorData> errorTable) {
		Tuple correct = linkedList.getLast();
		String correctBirth = correct.getValue(rawAttrs.BIRTH);
		String correctAge = "";
		boolean REFIND = false;
		if (correctBirth == null || correctBirth.equals("") || correctBirth.equals(" ")) {
			REFIND = true;
			correctAge = correct.getValue(rawAttrs.AGE);
		}
		for (int i = 0; i < linkedList.size() - 1; i++) {
			Tuple tuple = linkedList.get(i);
			if (REFIND) {
				//impossible to find truth
			} else {
				int a = 2013 - Integer.parseInt(correctBirth.split("-")[2]);
				correctAge = a + "";
				if (!tuple.getValue(rawAttrs.AGE).equals(correctAge)) {
					if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
						ErrorData edata = new ErrorData();
						edata.dataTuple = tuple;
						edata.errorFlagSet.set(rawAttrs.AGE_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					} else {
						ErrorData edata = errorTable.get(tuple
								.getValue(rawAttrs.RUID));
						edata.errorFlagSet.set(rawAttrs.AGE_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					}
				}
			}
		}
		
		correct.setValue(rawAttrs.BIRTH, correctBirth);
		correct.setValue(rawAttrs.AGE, correctAge);
		
		correctTable.get(correct.getValue(rawAttrs.CUID)).removeLast();
		correctTable.get(correct.getValue(rawAttrs.CUID)).add(correct);
	}
}
