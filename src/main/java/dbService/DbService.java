package dbService;

import dbService.dao.UsersDAO;
import dbService.dataSets.UserDataSet;
import org.h2.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by aleksandr on 23.01.16.
 */
public class DbService {
    private final Connection connection;

    public DbService() throws ClassNotFoundException {
        this.connection = getH2Connection();
    }

    public UserDataSet getUser(long id) throws DbException {
        try {
            return (new UsersDAO(connection).get(id));
        }
        catch (SQLException exception) {
            throw new DbException(exception);
        }
    }

    public UserDataSet getUserByName(String name) throws DbException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            long id = dao.getUserId(name);
            return getUser(id);
        }
        catch (SQLException e) {
            throw new DbException(e);
        }
    }

    public long addUser(String name, String pass) throws DbException {
        try {
            connection.setAutoCommit(false);
            UsersDAO dao = new UsersDAO(connection);
            dao.createTable();
            dao.insertUser(name, pass);
            connection.commit();
            return dao.getUserId(name);
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            }
            catch (SQLException ignore) {

            }
            throw new DbException(e);
        }
        finally {
            try {
                connection.setAutoCommit(true);

            }
            catch (SQLException ignore){}
        }
    }

    public void cleanUp() throws DbException {
        UsersDAO dao = new UsersDAO(connection);
        try {
            dao.dropTable();
        }
        catch (SQLException e){
            throw new DbException(e);
        }
    }

    public void printConnectionInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getH2Connection() throws ClassNotFoundException {
        try {
            DriverManager.registerDriver((Driver) Class.forName("org.h2.Driver").newInstance());
            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            Connection connection = DriverManager.getConnection(url, name, pass);
            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
