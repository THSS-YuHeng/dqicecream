package dqcup.repair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class DbFileReader {
	public static LinkedList<Tuple> readFile(String fileRoute) {
		File file = new File(fileRoute);
		LinkedList<Tuple> tupleList = new LinkedList<Tuple>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = null;
			boolean columnNameLine = true;
			ColumnNames columnNames = null;
			while (null != (line = br.readLine())) {
				if (columnNameLine) {
					columnNames = new ColumnNames(line);
					columnNameLine = false;
				} else {
					Tuple tuple = new Tuple(columnNames, line);
					tupleList.add(tuple);
				}
			}

			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tupleList;
	}
}
