package be.metier;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import be.dao.IElectionsDao;
import be.modele.ListeElectorale;

public class ElectionsMetierImpl implements IElectionsMetier {

	
	/* Spring se charge d'instancier iElectionsDao */
	@Autowired
	private IElectionsDao iElectionsDao;

	@Override
	public double getSeuilElectoral() {

		return iElectionsDao.getSeuilElectoral();
	}

	@Override
	public int getNbSiegesAPourvoir() {

		return iElectionsDao.getNbSiegesAPourvoir();
	}

	@Override
	public ListeElectorale[] getListesElectorales() {
		return iElectionsDao.getListesElectorales();
	}

	@Override
	public ListeElectorale[] calculerSieges(ListeElectorale[] listesElectorales) {
		int totalvoix = 0;
		double valSeuil = 0.0;
		int totalVoixUtile = 0;
		int quotienElect = 0;
		int resteDesSiege = 0;
		int nbreSieges = iElectionsDao.getNbSiegesAPourvoir();
		ArrayList<ListeElectorale> listPart = new ArrayList<ListeElectorale>();
		ArrayList<ListeElectorale> bufflListe = new ArrayList<ListeElectorale>();
		/* Calcul total des voix */
		for (ListeElectorale listeElectorale : listesElectorales) {
			totalvoix += listeElectorale.getVoix();
		}
		valSeuil = totalvoix * iElectionsDao.getSeuilElectoral();

		/* total des voix utiles */
		for (ListeElectorale listeElectorale : listesElectorales) {
			if (listeElectorale.getVoix() > valSeuil) {
				totalVoixUtile += listeElectorale.getVoix();
				listPart.add(listeElectorale);
			} else {
				listeElectorale.setElimine(true);
			}
		}

		quotienElect = totalvoix / nbreSieges;

		/* Première attribution des sièges */
		for (ListeElectorale listeElectorale : listPart) {
			int siege = listeElectorale.getVoix() / quotienElect;
			listeElectorale.setSieges(siege);
			nbreSieges = nbreSieges - siege;
		}

		/*
		 * Calcul et attribution des sièges à la plus forte moyenne
		 */
		resteDesSiege = nbreSieges;
		if (resteDesSiege > 0) {
			while (resteDesSiege > 0) {
				// initialisation de la moyenne Max à 0.0 (un float pour
				// plus de
				// précision dans le calcul)
				float moyenneMax = 0.0f;
				int indMax = 0;
				for (int i = 0; i < listPart.size(); i++) {
					float moyenne = listPart.get(i).getVoix()
							/ (listPart.get(i).getSieges() + 1);
					if (moyenne > moyenneMax) {
						moyenneMax = moyenne;
						indMax = i;
					}
				}
				/*
				 * On ajoute 1 siège au parti qui a une bonne moyenne.
				 * Temporaiment on le supprime de la liste jusqu'à ce qu'il n
				 * ait pas des sièges à attribuer.
				 */
				listPart.get(indMax).setSieges(
						listPart.get(indMax).getSieges() + 1);
				bufflListe.add(listPart.get(indMax));
				listPart.remove(indMax);
				resteDesSiege -= 1;
			}

		}
		for (ListeElectorale listeElectorale : bufflListe) {

			listPart.add(listeElectorale);
		}

		int cpt = 0;
		ListeElectorale[] tabSuccessFinal = new ListeElectorale[listPart.size()];
		for (ListeElectorale listeElectorale : listPart) {
			if (listeElectorale.getSieges() == 0)
				listeElectorale.setElimine(true);
			tabSuccessFinal[cpt++] = listeElectorale;
		}
    	
		Arrays.sort(tabSuccessFinal);
		return tabSuccessFinal;
	}

	@Override
	public void recordResultats(ListeElectorale[] listesElectorales) {
		iElectionsDao.setListesElectorales(listesElectorales);

	}

	public void setiElectionsDao(IElectionsDao iElectionsDao) {
		this.iElectionsDao = iElectionsDao;
	}

}
