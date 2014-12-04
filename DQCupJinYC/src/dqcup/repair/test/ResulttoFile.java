package dqcup.repair.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import dqcup.repair.RepairedCell;

public class ResulttoFile {
	
	private static String[] foldpath ={"C:\\Users\\H\\Desktop\\123\\result_easy.bin",
						"C:\\Users\\H\\Desktop\\123\\result_normal.bin",
						"C:\\Users\\H\\Desktop\\123\\result_hard.bin"};

	public static void writeresult(Set<RepairedCell> found, int type) throws IOException {
		// TODO Auto-generated constructor stub
		FileWriter writer = new FileWriter(foldpath[type]);
		
		for (RepairedCell cell : found) {
			writer.write(cell.toString());
			writer.write("\r\n");
		}
		
		writer.close();
	}
}