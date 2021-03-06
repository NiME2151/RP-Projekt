
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.JTable;
public class KundenSpieleDAO {
	
	private Connection conn = null;
	private PreparedStatement preparedStatement = null;
	private Statement statement = null;
	GetWertInZeile getWertInZeile = new GetWertInZeile();
	
	public void insert(KundenSpiele kundenSpiele, String spiel) throws SQLException, ClassNotFoundException {
		try {
			int lageranzahl = new SpielDAO().selectSpiel(spiel).getLageranzahl();
			String verfuegbarkeit = new SpielDAO().selectSpiel(spiel).getVerfuegbarkeit();
			//new AusleihfensterDAO().increaseCounter(spiel, String.valueOf(lageranzahl));
			String spieleID = kundenSpiele.getSpieleID();
			String kundenID = kundenSpiele.getKundenID();
			double ausleihpreis = kundenSpiele.getPreis();
			String ausleihmenge = kundenSpiele.getAusleihmenge();
			String faelligkeitsdatum = kundenSpiele.getFaelligkeitsdatum();
			String ausleihdatum = kundenSpiele.getAusleihdatum();
			String sqlSetLageranzahlNull = "UPDATE Spiele SET Lageranzahl = '0' WHERE ID = '" + spieleID + "'";
			String sqlAusleihmenge = "UPDATE Spiele SET Lageranzahl = Lageranzahl-" + ausleihmenge +  " WHERE ID = '" + spieleID + "'";
			String sqlVerfuegbarkeit = "UPDATE Spiele SET Verfuegbarkeit = 'verliehen' WHERE ID = '" + spieleID + "'";
			String sqlInsert = "INSERT INTO KundenSpiele VALUES ('" + spieleID + "', '" + kundenID + "', '" + ausleihpreis + "', '" + ausleihmenge + "', '" + faelligkeitsdatum + "', " + "CURRENT_DATE)";
			String sqlUpdate = "UPDATE Spiele SET AusleihCounter = AusleihCounter+1 WHERE ID = '" + spieleID + "'";
			Connection conn = ConnectToDB.getConnection();
			preparedStatement = conn.prepareStatement(sqlInsert);
			preparedStatement.executeUpdate();
			preparedStatement = conn.prepareStatement(sqlUpdate);
			preparedStatement.executeUpdate();
			
			if (lageranzahl - Integer.valueOf(ausleihmenge) > 0) {
				preparedStatement = conn.prepareStatement(sqlAusleihmenge);
				preparedStatement.executeUpdate();
			}
			if (lageranzahl - Integer.valueOf(ausleihmenge) == 0) {
				preparedStatement = conn.prepareStatement(sqlSetLageranzahlNull);
				preparedStatement.executeUpdate();
				preparedStatement = conn.prepareStatement(sqlVerfuegbarkeit);
				preparedStatement.executeUpdate();
			}
			
			System.out.println(spieleID + "\n" + kundenID+ "\n" + ausleihpreis + "\n" + ausleihmenge + "\n" + faelligkeitsdatum+ "\n" + ausleihdatum + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public KundenSpiele selectKundenSpiele(JTable table) {
		KundenSpiele kundenSpiele = new KundenSpiele();
		Spiel spiel = new Spiel();
		
		String spieleID = getWertInZeile.getWertInZeileVariabel(table, 0);
		String kundenID = getWertInZeile.getWertInZeileVariabel(table, 1);
		String faelligkeitsdatum = getWertInZeile.getWertInZeileVariabel(table, 4);
		String ausleihdatum = getWertInZeile.getWertInZeileVariabel(table, 5);
		String ausleihcounter = getWertInZeile.getWertInZeileVariabel(table, 6);
		
		kundenSpiele.setSpieleID(spieleID);
		kundenSpiele.setKundenID(kundenID);
		kundenSpiele.setFaelligkeitsdatum(faelligkeitsdatum);
		kundenSpiele.setAusleihdatum(ausleihdatum);
		spiel.setAusleihCounter(Integer.valueOf(ausleihcounter));
				
		ResultSet rs = null;
		String sql = "SELECT * FROM KundenSpiele WHERE KundenID = " + spieleID + " AND SpieleID = " + kundenID
					+ " AND Faelligkeitsdatum = " + faelligkeitsdatum + " AND Ausleihdatum = " + ausleihdatum;
		try {
			// connect()-Methode wird ausgeführt um eine Verbindung zur Datenbank
			// herzustellen
			Connection conn = ConnectToDB.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			// Gibt Nachricht aus bei funktionierendem SELECT
			System.out.println("SQL-SELECT funzt");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return kundenSpiele;
	}
}

