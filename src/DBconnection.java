import java.sql.*;

public class DBconnection {
    private static String sql;
    private static Connection con;
    private static Statement st;
    static ResultSet rs;

    public DBconnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DBconnection.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankapp_database", "root", "@Forthelord47");
        System.out.println("connection established");
    }

    //Insert user information to database
    public boolean updateDB(String sql) throws SQLException{
        boolean updatedDB = false;
        DBconnection.sql = sql;
        DBconnection.st = con.prepareStatement(DBconnection.sql);
        DBconnection.st.executeUpdate(DBconnection.sql);
        updatedDB = true;
        return updatedDB;
    }

    //get user information from database
    public ResultSet getUserInfor(String sql) throws SQLException{
        DBconnection.sql = sql;
        DBconnection.st = con.createStatement();
        DBconnection.rs = st.executeQuery(DBconnection.sql);
        return DBconnection.rs;
    }

    public void closeConnection() throws SQLException{
        DBconnection.con.close();
    }

}
