package be.metier;

import be.modele.ListeElectorale;

public interface IElectionsMetier {
	/**
	 * demande le seuil �lectoral
	 * 
	 * @return double : le seuil �lectoral
	 */
	public double getSeuilElectoral();

	/**
	 * demande le nombre de si�ges � pourvoir
	 * 
	 * @return int : le nombre de si�ges � pourvoir
	 */
	public int getNbSiegesAPourvoir();

	/**
	 * rend le tableau des listes en comp�tition
	 * 
	 * @return ListeElectorale[] : le tableau des listes en comp�tition
	 */
	ListeElectorale[] getListesElectorales();

	/**
	 * calcul des si�ges des listes candidates
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes avec les voix mais sans les
	 *            si�ges
	 * @return ListeElectorale[] : les listes avec les si�ges
	 */
	ListeElectorale[] calculerSieges(ListeElectorale[] listesElectorales);

	/**
	 * enregistre les r�sultats de l'�lection
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes candidates � enregistrer
	 */
	public void recordResultats(ListeElectorale[] listesElectorales);
}
