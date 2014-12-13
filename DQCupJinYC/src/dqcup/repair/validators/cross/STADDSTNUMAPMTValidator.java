package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

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
		
		if (correctAPMT.equals(" ")) { //apmt 都是错的
			correctSTNUM = correct.getValue(rawAttrs.STNUM);
			correctSTADD = correct.getValue(rawAttrs.STADD);
			
			if (correctSTNUM.equals(" ")) { // 只有stadd 是有可能对的
				if (correctSTADD.startsWith(rawAttrs.STADDfre)) {
					correctSTNUM = null;
					correctAPMT = null;
					for (int i = 0; i < linkedList.size() - 1; i++) {
						Tuple tuple = linkedList.get(i);
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
					for (int i = 0; i < linkedList.size() - 1; i++) {
						Tuple tuple = linkedList.get(i);
						correctSTADD = tuple.getValue(rawAttrs.STADD);
						if (!correctSTADD.equals(" ") && !correctSTADD.startsWith(rawAttrs.STADDfre)) {
							correctSTADD = tuple.getValue(rawAttrs.STADD);
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
					}
				}
			} else {	//存在 stnum 作为参考
				if (correctSTNUM == null || correctSTNUM.equals("")) {
					// PO BOX;
					correctAPMT = "";
					if (correctSTADD.startsWith(rawAttrs.STADDfre)) {
						for (int i = 0; i < linkedList.size() - 1; i++) {
							Tuple tuple = linkedList.get(i);
							if (tuple.getValue(rawAttrs.STNUM) != null && !tuple.getValue(rawAttrs.STNUM).equals("")) {
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
						for (int i = 0; i < linkedList.size() - 1; i++) {
							Tuple tuple = linkedList.get(i);
							if (tuple.getValue(rawAttrs.STNUM) != null && !tuple.getValue(rawAttrs.STNUM).equals("")) {
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
							if (tuple.getValue(rawAttrs.STADD).startsWith(rawAttrs.STADDfre)) {
								correctSTADD = tuple.getValue(rawAttrs.STADD);
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
						}
					}
				} else {
					// changgui
					for (int i = 0; i < linkedList.size() - 1; i++) {
						Tuple tuple = linkedList.get(i);
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
						if (correctSTADD.startsWith(rawAttrs.STADDfre)) {
							correctSTADD = tuple.getValue(rawAttrs.STADD);
							if (!correctSTADD.equals(" ") && correctSTADD != null && !correctSTADD.startsWith(rawAttrs.STADDfre)) {
								correctSTADD = tuple.getValue(rawAttrs.STADD);
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
			}
			correct.setValue(rawAttrs.STADD, correctSTADD);
			correct.setValue(rawAttrs.STNUM, correctSTNUM);
			correct.setValue(rawAttrs.APMT, correctAPMT);
			
			correctTable.get(correct.getValue(rawAttrs.CUID)).removeLast();
			correctTable.get(correct.getValue(rawAttrs.CUID)).add(correct);
		} else {
			boolean PRE = false;
			boolean REFIND = false;
			
			boolean numF = false;
			boolean addF = false;
			
			if (correctAPMT == null || correctAPMT.equals("")) {
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
				if (correctSTNUM == null || correctSTNUM.equals("")) {
					numF = true;
				}
				if (correctSTADD == null || correctSTADD.equals("") || correctSTADD.startsWith(rawAttrs.STADDfre)) {
					addF = true;
				}
			}
			
			for (int i = 0; i < linkedList.size() - 1; i++) {
				Tuple tuple = linkedList.get(i);
				if (PRE) {
					if (tuple.getValue(rawAttrs.APMT) != null && !tuple.getValue(rawAttrs.APMT).equals("")) {
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
					if (tuple.getValue(rawAttrs.STNUM) != null && !tuple.getValue(rawAttrs.STNUM).equals("")) {
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
						if (!tuple.getValue(rawAttrs.STNUM).equals(" ") && tuple.getValue(rawAttrs.STNUM) != null && !tuple.getValue(rawAttrs.STNUM).equals("")) {
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
						correctSTADD = tuple.getValue(rawAttrs.STADD);
						if (correctSTADD.equals(" ") && correctSTADD != null && !correctSTADD.startsWith(rawAttrs.STADDfre)) {
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
}
