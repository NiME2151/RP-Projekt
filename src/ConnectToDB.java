import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB {

	// Methode zum Aufbau einer Verbindung mit SQLite
		public Connection connectToDB() throws ClassNotFoundException {
			Connection conn = null;
			Class.forName("org.sqlite.JDBC");
			// Verzeichnispfad h�ngt davon ab wo eine Datei ist
			// dateiNico nicht anfassen!
			// String dateiNico = "C:\\Users/supervisor/Desktop/Programme/test.db3";
			URL datei = getClass().getResource("/test.db3");
			String url = "jdbc:sqlite::resource" + datei;
			// try-catch versucht Verbindung zu SQLite aufzubauen
			try {
				conn = DriverManager.getConnection(url);
				// Gibt Nachricht aus bei funktionierender Verbindung
				System.out.println("DB gefunden!");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			return conn;
		}

}