package dqcup.repair.repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;
import dqcup.repair.attrrepair.APMTrepair;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.validators.APMTValidator;
import dqcup.repair.validators.AgeValidator;
import dqcup.repair.validators.BirthValidator;
import dqcup.repair.validators.CityValidator;
import dqcup.repair.validators.FnameValidator;
import dqcup.repair.validators.LnameValidator;
import dqcup.repair.validators.MinitValidator;
import dqcup.repair.validators.SSNValidator;
import dqcup.repair.validators.STADDValidator;
import dqcup.repair.validators.SalaryValidator;
import dqcup.repair.validators.StateValidator;
import dqcup.repair.validators.StnumValidator;
import dqcup.repair.validators.TaxValidator;
import dqcup.repair.validators.Validator;
import dqcup.repair.validators.ZipValidator;
import dqcup.repair.validators.cross.BirthAgeValidator;
import dqcup.repair.validators.cross.SSNCross;
import dqcup.repair.validators.cross.STADDSTNUMAPMTValidator;
import dqcup.repair.validators.cross.SalaryTaxCross;
import dqcup.repair.validators.cross.ZipCityStateCross;

public class DataRepair {

	private LinkedList<Tuple> tuples; // 原始数据链表存放
	private HashSet<RepairedCell> result; // 修复结果

	public HashMap<String, LinkedList<Tuple>> correctTable; // 单行数据处理结果 ***使用“
	// ”作为错误的数据填充*** cuid -> tuples
	public HashMap<String, ErrorData> errorTable; // 单行数据错误 ruid -> data

	public ArrayList<Validator> validators;
	public SalaryTaxCross salaryTaxCross;
	public ZipCityStateCross zipCitystateCrocc;
	public SSNCross ssnCross;

	public DataRepair(LinkedList<Tuple> t, HashSet<RepairedCell> r) {
		// TODO Auto-generated constructor stub
		this.tuples = t;
		this.result = r;
		correctTable = new HashMap<String, LinkedList<Tuple>>();
		errorTable = new HashMap<String, ErrorData>();
		validators = new ArrayList<Validator>();

		salaryTaxCross = new SalaryTaxCross();
		zipCitystateCrocc = new ZipCityStateCross();
		ssnCross = new SSNCross();

		validators.add(new AgeValidator());
		validators.add(new BirthValidator());
		validators.add(new CityValidator());
		validators.add(new FnameValidator());
		validators.add(new LnameValidator());
		validators.add(new MinitValidator());
		validators.add(new SalaryValidator());
		validators.add(new SSNValidator());
		validators.add(new StateValidator());
		validators.add(new TaxValidator());
		validators.add(new ZipValidator());
		validators.add(new APMTValidator());
		validators.add(new STADDValidator());
		validators.add(new StnumValidator());
	}

