package be.dao;

import be.modele.ListeElectorale;

public interface IElectionsDao {

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
	 * demande le tableau des listes en comp�tition
	 * 
	 * @return ListeElectorale[] : les listes en comp�tition
	 */
	public ListeElectorale[] getListesElectorales();

	/**
	 * enregistre les r�sultats des listes en comp�tition
	 * 
	 * @param listesElectorales
	 *            ListeElectorale[] : les listes en comp�tition
	 */
	public void setListesElectorales(ListeElectorale[] listesElectorales);

}
