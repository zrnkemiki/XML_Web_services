package ftn.xscience.util.xmldb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Database;


public class BasicXMLConnectionPool {

	private List<XMLConnectionProperties> connectionPool;
	private List<XMLConnectionProperties> usedConnections = new ArrayList<XMLConnectionProperties>();
	private int initPoolSize = 10;
	
	public XMLConnectionProperties getConnection() {
		if (connectionPool.isEmpty()) {
			throw new RuntimeException("WOPA POOL JE PRAZAN SORRYYYYYYYYYYYYYYYYYY");
		}

		for (XMLConnectionProperties c : connectionPool) {
			System.out.println(c.toString());
		}

		XMLConnectionProperties conn = connectionPool.remove(connectionPool.size() - 1);
		usedConnections.add(conn);
		return conn;
	}
	
	public boolean releaseConnection(XMLConnectionProperties conn) {
		connectionPool.add(conn);
		return usedConnections.remove(conn);
	}
	
	public XMLConnectionProperties createConnection() {
		String propsName = "exist.properties";
		XMLConnectionProperties conn = null;
		
		try {
			InputStream propsStream = BasicXMLConnectionPool.class.getClassLoader().getResourceAsStream(propsName);
            if (propsStream == null)
                throw new IOException("Could not read properties " + propsName);

            Properties props = new Properties();
            props.load(propsStream);

            conn = new XMLConnectionProperties(props);

            Class<?> cl = Class.forName(conn.getDriver());

            Database database = (Database) cl.newInstance();
            database.setProperty("create-database", "true");

            DatabaseManager.registerDatabase(database);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}

	public List<XMLConnectionProperties> getConnectionPool() {
		return connectionPool;
	}

	public void setConnectionPool(List<XMLConnectionProperties> connectionPool) {
		this.connectionPool = connectionPool;
	}

	public List<XMLConnectionProperties> getUsedConnections() {
		return usedConnections;
	}

	public void setUsedConnections(List<XMLConnectionProperties> usedConnections) {
		this.usedConnections = usedConnections;
	}

	public int getInitPoolSize() {
		return initPoolSize;
	}

	public void setInitPoolSize(int initPoolSize) {
		this.initPoolSize = initPoolSize;
	}


	
}
