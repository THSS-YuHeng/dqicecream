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

	// key = cuid, 保存所有有问题的cuid
	Set<String> torepair;
	// key = cuid，value = cuid对应的正确的tax值，用于修复
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
			cuid_tax_map.put(cuid, 0);
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
//			String state = e.getKey(); 
//			if(e.getKey().equals("AK")) {
//				System.out.println("examine");
//			}
			// 遍历每个州，每个州对应一个salaries
			SortedMap<Integer, Map<Integer, Set<String>>> salariesMap = e.getValue();
			List<Entry<Integer, Map<Integer, Set<String>>>> salaries = 
					new ArrayList<Map.Entry<Integer, Map<Integer, Set<String>>>>(
							salariesMap.entrySet());
			int length = salaries.size();
			for (int i = 0; i < length; i++) {
				Entry<Integer, Map<Integer, Set<String>>> entry = salaries.get(i);
				Map<Integer, Set<String>> taxes = entry.getValue();
				// 修改所有相同salary但是包含多个tax的项
				if( taxes.size() > 1 ) {
					//System.out.print("salary:" + entry.getKey()+ "\t");
					Integer prev_tax = -1, next_tax = -1;
					for(int j = i-1; j >= 0; j--){
						prev_tax = salaries.get(j).getValue().keySet().iterator().next();
						break;
					}
					for(int j = i+1; j < length; j++){
						next_tax = salaries.get(j).getValue().keySet().iterator().next();
						break;
					}
					Integer candidate_tax = -1;
					for( Integer tax: taxes.keySet()){
						//System.out.print("\t" + tax.toString());
						if(tax > prev_tax && tax < next_tax) {
							candidate_tax = tax;
							//System.out.print("*");
							continue;
						}
					}
					Map<Integer, Set<String>> new_taxes = new HashMap<Integer, Set<String>>();
					HashSet<String> new_cuids = new HashSet<String>();
					new_taxes.put(candidate_tax, new_cuids);
					for( Integer tax: taxes.keySet()){
						if(tax == candidate_tax) {
							Set<String> cuids = taxes.get(tax);
//							for(String cuid: cuids) {
//								System.out.print("\t" + cuid);
//							}
							continue;
						}
						else {
							// wrong tax
							Set<String> cuids = taxes.get(tax);
							for(String cuid: cuids) {
								torepair.add(cuid);
								new_cuids.add(cuid);
								cuid_tax_map.put(cuid, candidate_tax);
								//System.out.print("\t" + cuid);
							}
						}
					}
					new_taxes.put(candidate_tax, new_cuids);
					//System.out.println();
				}
				else {
				}
			}
			// 目前应该所有的salary对应的项都改成1个可能正确的tax了
			// 执行一个最长递增子序列操作，找到outlier，即只有一个tax但是不属于递增序列的漏网之鱼
			// 暂时只能假定最长的递增序列是正确的
			int n = salaries.size();
			int[] B = new int[n+1];
			int[] A = new int[n];
			int INF = 100000000;
			int cur_lis_len = 1;
			B[0] = -INF;
			int tax_index = 0;
			// 初始化A和B数组
			for (int i = 0; i < length; i++) {
				Entry<Integer, Map<Integer, Set<String>>> entry = salaries.get(i);
				Integer sal = entry.getKey();
				Map<Integer, Set<String>> taxes = entry.getValue();
				Integer itax = taxes.keySet().iterator().next();
				//System.out.print(sal.toString() + ":" + itax + "\t");
				Set<String> cuids = taxes.get(itax);
				//for( String cuid: cuids) {
					//System.out.print(cuid+ ",");
				//}
				//System.out.println();;
				A[i] = itax;
			}
			//System.out.println(n);
			B[0] = 1;
			// 暴力求递增序列
			// B包含以对应元素结尾的最长序列长度
			// 每次迭代选择比自己小的最大长度，+1保存到B
			for(int i = 1; i < n; i++ ){
				int max_len = 0;
				for(int j = 0; j < i; j++) {
					if(A[i] >= A[j] && B[j] > max_len) {
						max_len = B[j];
					}
				}
				B[i] = max_len+1;
			}
			int l = INF;
			int lb = B[n-1]+1;
			for(int j = n-1; j >= 0; j--) {
				//System.out.print(A[j] + "\t" + B[j]);
				if( A[j] <= l && B[j] == lb-1) {
					l = A[j];
					lb = B[j];
					//System.out.println("");
				} else {
					//System.out.print("["+Integer.toString(A[j-1])+","+Integer.toString(A[j])+","+Integer.toString(A[j+1])+"]");
					Entry<Integer, Map<Integer, Set<String>>> entry = salaries.get(j);
					Integer sal = entry.getKey();
					Map<Integer, Set<String>> taxes = entry.getValue();
					
					Integer candidate_tax = 0;// 
					if (entry.getValue().size() > 1) {
						// should be impossible
						continue;
					}
					if (entry.getKey() < 1500) {
						// tax should be zero
						//continue;
					}
					if(j == 0){// no prev
						continue;
					}
					Entry<Integer, Map<Integer, Set<String>>> pre_entry = salaries.get(j-1);
					int prevSalary = pre_entry.getKey();
					int prevTax = pre_entry.getValue().keySet().iterator().next();
					int currentSalary = entry.getKey();
					int currentTax = entry.getValue().keySet().iterator().next();
					Set<String> cuids = taxes.get(currentTax);
					Entry<Integer, Map<Integer, Set<String>>> nextEntry = salaries.get(j + 1);
					int nextSalary = nextEntry.getKey();
					int nextTax = nextEntry.getValue().keySet().iterator().next();
					// 根据前后的salary和tax进行插值
					if ((currentTax < prevTax || currentTax > nextTax) && (prevTax < nextTax)) {
						//error
						int rate = (int) Math.round((double) (nextTax - prevTax) * 100
								/ (nextSalary - prevSalary));//per salary 100
						currentTax = (int) (prevTax + (double) (currentSalary - prevSalary) * rate
								/ 100);
						if ((currentSalary - prevSalary) % 100 != 0) {
							int ppTax = 0;
							int ppSalary = 0;
							int max = Math.max(0, j - 7);
							for (int k = j - 2; k >= max; k--) {
								Entry<Integer, Map<Integer, Set<String>>> ppEntry = salaries.get(k);
								if (ppEntry.getValue().size() > 1) {
									break;
								}
								ppSalary = ppEntry.getKey();
								ppTax = ppEntry.getValue().keySet().iterator().next();
								if (ppTax > nextTax) {
									break;
								}
								if ((currentSalary - ppSalary) % 100 == 0) {
									currentTax = (int) Math.round(ppTax + (currentSalary - ppSalary)
											* rate / 100);
									break;
								}
							}
						}
					}
					prevTax = currentTax;
					prevSalary = currentSalary;
					//System.out.print("\nto repair single tax error\t");
					for(String cuid: cuids) {
						torepair.add(cuid);
						cuid_tax_map.put(cuid, currentTax);
						//System.out.print("\t" + cuid);
					}
					//System.out.println(Integer.toString(A[j])+","+"-----");	
				}
				//System.out.println(Integer.toString(A[j])+","+Integer.toString(B[j]));
			}
			//System.out.println(e.getKey());
			//System.out.print("\n");
		}
		//System.out.println(Integer.toString(torepair.size()));
	}

	public void repair(String key, LinkedList<Tuple> correctTable, HashMap<String, ErrorData> errorTable, Tuple correctLine) {
		if( torepair.contains(key)) {
			int correctTax = cuid_tax_map.get(key);
			correctLine.setValue(rawAttrs.TAX, Integer.toString(correctTax));
			for(Tuple t: correctTable) {
				int tax = -1; 
				try{
					tax = Integer.parseInt(t.getValue(rawAttrs.TAX));
				}catch(NumberFormatException e){
				}
				if( tax != correctTax ) {
					String ruid = t.getValue(rawAttrs.RUID);
					//System.out.println(ruid + ","+key + "," + correctTax);
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
		// 接口，用于过滤有问题的tax和salary
		if( tax.equals(" ")){
			return;
		}
		else if( salary.equals(" ")){
			return;
		}
		add(state, Integer.parseInt(salary), Integer.parseInt(tax), cuid);
	}
}
