package ftn.xscience.utils.xmldb;

import java.util.Properties;

public class XMLConnectionProperties {
	
	public String host;
	public int port;
	public String user;
	public String password;
	public String driver;
	public String uri;
	
	public XMLConnectionProperties() {
		
	}
	
	public XMLConnectionProperties(Properties props) {
		
		user = props.getProperty("conn.user").trim();
		password = props.getProperty("conn.password").trim();
		host = props.getProperty("conn.host").trim();
		port = Integer.parseInt(props.getProperty("conn.port").trim());
		String connectionUri = "xmldb:exist://%1$s:%2$s/exist/xmlrpc";
		uri = String.format(connectionUri, host, port);
		driver = props.getProperty("conn.driver").trim();
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	
}
