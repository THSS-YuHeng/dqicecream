package dqcup.repair.validators;

public interface Validator {
	public int getIndex();
	public String getColName();
	public boolean test(String value);
}