	public void excute() {
		// TODO Auto-generated method stub
		String lastCUID = "";
		if (tuples == null || tuples.size() == 0)
			return;
		for (Tuple tuple : tuples) {
			// *************
			// ** step 1 single attribute validate
			// *************
			ErrorData edata = new ErrorData();
			edata.dataTuple = tuple;
			edata.originalTuple = tuple.clone();
			for (Validator validator : validators) {
				if (validator.test(tuple.getValue(validator.getIndex()))) {
					// correct
				} else {
					// TODO
					edata.errorFlagSet.set(validator.getIndex());
					errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
					tuple.setValue(validator.getColName(), " ");
				}
			}
			// *************
			// **处理步骤2 build correctTable
			// *************
			if (correctTable.get(tuple.getValue(rawAttrs.CUID)) == null) { // 不存在
				inlines(lastCUID);
				lastCUID = tuple.getValue(rawAttrs.CUID);
				LinkedList<Tuple> list = new LinkedList<Tuple>();
				list.add(tuple);
				correctTable.put(tuple.getValue(rawAttrs.CUID), list);
			} else { // 存在
				LinkedList<Tuple> list = correctTable.get(tuple
						.getValue(rawAttrs.CUID));
				list.add(tuple);
			}
		}
		zipCitystateCrocc.findOutlierAndRepair(correctTable, errorTable);
		for (String key: correctTable.keySet()) {
			LinkedList<Tuple> correctLine = correctTable.get(key);
			Tuple candidate = correctLine.getLast();
			HashSet<String> ssns = new HashSet<>();
			for(int i = 0; i < correctLine.size()-1; i++) {
				Tuple t = correctLine.get(i);
				// 使用candidate的值，除了cuid
				String curssnString = t.getValue(rawAttrs.SSN); 
				if (curssnString.equals(" ")) {
					// 错误的ssn,不管他
					continue;
				}
				ssns.add(curssnString);
				salaryTaxCross.add(candidate.getValue(rawAttrs.STATE), candidate.getValue(rawAttrs.SALARY), 
						candidate.getValue(rawAttrs.TAX), t.getValue(rawAttrs.CUID));
			}
			String cuid = candidate.getValue(rawAttrs.CUID);
			if( ssns.size() > 1) {
				ssnCross.add_problem(ssns, cuid);
			} else {
				ssnCross.add_single(candidate.getValue(rawAttrs.SSN), cuid);
			}
		}	
		salaryTaxCross.findOutlier();
		for (String key: correctTable.keySet()) {
			LinkedList<Tuple> correctLine = correctTable.get(key);
			Tuple candidate = correctLine.getLast();
			salaryTaxCross.repair(key, correctLine, errorTable, candidate);
			if(candidate.getValue(rawAttrs.APMT).equals(" ")) {
				// do apmt repair
				HashMap<String, Integer> tmp = new HashMap<>();
				for( int j = 0; j < correctLine.size()-1; j++) {
					String ruid = correctLine.get(j).getValue(rawAttrs.RUID);
					ErrorData edata = errorTable.get(ruid);
					String v = edata.originalTuple.getValue(rawAttrs.APMT);
					//System.out.println(">"+v+"<");
					if (v == null)
					{}
					if (tmp.get(v) == null) {
						tmp.put(v, 1);
					} else {
						tmp.put(v, tmp.get(v) + 1);
					}	
				}
				Set<String> keys = tmp.keySet();
				int max = 0;
				String correct = "";
				for (String apmt : keys) {
					if(APMTrepair.repair(apmt) != " " && apmt.length() == 3 && tmp.get(apmt) >= max) {
						max = tmp.get(apmt);
						correct = apmt;
					}
				}
				if(correct.length() == 3) {
					correct = APMTrepair.repair(correct);
				}
				//System.out.println("APMT :>" + correct + "<");
				candidate.setValue(rawAttrs.APMT, correct);
			}
		}
		ssnCross.repair(correctTable, errorTable);
		// *************
		// **错误结果统计与修复
		// *************
		for (String key : errorTable.keySet()) {
			ErrorData edata = errorTable.get(key);
			for (int i = edata.errorFlagSet.nextSetBit(0); i >= 0; i = edata.errorFlagSet
					.nextSetBit(i + 1)) {
				// operate on index i here
				LinkedList<Tuple> l = correctTable.get(edata.dataTuple.getValue(rawAttrs.CUID));
				Tuple specific = l.getLast();

				result.add(new RepairedCell(Integer.parseInt(key),
						rawAttrs.RAWS_STRINGS[i], specific.getValue(i)));
			}
		}

	}

	private void inlines(String CUID) {
		// TODO Auto-generated method stub
		if (CUID == null || CUID.length() < 1)
			return;
		LinkedList<Tuple> list = correctTable.get(CUID);
		Tuple cotuple = new Tuple(list.getFirst().getColumnNames(), list.getFirst().tus);		

		for (int i = 2; i < 16; i++) {

			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			for (Tuple tuple : list) {
				String v = tuple.getValue(i);
				if (v == null)
					v += "  ";
				if (tmp.get(v) == null) {
					tmp.put(v, 1);
				} else {
					tmp.put(v, tmp.get(v) + 1);
				}
			}

			Set<String> keys = tmp.keySet();
			int max = 0;
			String correct = "";
			boolean change = false;
			for (String key : keys) {
				if (!key.equals(" ") && tmp.get(key) >= max) {
					max = tmp.get(key);
					correct = key;
					change = true;
				}
			}
			if (correct.equals("  "))
				correct = "";
			if (!change) {
				correct = " ";
			}
			for (Tuple tuple : list) {
				String v = tuple.getValue(i);
				if (!v.equals(correct)) {
					if (i == rawAttrs.AGE_INDEX || i == rawAttrs.TAX_INDEX || i == rawAttrs.STADD_INDEX 
							|| i == rawAttrs.STNUM_INDEX || i==rawAttrs.SSN_INDEX) {
						//do nothing
					} else {
						if (errorTable.get(tuple.getValue(rawAttrs.RUID)) == null) {
							ErrorData edata = new ErrorData();
							edata.dataTuple = tuple;
							edata.errorFlagSet.set(i);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						} else {
							ErrorData edata = errorTable.get(tuple
									.getValue(rawAttrs.RUID));
							edata.errorFlagSet.set(i);
							errorTable.put(tuple.getValue(rawAttrs.RUID), edata);
						}
					}
				}
			}			
			cotuple.setValue(rawAttrs.RAWS_STRINGS[i], correct);
		}
		//初步处理结束，开始行内修复，以cotuple为初步修复内容参考
		correctTable.get(CUID).add(cotuple);
		STADDSTNUMAPMTValidator.test(correctTable, correctTable.get(CUID), errorTable);
		BirthAgeValidator.test(correctTable, correctTable.get(CUID), errorTable);
	}
}
