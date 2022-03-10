package adatbazis;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Car;
import model.Owner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerExec {
private static final String URL="jdbc:oracle:thin:@193.6.5.58:1521:XE";
	
	public static void main(String[] args) {
		try {
			Connection conn = connect("H22_YIF399", "YIF399");
			
			//createOwnerTable(conn);
			//alterCarTable(conn);
			
			//Owner owner1 = new Owner(1, "Kiss Pista", Date.valueOf("1980-05-20"));
			//Owner owner2 = new Owner(2, "Nagy József", Date.valueOf("1992-11-15"));
			//Owner owner3 = new Owner(3, "Kocsis László", Date.valueOf("1976-02-04"));
			//addOwner(conn, owner1);
			//addOwner(conn, owner2);
			//addOwner(conn, owner3);
			
			//updateOwner(conn, 1, "Skoda");
			//updateOwner(conn, 2, "Opel");
			//updateOwner(conn, 3, "Seat");
			
			//addOwnerOther(conn, 4, "1984-09-25");
			
			getAllData(conn);	
			//getCar(conn, "Skoda");
			
			closeDB(conn);
			System.out.println("End of program");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection connect(String username, String password) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection conn = DriverManager.getConnection(URL, username, password);
		return conn;
	}
	
	public static void closeDB(Connection conn) throws SQLException {
		
	}
	
	public static void createOwnerTable(Connection conn) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "CREATE TABLE owner ("
				+ "id int primary key, "
				+ "name varchar2(200), "
				+ "birth date "
				+ ")");
		
		prstmt.executeUpdate();
	}
	
	public static void alterCarTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(""
				+ "ALTER TABLE car ADD owner_id int "
				+ "CONSTRAINT owner_car REFERENCES owner(id)"
				);
	}
	
	public static void addOwner(Connection conn, Owner owner) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "INSERT INTO owner VALUES(?, ?, ?)"
				);
		prstmt.setInt(1, owner.getId());
		prstmt.setString(2, owner.getName());
		prstmt.setDate(3, owner.getBirth());
		
		prstmt.executeUpdate();
	}
	
	public static void updateOwner(Connection conn, int id, String manufacturer) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "UPDATE car SET owner_id=? WHERE manufacturer=?"
				);
		
		prstmt.setInt(1, id);
		prstmt.setString(2, manufacturer);
		
		System.out.println("updated:" + prstmt.executeUpdate());		
	}
	
	public static void getAllData(Connection conn) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "SELECT * FROM car c "
				+ "RIGHT JOIN owner o ON (c.owner_id=o.id)"
				);
		
		ResultSet rs = prstmt.executeQuery();
		
		while (rs.next()) {
			System.out.println(rs.getString("name")+" - "+rs.getString("manufacturer"));
		}
		
		rs.close();
		prstmt.close();
	}
	
	public static void addOwnerOther(Connection conn, int id, String birth) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "INSERT INTO owner VALUES(?, ?, {d ?})" //birth: yyyy-mm-dd
				);
		prstmt.setInt(1, id);
		prstmt.setString(2, "Hello");
		prstmt.setDate(3, Date.valueOf(birth));
		
		prstmt.executeUpdate();
	}
	
	public static void getCar(Connection conn, String manufacturer) throws SQLException {
		List<Car> carList = new ArrayList<Car>();
		
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "SELECT * FROM car WHERE manufacturer=?", 
				ResultSet.TYPE_SCROLL_SENSITIVE, 
				ResultSet.CONCUR_UPDATABLE);
		
		prstmt.setString(1, manufacturer);
		
		ResultSet rs = prstmt.executeQuery();
		
		while (rs.next()) {
			rs.updateString("color", "hupilila");
			rs.updateRow();
			
			carList.add(new Car(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
		}
		for (Car car : carList) {
			System.out.println(car);
		}
		
	}
}
