package dqcup.repair.repair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import dqcup.repair.RepairedCell;
import dqcup.repair.Tuple;

public class DataRepair {
	
	private LinkedList<Tuple> tuples;					//原始数据链表存放
	private HashSet<RepairedCell> result;				//修复结果
	
	public HashMap<String, Tuple> correctTable;			//单行数据处理结果
	public LinkedList<E> errorTable;					//单行数据错误火鬃

	public DataRepair(LinkedList<Tuple> t, HashSet<RepairedCell> r) {
		// TODO Auto-generated constructor stub
		this.tuples = t;
		this.result = r;
	}

	public void excute() {
		// TODO Auto-generated method stub
		if (tuples == null || tuples.size() == 0) return;
		for (Tuple tuple : tuples) {
			System.out.println(tuple.getValue(3));
		}
	}
}
