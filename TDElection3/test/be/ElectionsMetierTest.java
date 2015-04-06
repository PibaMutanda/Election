package be;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import be.dao.ElectionsDaoImpl;
import be.dao.IElectionsDao;
import be.metier.ElectionsMetierImpl;
import be.metier.IElectionsMetier;
import be.modele.ListeElectorale;

public class ElectionsMetierTest {

	static ListeElectorale listeElectorale1 = null;
	static ListeElectorale listeElectorale2 = null;
	static ListeElectorale listeElectorale3 = null;
	static ListeElectorale listeElectorale4 = null;
	static ListeElectorale listeElectorale5 = null;
	static ListeElectorale listeElectorale6 = null;
	static ListeElectorale listeElectorale7 = null;
	static ListeElectorale[] tableListe = null;
	private static ApplicationContext appctx = null;
	private static IElectionsMetier electionMetier = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appctx = new FileSystemXmlApplicationContext("applicationContext.xml");
		listeElectorale1 = new ListeElectorale();
		listeElectorale2 = new ListeElectorale();
		listeElectorale3 = new ListeElectorale();
		listeElectorale4 = new ListeElectorale();
		listeElectorale5 = new ListeElectorale();
		listeElectorale6 = new ListeElectorale();
		listeElectorale7 = new ListeElectorale();
		tableListe = new ListeElectorale[7];
	}

	@Test
	public void testCalculerSieges() {
		electionMetier = (IElectionsMetier) appctx.getBean("electionsMetier");
		listeElectorale1.setNom("A");
		listeElectorale1.setVoix(32000);
		listeElectorale2.setNom("B");
		listeElectorale2.setVoix(25000);
		listeElectorale3.setNom("C");
		listeElectorale3.setVoix(16000);
		listeElectorale4.setNom("D");
		listeElectorale4.setVoix(12000);
		listeElectorale5.setNom("E");
		listeElectorale5.setVoix(8000);
		listeElectorale6.setNom("F");
		listeElectorale6.setVoix(4500);
		listeElectorale7.setNom("G");
		listeElectorale7.setVoix(2500);
		ListeElectorale[] listeElectorales = { listeElectorale1,
				listeElectorale2, listeElectorale3, listeElectorale4,
				listeElectorale5, listeElectorale6, listeElectorale7 };
		ListeElectorale[] listeElectorales2 = electionMetier
				.calculerSieges(listeElectorales);
		assertEquals(2, listeElectorales2[0].getSieges());
		assertEquals(1, listeElectorales2[2].getSieges());
		assertEquals(0, listeElectorales2[4].getSieges());
	}

	@Test
	public void test2CalculSieges() {
		electionMetier = (IElectionsMetier) appctx.getBean("electionsMetier");
		ListeElectorale[] listeElectorales = electionMetier
				.getListesElectorales();
		listeElectorales[0].setVoix(32000);
		listeElectorales[1].setVoix(25000);
		listeElectorales[2].setVoix(16000);
		listeElectorales[3].setVoix(12000);
		listeElectorales[4].setVoix(8000);
		listeElectorales[5].setVoix(4500);
		listeElectorales[6].setVoix(2500);
		ListeElectorale[] listes = electionMetier
				.calculerSieges(listeElectorales);
		assertEquals(2, listes[1].getSieges());
		assertEquals(0, listes[4].getSieges());
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void test3CalculSieges() {
		ListeElectorale[] listeElectorales = new ListeElectorale[25];
		for (int i = 0; i < 25; i++) {
			listeElectorales[i] = new ListeElectorale();
		}
		for (int i = 0; i < 25; i++) {
			listeElectorales[i].setVoix(4000);
		}
		ListeElectorale[] listeElectorales2 = electionMetier
				.calculerSieges(listeElectorales);
	}

	@Test
	public void testEcritureResultatsElections() {
		electionMetier = (IElectionsMetier) appctx.getBean("electionsMetier");
		ListeElectorale[] listeElectorales = electionMetier
				.getListesElectorales();
		listeElectorales[0].setVoix(32000);
		listeElectorales[1].setVoix(25000);
		listeElectorales[2].setVoix(16000);
		listeElectorales[3].setVoix(12000);
		listeElectorales[4].setVoix(8000);
		listeElectorales[5].setVoix(4500);
		listeElectorales[6].setVoix(2500);
		ListeElectorale[] listes = electionMetier
				.calculerSieges(listeElectorales);
		for (ListeElectorale listeElectorale : listes) {
			System.out.println(listeElectorale);
		}
		electionMetier.recordResultats(listes);
	}

	@Test
	public void testGetListeElectoral() {
		IElectionsDao electionsDao = createMock(ElectionsDaoImpl.class);
		ListeElectorale[] listeElectorales = { listeElectorale1,
				listeElectorale2, listeElectorale3, listeElectorale4,
				listeElectorale5, listeElectorale6, listeElectorale7 };
		expect(electionsDao.getListesElectorales()).andReturn(listeElectorales);
		replay(electionsDao);
		ElectionsMetierImpl eImpl = new ElectionsMetierImpl();
		eImpl.setiElectionsDao(electionsDao);
		assertEquals(7, listeElectorales.length);
		assertEquals("A", listeElectorale1.getNom());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		appctx = null;
		electionMetier = null;
	}

}
