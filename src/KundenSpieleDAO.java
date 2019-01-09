import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class KundenSpieleDAO {
	
	private Connection conn = null;
	private PreparedStatement statement = null;
	
	ConnectToDB connect = new ConnectToDB();
	Spiel spiel = new Spiel();
	KundenSpiele kundenSpiele = new KundenSpiele();
	
	public void insertToKundenSpiele(String KundenSpiele) throws SQLException, ClassNotFoundException {
		try {
			String spieltitel = kundenSpiele.getSpieltitel();
			String spielRelease = kundenSpiele.getSpielRelease();
			String kundennachname = kundenSpiele.getKundennachname();
			String kundenIBAN = kundenSpiele.getKundenIBAN();
			double preis = kundenSpiele.getPreis();
			String menge = kundenSpiele.getMenge();
			String faelligkeitsdatum = kundenSpiele.getFaelligkeitsdatum();
			String ausleihdatum = kundenSpiele.getAusleihdatum();
			String sql = "INSERT INTO KundenSpiele VALUES (" + spieltitel + ", " + spielRelease + ", " + kundennachname + ", " + kundenIBAN + 
					", " + preis + ", " + menge + ", " + faelligkeitsdatum + ", " + ausleihdatum + ")";
			conn = connect.connectToDB();
			statement = conn.prepareStatement(sql);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
