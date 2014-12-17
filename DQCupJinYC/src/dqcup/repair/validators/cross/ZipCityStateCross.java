package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class ZipCityStateCross {
	
	HashMap<String, LinkedList<String[]>> zipMap;
	
	public void ZipCityStateCross() {
		// TODO Auto-generated method stub
		zipMap = new HashMap<String, LinkedList<String[]>>();
	}

	public void findOutlier(HashMap<String, LinkedList<Tuple>> correctTable, HashMap<String, ErrorData> errorTable) {
		// TODO Auto-generated method stub
		Set<String> keys = correctTable.keySet();
		for (String key : keys) {
			LinkedList<Tuple> list = correctTable.get(key);
			Tuple corTuple = list.getLast();
			if (!corTuple.getValue(rawAttrs.ZIP).equals(" ")) {
				String[] cs = new String[2];
				cs[0] = corTuple.getValue(rawAttrs.CITY);
				cs[1] = corTuple.getValue(rawAttrs.STATE);
				if (zipMap.get(key) == null) {
					LinkedList<String[]> l = new LinkedList<String[]>();
					l.add(cs);
					zipMap.put(key, l);
				} else {
					zipMap.get(key).add(cs);
				}
				
			}
		}
		
		Set<String> zips = zipMap.keySet();
		for (String zip : zips) {
			//city
			LinkedList<String[]> citylist = zipMap.get(zip);
			HashMap<String, Integer> citytmpmap = new HashMap<String, Integer>();
			for (String[] str : citylist) {
				if (!str[0].equals(" ")) {
					if (citytmpmap.get(str[0]) != null) {
						citytmpmap.put(str[0], citytmpmap.get(str[0]) + 1);
					} else {
						citytmpmap.put(str[0], 1);
					}
				}
			}
			Set<String> citytmpkeys = citytmpmap.keySet();
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
			
			//state
			LinkedList<String[]> statelist = zipMap.get(zip);
			HashMap<String, Integer> statetmpmap = new HashMap<String, Integer>();
			for (String[] str : statelist) {
				if (!str[0].equals(" ")) {
					if (statetmpmap.get(str[1]) != null) {
						statetmpmap.put(str[1], statetmpmap.get(str[0]) + 1);
					} else {
						statetmpmap.put(str[1], 1);
					}
				}
			}
		}
	}

	
}
