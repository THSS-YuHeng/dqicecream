package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.LinkedList;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class STADDSTNUMAPMTValidator {

	public static void test(HashMap<String, LinkedList<Tuple>> correctTable, LinkedList<Tuple> linkedList, HashMap<String, ErrorData> errorTable) {
		// TODO Auto-generated method stub
		Tuple correct = linkedList.getLast();		
		String correctAPMT = correct.getValue(rawAttrs.APMT);
		String correctSTADD = null;
		String correctSTNUM = null;
		boolean PRE = false;
		boolean REFIND = false;
		
		boolean numF = false;
		boolean addF = false;
		
		if (correctAPMT == null) {
			correctSTNUM = null;
			correctSTADD = rawAttrs.STADDfre;
			PRE = true;
			if (!correct.getValue(rawAttrs.STADD).startsWith(rawAttrs.STADDfre)) {
				REFIND = true;
			} else {
				correctSTADD = correct.getValue(rawAttrs.STADD);
			}
		} else {
			correctSTNUM = correct.getValue(rawAttrs.STNUM);
			correctSTADD = correct.getValue(rawAttrs.STADD);
			if (correctSTNUM == null) {
				numF = true;
			}
			if (correctSTADD == null || correctSTADD.startsWith(rawAttrs.STADDfre)) {
				numF = true;
			}
		}
		
		for (int i = 0; i < linkedList.size() - 1; i++) {
			Tuple tuple = linkedList.get(i);
			if (PRE) {
				if (tuple.getValue(rawAttrs.APMT) != null) {
					if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
						ErrorData edata = new ErrorData();
						edata.dataTuple = tuple;
						edata.errorFlagSet.set(rawAttrs.APMT_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					} else {
						ErrorData edata = errorTable.get(tuple
								.getValue(rawAttrs.RUID));
						edata.errorFlagSet.set(rawAttrs.APMT_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					}
				}
				if (tuple.getValue(rawAttrs.STNUM) != null) {
					if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
						ErrorData edata = new ErrorData();
						edata.dataTuple = tuple;
						edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					} else {
						ErrorData edata = errorTable.get(tuple
								.getValue(rawAttrs.RUID));
						edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					}
				}
				if (REFIND) {
					if (tuple.getValue(rawAttrs.STADD).startsWith(correctSTADD)) {
						correctSTADD = tuple.getValue(rawAttrs.STADD);
						REFIND = false;
					} else {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				} else {
					if (!tuple.getValue(rawAttrs.STADD).equals(correctSTADD)) {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				}				
			} else {
				if (!tuple.getValue(rawAttrs.APMT).equals(correctAPMT)) {
					if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
						ErrorData edata = new ErrorData();
						edata.dataTuple = tuple;
						edata.errorFlagSet.set(rawAttrs.APMT_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					} else {
						ErrorData edata = errorTable.get(tuple
								.getValue(rawAttrs.RUID));
						edata.errorFlagSet.set(rawAttrs.APMT_INDEX);
						errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					}
				}
				if (numF) {
					if (tuple.getValue(rawAttrs.STNUM) != null) {
						correctSTNUM = tuple.getValue(rawAttrs.STNUM);
						numF = false;
					} else {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				} else {
					if (!tuple.getValue(rawAttrs.STNUM).equals(correctSTNUM)) {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STNUM_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				}
				if (addF) {
					if (correctSTADD != null && !correctSTADD.startsWith(rawAttrs.STADDfre)) {
						correctSTADD = tuple.getValue(rawAttrs.STADD);
						addF = false;
					} else {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				} else {
					if (!tuple.getValue(rawAttrs.STADD).equals(correctSTADD)) {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(rawAttrs.STADD_INDEX);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				}
			}			
		}
		
		correct.setValue(rawAttrs.STADD, correctSTADD);
		correct.setValue(rawAttrs.STNUM, correctSTNUM);
		correct.setValue(rawAttrs.APMT, correctAPMT);
		
		correctTable.get(correct.getValue(rawAttrs.CUID)).removeLast();
		correctTable.get(correct.getValue(rawAttrs.CUID)).add(correct);
	}
}
