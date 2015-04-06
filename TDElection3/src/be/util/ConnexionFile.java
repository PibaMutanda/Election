package be.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import be.modele.ListeElectorale;

public class ConnexionFile {

	public static ArrayList<String> readInFile(String file) {
		ArrayList<String> listChaine = new ArrayList<String>();
		int index = 0;
		String chaine = null;
		FileReader fr = null;
		try {
			fr = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fr);
			while ((chaine = bufferedReader.readLine()) != null) {

				listChaine.add(chaine);
			}
			bufferedReader.close();
			fr.close();
		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return listChaine;

	}

	public static boolean writeInFile(String fileName,
			ListeElectorale[] listesElectorales) {
		boolean iswriten = false;
		try {
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fw);
			for (ListeElectorale listeElectorale : listesElectorales) {
				bufferedWriter.write(listeElectorale.toString());
				bufferedWriter.write("\n");
				bufferedWriter.flush();
			}
			bufferedWriter.close();
			fw.close();
			iswriten = true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return iswriten;
	}

	public static boolean writeInFile(String fileName, String mess) {
		boolean iswriten = false;
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter output = new BufferedWriter(fileWriter);
			output.write(mess);
			output.flush();
			fileWriter.close();
			output.close();
			iswriten = true;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return iswriten;
	}
}
