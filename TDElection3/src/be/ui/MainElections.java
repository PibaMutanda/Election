package be.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainElections {

	private static ApplicationContext appctx = null;

	public static void main(String[] args) {
		appctx = new FileSystemXmlApplicationContext("applicationContext.xml");
		IElectionsUI electionsUI = (IElectionsUI) appctx.getBean("electionsUI");
		electionsUI.run();
	}

}
