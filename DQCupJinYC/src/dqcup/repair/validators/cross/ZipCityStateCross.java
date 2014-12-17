package dqcup.repair.validators.cross;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.Tuple;
import dqcup.repair.attrs.rawAttrs;
import dqcup.repair.repair.ErrorData;

public class ZipCityStateCross {

	// 		zip				state	counter
	HashMap<String, HashMap<String, Integer>> zipStateCounterMap;
	// 		zip				state			city	counter
	HashMap<String, HashMap<String, HashMap<String, Integer>>> zipStateCityCounterHashMap;
	//		state			city			zip		counter
	HashMap<String, HashMap<String, HashMap<String, Integer>>> stateCityZipCounterHashMap;
	//		zip				state	count
	HashMap<String, HashMap<String, Integer>> zipShortStateMap;

	private String judge(Set<String> states, String zip) {
		return " ";
	}

	public ZipCityStateCross() {
		// TODO Auto-generated method stub
		zipStateCityCounterHashMap = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
		stateCityZipCounterHashMap = new HashMap<String, HashMap<String, HashMap<String, Integer>>>();
		zipStateCounterMap = new HashMap<String, HashMap<String, Integer>>();
		zipShortStateMap = new HashMap<String, HashMap<String, Integer>>();
	}

	public void findOutlierAndRepair(HashMap<String, LinkedList<Tuple>> correctTable, HashMap<String, ErrorData> errorTable) {
		for (String key: correctTable.keySet()) {
			LinkedList<Tuple> correctLine = correctTable.get(key);
			Tuple candidate = correctLine.getLast();
			//String cuid = candidate.getValue(rawAttrs.CUID);
			for(int i = 0; i < correctLine.size()-1; i++) {
				String zip = correctLine.get(i).getValue(rawAttrs.ZIP);
				String city = correctLine.get(i).getValue(rawAttrs.CITY);
				String state = correctLine.get(i).getValue(rawAttrs.STATE);
				if( !zip.equals(" ") && !state.equals(" ")) { // 首先zip不能是错误的
					String zipShort = zip.substring(0, 3); // zip的前三位，用于缩小搜索范围
					if( !zipShortStateMap.containsKey(zipShort)) {
						zipShortStateMap.put(zipShort, new HashMap<String, Integer>());
					}
					if( !zipShortStateMap.get(zipShort).containsKey(state)) {
						zipShortStateMap.get(zipShort).put(state, 0);
					}
					zipShortStateMap.get(zipShort).put(state, zipShortStateMap.get(zipShort).get(state)+1);
				}
				if( !zip.equals(" ") && !state.equals(" ") && !city.equals(" ")) {
					if( !zipStateCityCounterHashMap.containsKey(zip) ) {
						zipStateCityCounterHashMap.put(zip, new HashMap<String, HashMap<String, Integer>>());
						zipStateCounterMap.put(zip, new HashMap<String, Integer>());
					}
					if( !zipStateCityCounterHashMap.get(zip).containsKey(state) ) {
						zipStateCityCounterHashMap.get(zip).put(state, new HashMap<String, Integer>());
						zipStateCounterMap.get(zip).put(state, 0);
					}
					if( !zipStateCityCounterHashMap.get(zip).get(state).containsKey(city)) {
						zipStateCityCounterHashMap.get(zip).get(state).put(city, 0);
					}
					zipStateCounterMap.get(zip).put(state, zipStateCounterMap.get(zip).get(state)+1);
					zipStateCityCounterHashMap.get(zip).get(state).put(
							city, zipStateCityCounterHashMap.get(zip).get(state).get(city)+1);
					if ( !stateCityZipCounterHashMap.containsKey(state)) {
						stateCityZipCounterHashMap.put(state, new HashMap<String, HashMap<String, Integer>>());
					}
					if( !stateCityZipCounterHashMap.get(state).containsKey(city)) {
						stateCityZipCounterHashMap.get(state).put(city, new HashMap<String, Integer>());
					}
					if( !stateCityZipCounterHashMap.get(state).get(city).containsKey(zip) ) {
						stateCityZipCounterHashMap.get(state).get(city).put(zip, 0);
					}
					stateCityZipCounterHashMap.get(state).get(city).put(
							zip, stateCityZipCounterHashMap.get(state).get(city).get(zip)+1);
				}
			}
		}
		for(String kzipshort: zipShortStateMap.keySet()) {
			System.out.print(kzipshort + ":");
			for(String kstate: zipShortStateMap.get(kzipshort).keySet()) {
				System.out.print("{" + kstate + ":" + zipShortStateMap.get(kzipshort).get(kstate).toString() + "}");
			}
			System.out.print("\n");
		}
		for(String kzip: zipStateCityCounterHashMap.keySet()) {
			HashMap<String, HashMap<String, Integer>> stateCityCounter = zipStateCityCounterHashMap.get(kzip);
			if( stateCityCounter.keySet().size() > 1 ) {
				System.out.print(kzip + ":");
				for(String kstate: zipStateCityCounterHashMap.get(kzip).keySet() ) {
					System.out.print(" { " + kstate + " ");
					for(String kcity: zipStateCityCounterHashMap.get(kzip).get(kstate).keySet()) {
						System.out.print(kcity + " " + zipStateCityCounterHashMap.get(kzip).get(kstate).get(kcity) + " ");
					}
					System.out.print("}");
				}
				System.out.println();
			}
		}
		for(String kzip: zipStateCityCounterHashMap.keySet()) {
			HashMap<String, HashMap<String, Integer>> stateCityCounter = zipStateCityCounterHashMap.get(kzip);
			HashMap<String, Integer> stateCounter = zipStateCounterMap.get(kzip);
			int max = 0; String candidateState = " ";
			int citymax = 0; String candidateCity = " ";
			if( stateCounter.keySet().size() > 1) {
				// 1 zip 应该对应 only 1 state, 1 city
				// > 1 说明是有outlier
				System.out.print(kzip + ":");
				for( String kstate: zipShortStateMap.get(kzip.subSequence(0, 3)).keySet()) {
					System.out.print(kstate + " " + zipShortStateMap.get(kzip.subSequence(0, 3)).get(kstate).toString() + ",");
				}
				for( String kstate: stateCounter.keySet() ) {
					System.out.print(kstate + " ");
					if( zipShortStateMap.get(kzip.subSequence(0, 3)).get(kstate) > max) {
						max = zipShortStateMap.get(kzip.subSequence(0, 3)).get(kstate);
						candidateState = kstate;
					}
				}
				System.out.println("candidate:" + candidateState);
			}
			else {
				candidateState = stateCounter.keySet().iterator().next();
				//System.out.println("candidate:" + candidateState);
			}
			if( stateCityCounter.get(candidateState).keySet().size() > 1 ) {
				for( String kcity: stateCityCounter.get(candidateState).keySet() ) {
					if( kcity.equals(" ")) {
						System.out.println("wrong city" + kcity);
					}
					if( stateCityCounter.get(candidateState).get(kcity) > citymax) {
						citymax = stateCityCounter.get(candidateState).get(kcity);
						candidateCity = kcity;
						
					}
				}
				System.out.println("candidate:" + candidateCity);
			} else {
				candidateCity = stateCityCounter.get(candidateState).keySet().iterator().next();
			}
			// 在这之后实际上已经不关心counter的值了，直接将state和city赋给对应的位置
			stateCityCounter.clear();
			stateCityCounter.put(candidateState, new HashMap<String, Integer>());
			stateCityCounter.get(candidateState).put(candidateCity, 0);
		}
		for (String key: correctTable.keySet()) {
			LinkedList<Tuple> correctLine = correctTable.get(key);
			Tuple candidate = correctLine.getLast();
			HashSet<String> ssns = new HashSet<>();
			String cuid = candidate.getValue(rawAttrs.CUID);
			String zip = candidate.getValue(rawAttrs.ZIP);
			HashMap<String, HashMap<String, Integer>> temp = zipStateCityCounterHashMap.get(zip);
			if(temp == null) {
				continue;
			}
			String candidateState = zipStateCityCounterHashMap.get(zip).keySet().iterator().next();
			String candidateCity = zipStateCityCounterHashMap.get(zip).get(candidateState).keySet().iterator().next();
			if(!candidateState.equals(candidate.getValue(rawAttrs.STATE))||
					!candidateCity.equals(candidate.getValue(rawAttrs.CITY))){
				System.out.println("candidate:" + candidateState + "," + candidateCity);
			}
		}
	}
}