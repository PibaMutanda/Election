package be.metier;

import be.modele.ListeElectorale;

public interface IElectionsMetier {
	/**
	 * demande le seuil électoral
	 * 
	 * @return double : le seuil électoral
	 */
	public double getSeuilElectoral();

	/**
	 * demande le nombre de sièges à pourvoir
	 * 
	 * @return int : le nombre de sièges à pourvoir
	 */
	public int getNbSiegesAPourvoir();

	/**
	 * rend le tableau des listes en compétition
	 * 
	 * @return ListeElectorale[] : le tableau des listes en compétition
	 */
	ListeElectorale[] getListesElectorales();

	/**
	 * calcul des sièges des listes candidates
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes avec les voix mais sans les
	 *            sièges
	 * @return ListeElectorale[] : les listes avec les sièges
	 */
	ListeElectorale[] calculerSieges(ListeElectorale[] listesElectorales);

	/**
	 * enregistre les résultats de l'élection
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes candidates à enregistrer
	 */
	public void recordResultats(ListeElectorale[] listesElectorales);
}
