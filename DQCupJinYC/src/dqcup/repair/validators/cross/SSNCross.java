package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class SSNCross {

	// ssn > cuids
	HashMap<String, String> ssn_cuid_map;
	HashMap<String, HashSet<String>> cuid_ssns_map;

	public SSNCross() {
		ssn_cuid_map = new HashMap<>();
		cuid_ssns_map = new HashMap<>();
	}

	public void add_single(String ssn, String cuid) {
		if(ssn.length() != 9) {
			// 过滤不合规则的ssn
			return;
		}
//		if ( ssn_cuid_map.containsKey(ssn) ) {
//		}
		ssn_cuid_map.put(ssn, cuid);
	}
	public void add_problem(HashSet<String> ssns, String cuid) {
		// 添加存在问题的ssn和cuid
		cuid_ssns_map.put(cuid, ssns);
	}

	public void repair(
			HashMap<String, LinkedList<Tuple>> correctTable, 
			HashMap<String, ErrorData> errorTable) {
		// 遍历可能存在问题的cuidß
		for(String cuid: cuid_ssns_map.keySet()) {
			HashSet<String> ssns = cuid_ssns_map.get(cuid);
			String candidateSsn = "***"; // 肯定错误的ssn
			boolean hasZero = false;
			for(String ssn: ssns) {
				if(ssn_cuid_map.containsKey(ssn) && !ssn.equals("000000000")) {
					continue;
				}
				if(ssn.equals("000000000")){
					// 如果有零，作为备选方案，如果最后没有合适的结果，那就选这个作为正确答案
					hasZero = true;
				} else {
					candidateSsn = ssn;
				}
			}
			if(candidateSsn.equals("***") && hasZero) {
				// 如果ssn里面有零，会因为出现过而不选择，但是如果没有合适的ssn，那么就选这个
				candidateSsn = "000000000";
			}
			//System.out.println(cuid + "," + candidateSsn);
			LinkedList<Tuple> correctLinkedList = correctTable.get(cuid);
			Tuple candidateTuple = correctLinkedList.getLast();
			candidateTuple.setValue(rawAttrs.SSN, candidateSsn);
			for(Tuple t: correctLinkedList) {
				if(t==candidateTuple) {
					// 跳过结尾保存的correctTuple
					continue;
				}
				String tssn = t.getValue(rawAttrs.SSN_INDEX);
				String ruid = t.getValue(rawAttrs.RUID);
				if( !tssn.equals(candidateSsn) ) {
					//System.out.println(ruid + "," + candidateSsn);
					ErrorData ed = errorTable.get(ruid);
					if( ed == null) {
						ed = new ErrorData(); 
						ed.dataTuple = t;
					}
					ed.errorFlagSet.set(rawAttrs.SSN_INDEX);
					errorTable.put(ruid, ed);
				} else {
					ErrorData ed = errorTable.get(ruid);
					if(ed != null){
						// 可能之前错误的设置了ssn的错误flag，这里把这个flag清除掉
						ed.errorFlagSet.clear(rawAttrs.SSN_INDEX);
						errorTable.put(ruid, ed);
					}
				}
			}
		}
	}
}
