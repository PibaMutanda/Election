package be.dao;

import be.modele.ListeElectorale;

public interface IElectionsDao {

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
	 * demande le tableau des listes en compétition
	 * 
	 * @return ListeElectorale[] : les listes en compétition
	 */
	public ListeElectorale[] getListesElectorales();

	/**
	 * enregistre les résultats des listes en compétition
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes en compétition
	 */
	public void setListesElectorales(ListeElectorale[] listesElectorales);

}
