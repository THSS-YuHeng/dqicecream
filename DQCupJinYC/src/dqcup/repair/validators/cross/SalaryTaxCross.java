package dqcup.repair.validators.cross;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.text.html.CSS;
import javax.xml.soap.Text;

import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class SalaryTaxCross {
	class SalaryTax {
		public int salary;
		public int tax;
		public String cuid;
	}
	// 		state			  Salary		Tax			Cuids	 
	HashMap<String, SortedMap<Integer, Map<Integer, Set<String>>>> map;
	
	// key = cuid
	Set<String> torepair;
	HashMap<String, Integer> cuid_tax_map;
	public SalaryTaxCross() {
		map = new HashMap<>();
		torepair = new HashSet<>();
		cuid_tax_map = new HashMap<>();
	}

	public void add(String state, int salary, int tax, String cuid) {
		SortedMap<Integer, Map<Integer, Set<String>>> salaries = map.get(state);
		if (salaries == null) {
			salaries = new TreeMap<Integer, Map<Integer, Set<String>>>();
			map.put(state, salaries);
		}
		Map<Integer, Set<String>> taxes = salaries.get(salary);
		if (taxes == null) {
			taxes = new HashMap<Integer, Set<String>>();
			salaries.put(salary, taxes);
		}
		if(salary < 1500 && tax != 0) {
			// 过滤掉不应该征税的东西
			tax = 0;
			torepair.add(cuid);
		}
		Set<String> cuids = taxes.get(tax);
		if (cuids == null) {
			cuids = new HashSet<String>();
			taxes.put(tax, cuids);
		}
		cuids.add(cuid);
	}

	
	/* 用贪心算法搞
	 * 如果是第一项，且salary大于1500
	 * */
	
	public void findOutlier() {
		for (Entry<String, SortedMap<Integer, Map<Integer, Set<String>>>> e : map.entrySet()) {
			// in one state
			System.out.println(e.getKey());
			SortedMap<Integer, Map<Integer, Set<String>>> salariesMap = e.getValue();
			List<Entry<Integer, Map<Integer, Set<String>>>> salaries = 
					new ArrayList<Map.Entry<Integer, Map<Integer, Set<String>>>>(
					salariesMap.entrySet());
			int length = salaries.size();
			for (int i = 0; i < length; i++) {
				Entry<Integer, Map<Integer, Set<String>>> entry = salaries.get(i);
				Map<Integer, Set<String>> taxes = entry.getValue();
				if( taxes.size() > 1 ) {
					System.out.print("salary:" + entry.getKey()+ "\t");
					Integer prev_tax = -1, next_tax = -1;
					for(int j = i-1; j >= 0; j--){
						if( salaries.get(j).getValue().size() == 1 ) {
							prev_tax = salaries.get(j).getValue().keySet().iterator().next();
							for( Integer tax: salaries.get(j).getValue().keySet()){
								System.out.print("prevtax:" + tax+"\t");
							}
							break;
						}
					}
					for(int j = i+1; j < length; j++){
						if( salaries.get(j).getValue().size() == 1 ) {
							next_tax = salaries.get(j).getValue().keySet().iterator().next();
							for( Integer tax: salaries.get(j).getValue().keySet()){
								System.out.print("nexttax:" + tax+"\t");
							}
							break;
						}
					}
					Integer candidate_tax = -1;
					for( Integer tax: taxes.keySet()){
						System.out.print("\t" + tax.toString());
						if(tax > prev_tax && tax < next_tax) {
							candidate_tax = tax;
							System.out.print("*");
							continue;
						}
					}
					for( Integer tax: taxes.keySet()){
						if(tax > prev_tax && tax < next_tax) {
							continue;
						}
						else {
							// wrong tax
							Set<String> cuids = taxes.get(tax);
							for(String cuid: cuids) {
								torepair.add(cuid);
								cuid_tax_map.put(cuid, candidate_tax);
							}
						}
					}
					System.out.println();
				}
				else {
					
				}
			}
		}
//		for(String outlier: torepair) {
//			System.out.println(outlier + "\t" + cuid_tax_map.get(outlier));
//		}
		System.out.println(Integer.toString(torepair.size()));
	}
	
	public void repair(String key, LinkedList<Tuple> correctTable, HashMap<String, ErrorData> errorTable, Tuple correctLine) {
		if( torepair.contains(key)) {
			int correctTax = cuid_tax_map.get(key);
			correctLine.setValue(rawAttrs.TAX, Integer.toString(correctTax));
			for(Tuple t: correctTable) {
				if( Integer.parseInt(t.getValue(rawAttrs.TAX)) != correctTax ) {
					String ruid = t.getValue(rawAttrs.RUID);
					System.out.println(ruid + ","+key + "," + correctTax);
					ErrorData ed = errorTable.get(ruid);
					if( ed == null) {ed = new ErrorData(); }
					ed.dataTuple = t;
					ed.errorFlagSet.set(rawAttrs.TAX_INDEX);
					errorTable.put(ruid, ed);
				}
			}
		}
	}

	public void add(String state, String salary, String tax, String cuid) {
		// TODO Auto-generated method stub
		if( tax.equals(" ")){
			return;
		}
		else if( salary.equals(" ")){
			return;
		}
		add(state, Integer.parseInt(salary), Integer.parseInt(tax), cuid);
	}
}
