package be.ui;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import be.metier.IElectionsMetier;
import be.modele.ListeElectorale;

public class ElectionsConsole implements IElectionsUI {

	@Autowired
	private IElectionsMetier electionsMetier;

	public IElectionsMetier getElectionsMetier() {
		return electionsMetier;
	}

	public void setElectionsMetier(IElectionsMetier electionsMetier) {
		this.electionsMetier = electionsMetier;
	}

	@Override
	public void run() {
		ListeElectorale[] listeElectorales = null;
		Scanner sc = new Scanner(System.in);
		String nbre = "";
		System.out.println("Nombre de sièges à pourvoir: "
				+ electionsMetier.getNbSiegesAPourvoir());
		System.out.println("Seuil électoral :"
				+ electionsMetier.getSeuilElectoral());
		System.out.println("IL y a 7 listes en compétion.");
		System.out
				.println("Veuillez indiquer le nombre de voix de chacune d'elles: \n");
		listeElectorales = electionsMetier.getListesElectorales();
		for (ListeElectorale listeElectorale : listeElectorales) {
			System.out.println("Nombre de voix de la liste ["
					+ listeElectorale.getNom() + "]");
			nbre = sc.next();
			if ((Pattern.matches("[^a-zA-Z]*$", nbre) == false)
					|| (Integer.parseInt(nbre) < 0))
				do {
					System.out
							.println("Nombre de voix incorrect. Veuillez recommencer ");
					nbre = sc.next();
				} while ((Pattern.matches("[^a-zA-Z]*$", nbre) == false)
						|| (Integer.parseInt(nbre) < 0));
			listeElectorale.setVoix(Integer.parseInt(nbre));
		}
		Arrays.sort(listeElectorales);
		/* calcul et enregistrement des sièges dans le fichier elections.out.txt */
		electionsMetier.recordResultats(electionsMetier
				.calculerSieges(listeElectorales));

		System.out.println("Résultats de l'élection");
		for (ListeElectorale listeElectorale : listeElectorales) {
			System.out.println(listeElectorale);
		}
	}

}
