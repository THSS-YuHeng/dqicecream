package dqcup.repair.test;

import java.util.Set;

import dqcup.repair.DatabaseRepair;
import dqcup.repair.RepairedCell;
import dqcup.repair.impl.DatabaseRepairImpl;

public class Test {

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		long startTime = 0, endTime = 0, totalTime = 0;
		double avgFindAccuracy = 0, avgRepairAccuracy = 0;
		Set<RepairedCell> found, truth;

		DatabaseRepair dr = new DatabaseRepairImpl();
		truth = TestUtil.readTruth("input/Truth-easy.txt");
		if (truth.size() != 0) {
			startTime = System.currentTimeMillis();
			found = dr.repair("input/DB-easy.txt");
			endTime = System.currentTimeMillis();

			double findAccuracy = TestUtil.findAccuracy(truth, found);
			double repairAccuracy = TestUtil.repairAccuracy(truth, found);
			System.out.println("easy-Time:" + (endTime - startTime));
			System.out.println("easy-Find Accuracy:" + findAccuracy);
			System.out.println("easy-Repair Accuracy:" + repairAccuracy);
			totalTime += (endTime - startTime);
			avgFindAccuracy += findAccuracy;
			avgRepairAccuracy += repairAccuracy;
			ResulttoFile.writeresult(found, 0);
		}

		dr = new DatabaseRepairImpl();
		truth = TestUtil.readTruth("input/Truth-normal.txt");
		if (truth.size() != 0) {
			startTime = System.currentTimeMillis();
			found = dr.repair("input/DB-normal.txt");
			endTime = System.currentTimeMillis();

			double findAccuracy = TestUtil.findAccuracy(truth, found);
			double repairAccuracy = TestUtil.repairAccuracy(truth, found);
			System.out.println("normal-Time:" + (endTime - startTime));
			System.out.println("normal-Find Accuracy:" + findAccuracy);
			System.out.println("normal-Repair Accuracy:" + repairAccuracy);
			totalTime += (endTime - startTime);
			avgFindAccuracy += findAccuracy;
			avgRepairAccuracy += repairAccuracy;
			ResulttoFile.writeresult(found, 1);
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
