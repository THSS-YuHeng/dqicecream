package dqcup.repair.impl;

import dqcup.repair.ColumnNames;
import dqcup.repair.Tuple;

public class DQrepair {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		ColumnNames columnNames = new ColumnNames("RUID:CUID:SSN:FNAME:MINIT:LNAME:STNUM:STADD:APMT:CITY:STATE:ZIP:BIRTH:AGE:SALARY:TAX");
		
		Tuple tuple = new Tuple(columnNames, "0:20000:276295220:Maveety::Ipadmin:621:Roumeas St:4c8:San Juan:PR:00921:2-14-1959:54:8450:1053");
		
		System.out.println(tuple.toString());
	}

}
