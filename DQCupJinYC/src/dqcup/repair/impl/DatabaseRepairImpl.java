package dqcup.repair.impl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import dqcup.repair.DatabaseRepair;
import dqcup.repair.DbFileReader;
import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;

public class DatabaseRepairImpl implements DatabaseRepair {

	@Override
	public Set<RepairedCell> repair(String fileRoute) {
		//Please implement your own repairing methods here.
		LinkedList<Tuple> tuples = DbFileReader.readFile(fileRoute);
		
		HashSet<RepairedCell> result = new HashSet<RepairedCell>();
		
		return result;
	}

}
