package dqcup.repair.repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.validators.AgeValidator;
import dqcup.repair.validators.BirthValidator;
import dqcup.repair.validators.CityValidator;
import dqcup.repair.validators.FnameValidator;
import dqcup.repair.validators.LnameValidator;
import dqcup.repair.validators.MinitValidator;
import dqcup.repair.validators.SSNValidator;
import dqcup.repair.validators.STADDSTNUMAPMTValidator;
import dqcup.repair.validators.SalaryValidator;
import dqcup.repair.validators.StateValidator;
import dqcup.repair.validators.TaxValidator;
import dqcup.repair.validators.Validator;
import dqcup.repair.validators.ZipValidator;

public class DataRepair {
	
	private LinkedList<Tuple> tuples;					//原始数据链表存放
	private HashSet<RepairedCell> result;				//修复结果
	
	public HashMap<String, LinkedList<Tuple>> correctTable;			//单行数据处理结果
	public LinkedList<ErrorData> errorTable;			//单行数据错误

	public ArrayList<Validator> validators;
	
	public DataRepair(LinkedList<Tuple> t, HashSet<RepairedCell> r) {
		// TODO Auto-generated constructor stub
		this.tuples = t;
		this.result = r;
		correctTable = new HashMap<String, LinkedList<Tuple>>();
		errorTable = new LinkedList<ErrorData>();
		validators = new ArrayList<Validator>();
		
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
	}

	public void excute() {
		// TODO Auto-generated method stub
		String lastCUID = "";
		if (tuples == null || tuples.size() == 0) return;
		for (Tuple tuple : tuples) {
			for (Validator validator: validators) {
				if(validator.test(tuple.getValue(validator.getIndex()))){
					//correct
				} else {
					System.out.println(tuple.getValue(rawAttrs.RUID_INDEX) + "\t" +
							validator.getColName() + "\t" + 
							tuple.getValue(validator.getIndex()));
//					result.add(new RepairedCell(
//							Integer.parseInt(tuple.getValue(rawAttrs.RUID_INDEX)), 
//							validator.getColName(), 
//							""));
				}
			}
			
			if (STADDSTNUMAPMTValidator.test(tuple.getValue(rawAttrs.STADD), 
					tuple.getValue(rawAttrs.STNUM), tuple.getValue(rawAttrs.APMT))) {
				//correct
			} else {
				//TODO
			}
			
			//处理1结束
			if (correctTable.get(tuple.getValue(rawAttrs.CUID)) == null) { // 不存在
				inlines(lastCUID);
				lastCUID = tuple.getValue(rawAttrs.CUID);
				LinkedList<Tuple> list = new LinkedList<Tuple>();
				list.add(tuple);
				correctTable.put(tuple.getValue(rawAttrs.CUID), list);
			} else {														//存在
				LinkedList<Tuple> list = correctTable.get(tuple.getValue(rawAttrs.CUID));
				list.add(tuple);
			}		
		}
	}

	private void inlines(String CUID) {
		// TODO Auto-generated method stub
		if (CUID == null || CUID.length() < 1) return;
		LinkedList<Tuple> list = correctTable.get(CUID);
		for (int i = 2; i < 16; i++) {
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();
			for (Tuple tuple : list) {
				String v = tuple.getValue(i);
				if (v == null) v += " ";
				if (tmp.get(v) == null) {
					tmp.put(v, 1);
				} else {
					tmp.put(v, tmp.get(v) + 1);
				}
			}
			
			Set<String> keys = tmp.keySet();
			int max = 0;
			@SuppressWarnings("unused")
			String correct = "";
			for(String key: keys){  
	            if (tmp.get(key) >= max) {
	            	max = tmp.get(key);
	            	correct = key;
	            }
	        }
			if (correct == " ") correct = "";
			for (Tuple tuple : list) {
				String v = tuple.getValue(i);
				if (!v.equals(correct)) {
					result.add(new RepairedCell(
							Integer.parseInt(tuple.getValue(rawAttrs.RUID_INDEX)), 
							rawAttrs.RAWS_STRINGS[i], 
							correct));
				}
			}
		}
	}
}
