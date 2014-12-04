package dqcup.repair.validators.cross;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.text.html.CSS;

import dqcup.repair.RepairedCell;

public class SalaryTaxCross {
	class SalaryTax {
		public int salary;
		public int tax;
		public String cuid;
	}
	// 		state			  Salary		Tax			Cuids	 
	HashMap<String, SortedMap<Integer, Map<Integer, Set<String>>>> map;
	Set<String> torepair;
	public SalaryTaxCross() {
		map = new HashMap<>();
		torepair = new HashSet<>();
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
			SortedMap<Integer, Map<Integer, Set<String>>> salariesMap = e.getValue();
			List<Entry<Integer, Map<Integer, Set<String>>>> salaries = 
					new ArrayList<Map.Entry<Integer, Map<Integer, Set<String>>>>(
					salariesMap.entrySet());
			int length = salaries.size();
			for (int i = 0; i < length; i++) {
				Entry<Integer, Map<Integer, Set<String>>> entry = salaries.get(i);
				Map<Integer, Set<String>> taxes = entry.getValue();
				if (entry.getValue().size() <= 1) {
					continue;
				} else if( i == 0) {
					// 第一项，选择最小的合法项
					int min_tax = Integer.MAX_VALUE;
					for(Integer tax : taxes.keySet()) {
						if( tax != 0 && tax < min_tax) {
							min_tax = tax;
						}
					}
					Set<String> cuids = taxes.get(min_tax);
					taxes.clear();
					taxes.put(min_tax, cuids);
				} else {
					// pick the least tax to be the correct answer
					int prev = i-1;
					int candidate = Integer.MAX_VALUE;
					int prev_tax = Integer.MAX_VALUE;
					Entry<Integer, Map<Integer, Set<String>>> entry_prev = salaries.get(prev);
					if (entry_prev.getValue().size() == 1) {
						Map<Integer, Set<String>> taxes_prev = entry_prev.getValue();
						if(taxes_prev.size() == 1) {
							
						}
					}
				}
			}
		}
	}
	
	public void repair(Set<RepairedCell> repairset) {
		return;
	}

	public void add(String state, String salary, String tax, String cuid) {
		// TODO Auto-generated method stub
		add(state, Integer.parseInt(salary), Integer.parseInt(tax), cuid);
	}
}
