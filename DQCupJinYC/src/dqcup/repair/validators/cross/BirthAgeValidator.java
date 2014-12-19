package dqcup.repair.validators.cross;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class BirthAgeValidator {

	private static int parseInt(String num, int err) {
		try {
			Integer x = Integer.parseInt(num);
			return x;
		} catch ( NumberFormatException e) { 
			return err;
		}
	}
	
	public static void test(HashMap<String, LinkedList<Tuple>> correctTable, LinkedList<Tuple> linkedList, HashMap<String, ErrorData> errorTable) {
		Tuple correct = linkedList.getLast();
		String correctBirth = correct.getValue(rawAttrs.BIRTH);
		String correctAge = "";
		boolean REFIND = false;
		String cuid = correct.getValue(rawAttrs.CUID);
		if (correctBirth == null || correctBirth.equals("") || correctBirth.equals(" ")) {
			REFIND = true;
			correctAge = correct.getValue(rawAttrs.AGE);
		}
		boolean correctFind = false;
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
		if(REFIND) {
			// if no correct birth
			// is age correct?
			HashMap<Integer, Integer> dayCounterHashMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> monthCounterHashMap = new HashMap<Integer, Integer>();
			HashMap<Integer, Integer> yearCounterHashMap = new HashMap<Integer, Integer>();
			
			for (int i = 0; i < linkedList.size() - 1; i++) {
				int candidateAge = parseInt(correctAge, -1);
				int candidateDay = -1, candidateMonth = -1, candidateYear = -1;
				String[] birthSplit = correctBirth.split("-");
				int[] buffer = new int[3];
				if ( birthSplit.length == 3) {
					for (int j = 0; j < buffer.length; j++) {
						buffer[j] = parseInt(birthSplit[j], -1);
					}
					for (int j = 0; j < buffer.length; j++) {
						if( buffer[j] > 1922) {
							candidateYear = buffer[j];
							if( !yearCounterHashMap.containsKey(candidateYear) ) {
								yearCounterHashMap.put(candidateYear, 0);
							}
							yearCounterHashMap.put(candidateYear, yearCounterHashMap.get(candidateYear)+1);
						} else if( buffer[j] < 13 && buffer[j] > 0) {
							candidateMonth = buffer[j];
							if( !monthCounterHashMap.containsKey(candidateMonth) ) {
								monthCounterHashMap.put(candidateMonth, 0);
							}
							monthCounterHashMap.put(candidateMonth, monthCounterHashMap.get(candidateMonth)+1);
						} else if( buffer[j] < 32 && buffer[j] > 0) {
							candidateDay = buffer[j];
							if( !dayCounterHashMap.containsKey(candidateDay) ) {
								dayCounterHashMap.put(candidateDay, 0);
							}
							dayCounterHashMap.put(candidateDay, dayCounterHashMap.get(candidateDay)+1);
						}
					}
					int max = 0;
					for(Integer key: yearCounterHashMap.keySet()) {
						if( yearCounterHashMap.get(key) > max){ candidateYear = key; max = yearCounterHashMap.get(key); }
					}
					max = 0;
					for(Integer key: monthCounterHashMap.keySet()) {
						if( monthCounterHashMap.get(key) > max){ candidateMonth = key; max = monthCounterHashMap.get(key); }
					}
					max = 0;
					for(Integer key: dayCounterHashMap.keySet()) {
						if( dayCounterHashMap.get(key) > max){ candidateDay = key; max = dayCounterHashMap.get(key); }
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
