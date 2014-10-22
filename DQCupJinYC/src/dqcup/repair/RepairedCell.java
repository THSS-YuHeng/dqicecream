package dqcup.repair;

public class RepairedCell {
	private int rowId;
	private String columnId;
	private String value;
	
	public RepairedCell(int rowId, String columnId, String value){
		this.rowId = rowId;
		this.columnId = columnId;
		this.value = value;
	}
	
	/**
	 * @return the rowId
	 */
	public int getRowId() {
		return rowId;
	}
	
	/**
	 * @param rowId the row ID to set
	 */
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}
	
	/**
	 * @param columnId the column ID to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the repaired value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnId == null) ? 0 : columnId.hashCode());
		result = prime * result + rowId;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RepairedCell other = (RepairedCell) obj;
		if (columnId == null) {
			if (other.columnId != null)
				return false;
		} else if (!columnId.equals(other.columnId))
			return false;
		if (rowId != other.rowId)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String toString(){
		StringBuilder str = new StringBuilder();
		str.append(this.rowId);
		str.append(" ");
		str.append(this.columnId);
		str.append(" ");
		str.append(this.value);
		return str.toString();
	}
}
