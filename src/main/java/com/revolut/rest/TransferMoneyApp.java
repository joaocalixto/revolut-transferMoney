package com.revolut.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class TransferMoneyApp {
	
	static Server server = new Server(8090);

	public static void main(String[] args) {
		startServer();
	}
	
	public static void startServer() {
		ServletContextHandler ctx = 
				new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		ctx.setContextPath("/");
		server.setHandler(ctx);

		ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/rest/*");
		serHol.setInitOrder(1);
		serHol.setInitParameter("jersey.config.server.provider.packages", 
				"com.revolut.rest.api");

		try {
			server.start();
			server.join();
		} catch (Exception ex) {
			Logger.getLogger(TransferMoneyApp.class.getName()).log(Level.SEVERE, null, ex);
			server.destroy();
		} finally {
			server.destroy();
		}
	}
	
	public static void destroyServer() {
		server.destroy();
	}
}
