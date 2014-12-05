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
			// skip wrong ssn
			return;
		}
		if ( ssn_cuid_map.containsKey(ssn) ) {
			if( !ssn.equals("000000000")) {
				//System.out.println("multiple cuid one ssn");
			}
		}
		ssn_cuid_map.put(ssn, cuid);
	}
	public void add_problem(HashSet<String> ssns, String cuid) {
		cuid_ssns_map.put(cuid, ssns);
	}

	public void repair(
			HashMap<String, LinkedList<Tuple>> correctTable, 
			HashMap<String, ErrorData> errorTable) {
		for(String cuid: cuid_ssns_map.keySet()) {
			HashSet<String> ssns = cuid_ssns_map.get(cuid);
			String candidateSsn = "***";
			boolean hasZero = false;
			for(String ssn: ssns) {
				if(ssn_cuid_map.containsKey(ssn) && !ssn.equals("000000000")) {
					continue;
				}
				if(ssn.equals("000000000")){
					hasZero = true;
				} else {
					candidateSsn = ssn;
				}
			}
			if(candidateSsn.equals("***") && hasZero) {
				candidateSsn = "000000000";
			}
			//System.out.println(cuid + "," + candidateSsn);
			LinkedList<Tuple> correctLinkedList = correctTable.get(cuid);
			Tuple candidateTuple = correctLinkedList.getLast();
			candidateTuple.setValue(rawAttrs.SSN, candidateSsn);
			for(Tuple t: correctLinkedList) {
				if(t==candidateTuple) {
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
						ed.errorFlagSet.clear(rawAttrs.SSN_INDEX);
						errorTable.put(ruid, ed);
					}
				}
			}
		}
	}
}
