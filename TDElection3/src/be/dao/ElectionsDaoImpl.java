package be.dao;

import java.util.ArrayList;
import java.util.regex.Pattern;

import be.modele.ListeElectorale;
import be.util.ConnexionFile;

public class ElectionsDaoImpl implements IElectionsDao {

	/**
	 * le nom du fichier qui contient les données nécessaires au calcul des
	 * sièges
	 * */
	private String inFileName = null;

	/**
	 * le nom du fichier qui contiendra les résultats
	 */
	String outFileName = null;
	/**
	 * le nom du fichier de logs
	 */
	private String logFileName = null;

	/**
	 * le seuil électoral
	 */
	private double seuilElectoral;
	/**
	 * le nombre de sièges à pourvoir
	 */
	private int nbSiegesAPourvoir;

	/**
	 * les listes en compétition
	 */
	private ListeElectorale[] listesElectorales = null;

	/**
	 * constructeur avec paramètres
	 * 
	 * @param inFileFileName
	 *            String : le nom du fichier qui contient les données
	 *            nécessaires au calcul des sièges
	 * @param outFileName
	 *            String : le nom du fichier qui contiendra les résultats
	 * @param logFileName
	 *            String : le nom du fichier qui contiendra les messages
	 *            d'erreurs éventuels
	 * @throws ElectionsException
	 *             si problème quelconque
	 *
	 */
	public ElectionsDaoImpl(String inFileName, String outFileName,
			String logFileName) {
		super();
		this.outFileName = outFileName;
		this.logFileName = logFileName;
		this.inFileName = inFileName;
		ArrayList<String> listeIn = ConnexionFile.readInFile(inFileName);
		for (String string : listeIn) {
			/*
			 * Cette partie du code teste si on a un chiffre suivi d'un
			 * caractère alphabétique. Dans ce cas, il considère que ce n'est
			 * pas un bon fichier si l'egalité est verifiée avec le Pattern et
			 * termine le programme
			 */
			if (Pattern.matches("\\d\\w", string) == true) {
				ConnexionFile.writeInFile(logFileName, "Dans le fichier ["
						+ inFileName + "] Nombre de sièges à pourvoir ["
						+ string + "] incorrect\n" + "Le fichier ["
						+ inFileName + "] est incomplet");
				System.exit(-1);
			}
		}

	}

	public ElectionsDaoImpl() {
		super();
	}

	@Override
	public double getSeuilElectoral() {
		ArrayList<String> listeChaine;

		listeChaine = ConnexionFile.readInFile(this.inFileName);
		for (String string : listeChaine) {
			if (isValidDouble(string) == true)
				seuilElectoral = Double.valueOf(string);
		}
		return seuilElectoral;
	}

	@Override
	public int getNbSiegesAPourvoir() {
		ArrayList<String> listeChaine;
		listeChaine = ConnexionFile.readInFile(this.inFileName);
		for (String string : listeChaine) {
			if (isValidInt(string))
				nbSiegesAPourvoir = Integer.parseInt(string);
		}

		return nbSiegesAPourvoir;
	}

	@Override
	public ListeElectorale[] getListesElectorales() {
		ArrayList<String> listeChaine;
		int index = 0;

		listeChaine = ConnexionFile.readInFile(this.inFileName);
		ArrayList<ListeElectorale> buff = new ArrayList<ListeElectorale>();
		for (String string : listeChaine) {

			char car = string.charAt(0);
			if (Character.isAlphabetic(car)) {
				ListeElectorale listeElectorale = new ListeElectorale();
				listeElectorale.setNom(String.valueOf(car));
				buff.add(listeElectorale);
			}
		}
		listesElectorales = new ListeElectorale[buff.size()];
		for (ListeElectorale listeElectorale : buff) {
			listesElectorales[index++] = listeElectorale;
		}

		return listesElectorales;

	}

	@Override
	public void setListesElectorales(ListeElectorale[] listesElectorales) {
		ConnexionFile.writeInFile(this.outFileName, listesElectorales);

	}

	public String getInFileName() {
		return inFileName;
	}

	/* Ces fonctions privées testent si une valeur est double ou int */
	private static boolean isValidDouble(String str) {
		try {
			Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	private static boolean isValidInt(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
