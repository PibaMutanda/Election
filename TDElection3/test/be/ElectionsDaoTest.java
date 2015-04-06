package be;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import be.dao.IElectionsDao;
import be.modele.ListeElectorale;
import be.util.ConnexionFile;

public class ElectionsDaoTest {

	private static IElectionsDao electionsDao = null;
	private static ApplicationContext appctx = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		appctx = new FileSystemXmlApplicationContext("applicationContext.xml");

	}

	@Test
	public void testElectionsDaoImplGetBean() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		assertNotNull(electionsDao);
	}

	@Test
	public void testElectionDaoImpl() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		assertEquals(7, electionsDao.getListesElectorales().length);
		assertEquals(0.05, electionsDao.getSeuilElectoral(), 0.0);

	}

	@Test
	public void testGetListesElectorales() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		assertEquals(7, electionsDao.getListesElectorales().length);
	}

	@Test
	public void testGetSeuilElectoral() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		assertEquals(0.05, electionsDao.getSeuilElectoral(), 0.0);
	}

	@Test
	public void testGetNbSiegesAPourvoir() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		assertEquals(6, electionsDao.getNbSiegesAPourvoir());
	}

	@Test
	public void testLectureDataElections() {
		electionsDao = (IElectionsDao) appctx.getBean("electionsDao");
		ListeElectorale[] listeElectorales = electionsDao
				.getListesElectorales();
		assertEquals("A", listeElectorales[0].getNom());
		assertEquals("G", listeElectorales[6].getNom());
	}

	@Test
	public void testEcritureResultatsElections() {
		ListeElectorale listeElectorale = new ListeElectorale(1, "PS", 4521, 5,
				false);
		ListeElectorale listeElectorale2 = new ListeElectorale(2, "MR", 2450,
				2, false);
		ListeElectorale listeElectorale3 = new ListeElectorale(3, "CDH", 2003,
				2, false);
		ListeElectorale[] listeElectorales = { listeElectorale,
				listeElectorale2, listeElectorale3 };
		assertEquals(true, ConnexionFile.writeInFile("data/elections.out.txt",
				listeElectorales));
		assertEquals("CDH", listeElectorales[2].getNom());
		assertEquals(4521, listeElectorales[0].getVoix());
		assertEquals(2, listeElectorales[1].getId());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		electionsDao = null;
		appctx = null;
	}

}
