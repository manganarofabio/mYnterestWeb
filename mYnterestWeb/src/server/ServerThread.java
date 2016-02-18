package server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;



/**
 * Classe che avvia il thread ReadMain
 *
 */
@WebListener
public class ServerThread implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {

		ReadMain main = new ReadMain();

		Thread t = new Thread(main);

		t.start();


	}

}
