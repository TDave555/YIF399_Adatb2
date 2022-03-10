package adatbazis;

import model.Car;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;
import java.util.ArrayList;


public class Exec {
private static final String URL="jdbc:oracle:thin:@193.6.5.58:1521:XE";
	
	public static void main(String[] args) {
		
		try {
			Connection conn = connect("H22_YIF399", "YIF399");
			//createTable(conn);
			//insertCar(conn);
			//setPriceOfCarByColor(conn,"zöld",800);
			//setPriceOfCarByColorPrep(conn, "piros", 1000);
			/*String[] sqlString = {
							"insert into car values(10,'Opel','fehér',300)",
							"insert into car values(11,'Seat','zöld',700)",
							"insert into car values(12,'Opel','fehér',200)"
							};
			*/
			//insertMultipleCar(conn, sqlString);
			//deleteCarById(conn, 1);
			//getDatabaseMetadata(conn);
			setCarColorById(conn, "kék", 10);
			getAllCars(conn);
			//getMostExpensiveCar(conn);
			//getAllCarsMeta(conn);
			//getCarsByManufacturer(conn, "Opel");
			
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
	
	public static void createTable(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		System.out.println(stmt.execute("CREATE TABLE car ("
				+ "id number(4) primary key, "
				+ "manufacturer varchar2(200) not null, "
				+ "color varchar2(20) not null, "
				+ "price number(5) not null "
				+ ")"));
	}
	
	public static void insertCar(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		
		System.out.println("Insert returned:" + stmt.executeUpdate("" 
		+ "INSERT INTO car VALUES(3, 'Skoda', 'piros', 600)"));
	}
	
	public static void setPriceOfCarByColor(Connection conn, String color, int price) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("UPDATE car SET price="+price+"WHERE color='"+color+"'");
	}
	
	public static void setPriceOfCarByColorPrep(Connection conn, String color, int price) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement("UPDATE car SET price=? WHERE color=?");
		prstmt.setInt(1, price);
		prstmt.setString(2, color);
		System.out.println(prstmt.executeUpdate());
	}
	
	public static void insertMultipleCar(Connection conn, String[] insertSql) throws SQLException {
		Statement stmt = conn.createStatement();
		for (String sql : insertSql) {
			stmt.addBatch(sql);
		}
		System.out.println(stmt.executeBatch());
	}
	
	public static void deleteCarById(Connection conn, int id) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement("DELETE car WHERE id=?");
		prstmt.setInt(1, id);
		System.out.println("Deleted rows: " + prstmt.executeUpdate());
	}
	
	public static void getDatabaseMetadata(Connection conn) throws SQLException {
		System.out.println("Adatbázis metaadatok: \n");
		System.out.println("driver verzió: " + conn.getMetaData().getDriverVersion());
		String[] specifyTables = {"TABLE"};
		ResultSet rs = conn.getMetaData().getTables(null, null, "%", specifyTables);
		while (rs.next())
			System.out.println(rs.getString(3));
	}
	
	public static void getAllCars(Connection conn) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select * from car");
		List<Car> carList = new ArrayList<>();
		while (rs.next()) {			
			Car car = new Car(
					rs.getInt(1), //id-val
					rs.getString("manufacturer"), //mezõnévvel
					rs.getString(3), //id-val
					rs.getInt("price")
					);//mezõnévvel
			carList.add(car);			
		}
		System.out.println("Összes autó: \n" + carList);
	}
	
	public static void getMostExpensiveCar(Connection conn) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "SELECT * FROM car WHERE price=(SELECT MAX(price) FROM car)");
		ResultSet rs = prstmt.executeQuery();
		rs.next();
		Car car = new Car(
				rs.getInt(1),
				rs.getString(2),
				rs.getString(3),
				rs.getInt(4)
				);
		System.out.println("Legdrágább autó: \n" + car);
	}
	
	public static void getAllCarsMeta(Connection conn) throws SQLException {
		System.out.println("Mezõk metaadatai:");
		PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM car");
		ResultSet rs = prstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		System.out.println("number of columns: " + rsmd.getColumnCount()+"\n");
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			System.out.println(rsmd.getColumnName(i)+": "+rsmd.getColumnTypeName(i));
		}
	}
	
	public static void getCarsByManufacturer(Connection conn, String manufacturer) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement("SELECT * FROM car WHERE manufacturer=?");
		prstmt.setString(1, manufacturer);
		ResultSet rs = prstmt.executeQuery();
		List<Car> carList = new ArrayList<>();
		while(rs.next()) {
			carList.add(
					new Car(
							rs.getInt("id"),
							rs.getString("manufacturer"),
							rs.getString("color"),
							rs.getInt("price")
							)
					);
		}
		System.out.println("Opel márkájú autók: \n" + carList);
	}
		
	public static void setCarColorById(Connection conn, String color, int id) throws SQLException {
		PreparedStatement prstmt = conn.prepareStatement(""
				+ "UPDATE car SET color=? WHERE id=?");
		prstmt.setString(1, color);
		prstmt.setInt(2, id);
		
		System.out.println(prstmt.executeUpdate());
		prstmt.close();
	}
	
	
}
