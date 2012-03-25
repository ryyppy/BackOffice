package dal;

import java.sql.*;

public abstract class DatabaseAdapter {
    private String jdbcDriver = null;
    private String dbUser = null;
    private String dbPassword = null;
    private String dbUrl = null;
    protected Connection con = null;

    protected DatabaseAdapter(String jdbcDriver, String dbUser, String dbPassword, String dbUrl){
        this.jdbcDriver = jdbcDriver;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbUrl = dbUrl;
    }

    public void connect() throws SQLException {
        if (con == null) {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException ex) {
                System.err.println("Fehler beim Laden des JDBC-Treibers");
                return;
            }
            con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
    }

    public void disconnect() throws SQLException {
        if (con != null) {
            con.close();
            con = null;
        }
    }

    public boolean isConnected(){
        return con != null;
    }
    
    public abstract DBEntity getEntityByID(Object id, Class<? extends DBEntity> entityClass) throws SQLException;
}
