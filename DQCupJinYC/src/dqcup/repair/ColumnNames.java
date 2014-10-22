package dqcup.repair;

import java.util.LinkedList;

/**
 * 
 * @author bloodyparadise
 *
 */
public class ColumnNames extends LinkedList<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6509035934111193396L;
	
	public ColumnNames(String line){
		String[] columnNames = line.split(":");
		for(String column: columnNames){
			this.add(column);
		}
	}
}
