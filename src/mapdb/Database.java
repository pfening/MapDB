package mapdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private static final Database instance = new Database();
	private static Connection conn;	
        //private final String dbpath ="jdbc:sqlite:C:\\NetBeansProjects\\MapDB\\src\\mapdb\\dict.db";
        private final String dbpath ="jdbc:sqlite:/home/pfg/NetBeansProjects/MapDB/src/mapdb/dict.db";
	private Database() {
		
	}
	
	public static Database getInstance() {
		return instance;
	}
        
        public Connection getConnection() throws Exception {            
		return conn;
	}

	public void connect() throws Exception {
		Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection(dbpath);
                System.out.println("connected");
	}
	
	public void disconnect() throws SQLException {
		conn.close();		
		conn = null;
                System.out.println("disconnected");
	}
	
}