package dqcup.repair.mytest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*; 

import dqcup.repair.DatabaseRepair;
import dqcup.repair.RepairedCell;
import dqcup.repair.impl.DatabaseRepairImpl;
import dqcup.repair.test.*;

public class CompareTest {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	
	static void findnotfound(Set<RepairedCell> found, Set<RepairedCell> truth) {
		HashMap<Integer,HashSet<String>> foundmap = new HashMap<Integer,HashSet<String>>();
		for(RepairedCell cell:found){
			HashSet<String> columnIds = null;
			if(foundmap.get(cell.getRowId())==null){
				columnIds = new HashSet<String>();
			}else{
				columnIds = foundmap.get(cell.getRowId());
			}
			columnIds.add(cell.getColumnId());
			foundmap.put(cell.getRowId(), columnIds);
		}
		for(RepairedCell cell:truth){
			if(foundmap.get(cell.getRowId())!=null){
				if(foundmap.get(cell.getRowId()).contains(cell.getColumnId())){
					// do nothing
				} else {
					System.out.println("Col not found " + cell.getColumnId().toString() + "\t"
							+ cell.getRowId() + "\t"
							+ cell.getValue());
				}
			} else {
				System.out.println("Row not found " + cell.getColumnId().toString() + "\t"
						+ cell.getRowId() + "\t"
						+ cell.getValue());
			}
		}
	}
	
	static void ouputFound(Set<RepairedCell> found, String fileName) {
		try {
			System.out.println(found.size());
			BufferedWriter output = new BufferedWriter(new FileWriter(new File(fileName)));
			for(RepairedCell cell:found){
				StringBuilder s = new StringBuilder();
				s.append(Integer.toString(cell.getRowId()) + ","+cell.getColumnId() + "," + cell.getValue()+"\n");
				output.write(s.toString());
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void ouputFound(List<RepairedCell> found, String fileName) {
		try {
			System.out.println(found.size());
			BufferedWriter output = new BufferedWriter(new FileWriter(new File(fileName)));
			for(RepairedCell cell:found){
				StringBuilder s = new StringBuilder();
				s.append(Integer.toString(cell.getRowId()) + ","+cell.getColumnId() + "," + cell.getValue()+"\n");
				output.write(s.toString());
			}
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void compare(Set<RepairedCell> truth, Set<RepairedCell> found){
		if(found.size()!=0){
			HashMap<Integer,HashSet<String>> truthMap = new HashMap<Integer,HashSet<String>>();
			for(RepairedCell cell:truth){
				HashSet<String> columnIds = null;
				if(truthMap.get(cell.getRowId())==null){
					columnIds = new HashSet<String>();
				}else{
					columnIds = truthMap.get(cell.getRowId());
				}
				columnIds.add(cell.getColumnId());
				truthMap.put(cell.getRowId(), columnIds);
			}
			int tAndF = 0;
			for(RepairedCell cell:found){
				if(truthMap.get(cell.getRowId())!=null){
					if(truthMap.get(cell.getRowId()).contains(cell.getColumnId())){
						tAndF++;
					}
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		long startTime = 0, endTime = 0, totalTime = 0;
		double avgFindAccuracy = 0, avgRepairAccuracy = 0;
		Set<RepairedCell> found, truth;
		 
		DatabaseRepair dr = new DatabaseRepairImpl();
		truth = TestUtil.readTruth("..\\compare\\Truth-easy.txt");
		if (truth.size() != 0) {
			startTime = System.currentTimeMillis();
			found = dr.repair("..\\compare\\DB-easy.txt");
			endTime = System.currentTimeMillis();
			ouputFound(found, "..\\compare\\"+"out-easy.txt");
			findnotfound(found, truth);
			double findAccuracy = TestUtil.findAccuracy(truth, found);
			double repairAccuracy = TestUtil.repairAccuracy(truth, found);
			System.out.println("easy-Time:" + (endTime - startTime));
			System.out.println("easy-Find Accuracy:" + findAccuracy);
			System.out.println("easy-Repair Accuracy:" + repairAccuracy);
			totalTime += (endTime - startTime);
			avgFindAccuracy += findAccuracy;
			avgRepairAccuracy += repairAccuracy;
		}

		dr = new DatabaseRepairImpl();
		truth = TestUtil.readTruth("..\\compare\\Truth-normal.txt");
		if (truth.size() != 0) {
			startTime = System.currentTimeMillis();
			found = dr.repair("..\\compare\\DB-normal.txt");
			endTime = System.currentTimeMillis();
			ouputFound(found, "..\\compare\\"+"out-normal.txt");
			findnotfound(found, truth);
			List<RepairedCell> l=new ArrayList<RepairedCell>();  
		    l.addAll(found);  
		    class ComparatorCell implements Comparator<RepairedCell>{
		   	 public int compare(RepairedCell arg0, RepairedCell arg1) {
		   		 RepairedCell r0 = arg0;
		   		 RepairedCell r1 = arg1;

		   		 return r0.getRowId() - r1.getRowId();
		   	 }
		   }
		    ComparatorCell comparator=new ComparatorCell();
		    Collections.sort(l, comparator);
		    ouputFound(l, "out-normal-re.txt");
			double findAccuracy = TestUtil.findAccuracy(truth, found);
			double repairAccuracy = TestUtil.repairAccuracy(truth, found);
			System.out.println("normal-Time:" + (endTime - startTime));
			System.out.println("normal-Find Accuracy:" + findAccuracy);
			System.out.println("normal-Repair Accuracy:" + repairAccuracy);
			totalTime += (endTime - startTime);
			avgFindAccuracy += findAccuracy;
			avgRepairAccuracy += repairAccuracy;
		}

		dr = new DatabaseRepairImpl();
		truth = TestUtil.readTruth("input/Truth-hard.txt");
		if (truth.size() != 0) {
			startTime = System.currentTimeMillis();
			found = dr.repair("input/DB-hard.txt");
			endTime = System.currentTimeMillis();

			double findAccuracy = TestUtil.findAccuracy(truth, found);
			double repairAccuracy = TestUtil.repairAccuracy(truth, found);
			System.out.println("hard-Time:" + (endTime - startTime));
			System.out.println("hard-Find Accuracy:" + findAccuracy);
			System.out.println("hard-Repair Accuracy:" + repairAccuracy);
			totalTime += (endTime - startTime);
			avgFindAccuracy += findAccuracy;
			avgRepairAccuracy += repairAccuracy;
		}

		System.out.println("Total Time:"+totalTime);
		System.out.println("Average Find Accuracy:"+avgFindAccuracy/3);
		System.out.println("Average Repair Accuracy:"+avgRepairAccuracy/3);
	}
}
