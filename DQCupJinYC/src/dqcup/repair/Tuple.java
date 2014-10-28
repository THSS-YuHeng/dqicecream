package dqcup.repair;

import java.util.HashMap;

public class Tuple {
	private ColumnNames columnNames;
	private HashMap<String, String> cells;
	
	public Tuple(ColumnNames columnNames, String tupleLine){
		cells = new HashMap<String,String>();
		this.setColumnNames(columnNames);
		String[] cellValues = tupleLine.split(":");
		for(int i = 0 ; i < cellValues.length ; i++){
			cells.put(columnNames.get(i), cellValues[i]);
		}
	}
	
	public String toString(){
		return cells.toString();
	}
	
	public String getValue(String columnName){
		return cells.get(columnName);
	}
	
	public String getValue(int columnIndex){
		String columnName = columnNames.get(columnIndex);
		return getValue(columnName);
	}
	
	public void setValue(String colomn, String value) {
		this.cells.put(colomn, value);
	}

	/**
	 * @return the columnNames
	 */
	public ColumnNames getColumnNames() {
		return columnNames;
	}

	/**
	 * @param columnNames the columnNames to set
	 */
	private void setColumnNames(ColumnNames columnNames) {
		this.columnNames = columnNames;
	}
	
}
