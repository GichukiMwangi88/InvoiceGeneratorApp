import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{
    private static final String URL = "jdbc:mysql://Gichuki_Lenovo:3306/invoice_db";
    private static final String USERNAME = "Gichuki88";
    private static final String PASSWORD = "Thestig881925$$";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

}
