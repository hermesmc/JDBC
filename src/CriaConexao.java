import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CriaConexao {

	public Connection recuperarConexao() throws SQLException {
		String urlConnection = 	"jdbc:mysql://127.0.0.1/loja_virtual?useTimezone=true&serverTimezone=UTC";
		String userConnection = "root";
		String passwordConnection = "Peruas@1";
		return DriverManager.getConnection(urlConnection, userConnection, passwordConnection);
	}
}
