package dqcup.repair.repair;

import java.util.BitSet;

import dqcup.repair.Tuple;

public class ErrorData {
	BitSet errorFlagSet;
	Tuple dataTuple;	
	
	public ErrorData() {
		// TODO Auto-generated constructor stub
		this.errorFlagSet = new BitSet(16);
	}
}
