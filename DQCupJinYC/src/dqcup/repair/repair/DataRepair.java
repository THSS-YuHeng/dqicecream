package dqcup.repair.repair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.validators.BirthValidator;
import dqcup.repair.validators.CityValidator;
import dqcup.repair.validators.FnameValidator;
import dqcup.repair.validators.LnameValidator;
import dqcup.repair.validators.MinitValidator;
import dqcup.repair.validators.SalaryValidator;
import dqcup.repair.validators.Validator;

public class DataRepair {
	
	private LinkedList<Tuple> tuples;					//原始数据链表存放
	private HashSet<RepairedCell> result;				//修复结果
	
	public HashMap<String, Tuple> correctTable;			//单行数据处理结果
	public LinkedList<ErrorData> errorTable;			//单行数据错误

	public ArrayList<Validator> validators;
	
	public DataRepair(LinkedList<Tuple> t, HashSet<RepairedCell> r) {
		// TODO Auto-generated constructor stub
		this.tuples = t;
		this.result = r;
		validators = new ArrayList<Validator>();
		validators.add(new FnameValidator());
		validators.add(new LnameValidator());
		validators.add(new BirthValidator());
		validators.add(new CityValidator());
		validators.add(new MinitValidator());
		validators.add(new SalaryValidator());
	}

	public void excute() {
		// TODO Auto-generated method stub
		if (tuples == null || tuples.size() == 0) return;
		for (Tuple tuple : tuples) {
			for (Validator validator: validators) {
				if(validator.test(tuple.getValue(validator.getIndex()))){
					// correct
				}
				else {
					System.out.println(tuple.getValue(rawAttrs.RUID_INDEX) + "\t" +
							validator.getColName() + "\t" + 
							tuple.getValue(validator.getIndex()));
					result.add(new RepairedCell(
							Integer.parseInt(tuple.getValue(rawAttrs.RUID_INDEX)), 
							validator.getColName(), 
							""));
				}
			}
		}
	}
}
