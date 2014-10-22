package dqcup.repair;

import java.util.Set;

public interface DatabaseRepair {
	/**
	 * repair the input database and return repaired cells
	 * @param fileRoute the relative route of input file
	 */
	public Set<RepairedCell> repair(String fileRoute);
}
