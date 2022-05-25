package Exec;

import java.sql.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.*;


public class Create_DB {
    private static final String URL="jdbc:oracle:thin:@193.6.5.58:1521:XE";

    public static void main(String[] args) {
        try {
            Scanner scrInput = new Scanner(System.in);
            String uname, pw;
            System.out.println("Adja meg a felhasználónevét és jelszavát!");
            System.out.print("Felhasználónév: ");
            uname = scrInput.next();
            System.out.print("Jelszó: ");
            pw = scrInput.next();

            Connection conn = connectToDB(uname, pw);
            System.out.println(conn);
            System.out.println("\nBejelentkezés sikeres! \n\n\nAdatbázis Táblák létrehozása...\n");

            List<String> sqlCreateList = new ArrayList<>();

            sqlCreateList.add("CREATE TABLE Gyartok (" +
                    "gyarto_azonosito NUMBER(2) PRIMARY KEY," +
                    "nev VARCHAR2(30)," +
                    "telephely VARCHAR2(30)," +
                    ")");

            sqlCreateList.add("CREATE TABLE Mobiltelefonok (" +
                    "azonosito VARCHAR2(10) PRIMARY KEY," +
                    "gyarto_az NUMBER(2) NOT NULL REFERENCES Gyartok(gyarto_azonosito)," +
                    "kamera VARCHAR2(100)," +
                    "cpu VARCHAR(30)," +
                    "gpu VARCHAR2(30)," +
                    "piacra_helyezes_datuma DATE" +
                    ")");

            if(!tablesExists(conn, "Gyartok", "Mobiltelefonok") ) {
                crateBatch(conn, sqlCreateList);
                System.out.println("Táblák létrehozása véget ért.\n\n");
            } else {
                System.out.println("A táblák már léteznek az adatbáziban.\n\n");
            }

            System.out.println("Feltöltés dummy adatokkal...\n\n");

            Gyarto[] gyartok = new Gyarto[3];
            gyartok[0] = new Gyarto(1, "Apple Hungary Kft.", "Budapest, Magyarország");
            gyartok[1] = new Gyarto(2, "Samsung Electronics Magyar Zrt.", "Jászfényszaru, Magyarország");
            gyartok[2] = new Gyarto(3, "Xiaomi Inc.", "Peking, Kína");

            for (Gyarto gyarto : gyartok) {
                insertGyarto(conn, gyarto);
            }

            Mobiltelefon[] mobiltelefonok = new Mobiltelefon [6];
            mobiltelefonok[0] = new Mobiltelefon("A2484", 1, "Tripla, főkamera: 12MP f2.8 OIS", "A15 Bionic", "Apple A15", Date.valueOf("2021-09-14"));
            mobiltelefonok[1] = new Mobiltelefon("A2341", 1, "Tripla, főkamera: 12MP f2.0 OIS", "A14 Bionic", "Apple A14", Date.valueOf("2020-10-13"));
            mobiltelefonok[2] = new Mobiltelefon("SM-G981", 2, "Tripla, főkamera: 64MP f2.0 OIS", "Exynos 990", "Adreno 650", Date.valueOf("2020-03-06"));
            mobiltelefonok[3] = new Mobiltelefon("SM-G973F", 2, "Tripla, főkamera: 12MP f2.4 OIS", "Exynos 9820", "Adreno 640", Date.valueOf("2019-03-08"));
            mobiltelefonok[4] = new Mobiltelefon("2201122G", 3, "Tripla, főkamera: 50MP f1.9 OIS", "Snapdragon 8 Gen 1", "Adreno 730", Date.valueOf("2021-12-31"));
            mobiltelefonok[5] = new Mobiltelefon("M2102J20SG", 3, "Quadra, főkamera: 48MP f1.8 EIS", "Snapdragon 860", "Adreno 640", Date.valueOf("2021-03-24"));

            for (Mobiltelefon mobil : mobiltelefonok) {
                insertMobiltelefon(conn, mobil);
            }

            System.out.println("Adatok feltöltése befejeződött.\n\n");

            int valasz;
            boolean kilepes = false;

            while(!kilepes) {
                System.out.println("Válassza ki az alábbiak közül milyen műveletet szeretne végezni!\n");
                System.out.println("0 - kilépés \n1 - új gyártó felvitele \n2 - új mobiltelefon felvitele \n3 - gyár törlése \n4 - mobiltelefon törlése \n5 - gyártó módosítása \n6 - egy gyártó adott időintervallumban piacra helyezett termékeinek száma");

                while (!scrInput.hasNextInt()) {
                    System.out.println("Számot adjon meg!");
                    scrInput.next();
                }
                valasz = scrInput.nextInt();

                switch (valasz) {
                    case 0:
                        kilepes = true;
                        break;
                    case 1:
                        System.out.println("Eddigi Gyártók:");
                        getAllGyarto(conn);
                        int gy_az;
                        String gy_nev;
                        String telephely;
                        System.out.print("\n\nAdja meg az új gyártó azonosítóját: ");
                        gy_az = scrInput.nextInt();
                        System.out.print("Adja meg az új gyártó nevét: ");
                        gy_nev = scrInput.next();
                        System.out.print("Adja meg az új gyártó telephelyét: ");
                        telephely = scrInput.next();
                        Gyarto ujgyarto = new Gyarto(gy_az, gy_nev, telephely);
                        insertGyarto(conn, ujgyarto);
                        break;
                    case 2:
                        System.out.println("Eddigi Mobilok:");
                        getAllMobiltelefon(conn);
                        String m_az;
                        int gyart_az;
                        String m_kamera;
                        String m_cpu;
                        String m_gpu;
                        Date m_piacra_helyezes;

                        System.out.print("\n\nAdja meg az új telefon azonosítóját: ");
                        m_az = scrInput.next();
                        System.out.print("Adja meg az új telefon gyártójának azonosítóját: ");
                        gyart_az = scrInput.nextInt();
                        System.out.print("Adja meg az új telefon kamerájának adatait: ");
                        m_kamera = scrInput.next();
                        System.out.print("Adja meg az új telefon CPU-jának típusát: ");
                        m_cpu = scrInput.next();
                        System.out.print("Adja meg az új telefon GPU-jának típusát: ");
                        m_gpu = scrInput.next();
                        System.out.print("Adja meg az új telefon piacra helyezésének dátumát (yyyy-mm-dd): ");
                        m_piacra_helyezes = Date.valueOf(scrInput.next());
                        Mobiltelefon ujmobil = new Mobiltelefon(m_az, gyart_az, m_kamera, m_cpu, m_gpu, m_piacra_helyezes);
                        insertMobiltelefon(conn, ujmobil);
                        break;
                    case 3:
                        System.out.println("Eddigi Gyártók:");
                        getAllGyarto(conn);
                        int gy_id;
                        System.out.print("Adja meg a törölni kívánt gyártó azonsítóját:");
                        gy_id = scrInput.nextInt();
                        deleteGyartoById(conn, gy_id);
                        break;
                    case 4:
                        System.out.println("Eddigi Mobilok:");
                        getAllMobiltelefon(conn);
                        String m_id;
                        System.out.print("Adja meg a törölni kívánt mobil azonsítóját:");
                        m_id = scrInput.next();
                        deleteMobilById(conn, m_id);
                        break;
                    case 5:
                        System.out.println("Eddigi Gyártók:");
                        getAllGyarto(conn);
                        int id;
                        String uj_nev;
                        System.out.print("Adja meg a módosítani kívánt gyártó azonosítóját:");
                        id = scrInput.nextInt();
                        System.out.print("Adja meg a gyártó új nevét:");
                        uj_nev = scrInput.next();
                        updateNevOfGyartoById(conn, id, uj_nev);
                        break;
                    case 6:
                        Date kezdet;
                        Date vege;
                        System.out.print("Adja meg a kezdő dátumot:");
                        kezdet = Date.valueOf(scrInput.next());
                        System.out.print("Adja meg a befejező dátumot:");
                        vege = Date.valueOf(scrInput.next());
                        getMobilokSzamaIntervallumbanGyartonkent(conn, kezdet, vege);
                        break;
                    default:
                        System.out.println("Erre a számra nincs parancs.");
                }

            }

            closeDB(conn);

        }catch (Exception e) {
            if (e.getMessage().contains("ORA-01017: invalid username/password; logon denied"))
                System.out.println("\n\nHibás felhasználónév vagy jelszó! Kérem indítsa el újra a programot");
            else
                e.printStackTrace();
        }
    }


    public static Connection connectToDB(String username, String password) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, username, password);
    }

    public static void crateBatch(Connection conn, List<String> sqlCommands) throws SQLException {
        Statement stmt = conn.createStatement();
        for (String command : sqlCommands) {
            stmt.addBatch(command);
        }
        System.out.println(Arrays.toString(stmt.executeBatch()));
    }

    public static boolean tablesExists(Connection conn, String tName1, String tName2) throws SQLException {
        boolean exists = false;
        String[] specifyTables = {"TABLE"};
        ResultSet rs = conn.getMetaData().getTables(null, null, "%", specifyTables);
        while (rs.next()) {
            if (rs.getString(3).equalsIgnoreCase(tName1) || rs.getString(3).equalsIgnoreCase(tName2) ) {
                exists = true;
                break;
            }
            //System.out.println(rs.getString(3));
        }
        return exists;
    }

    public static void insertGyarto(Connection conn, Gyarto gyarto) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("INSERT INTO Gyartok VALUES(?, ?, ?)");
        prstmt.setInt(1, gyarto.getGyarto_azonosito());
        prstmt.setString(2, gyarto.getNev());
        prstmt.setString(3, gyarto.getTelephely());

        System.out.println(prstmt.executeUpdate());
    }

    public static void insertMobiltelefon(Connection conn, Mobiltelefon mobil) throws SQLException {
        Statement stmt = conn.createStatement();
        if (stmt.executeUpdate("SELECT * FROM Gyarto WHERE gyarto_azonosito = " + mobil.getGyarto_az()) != 0) {
            PreparedStatement prstmt = conn.prepareStatement("INSERT INTO Mobiltelefonok VALUES(?, ?, ?, ?, ?, ?)");
            prstmt.setString(1, mobil.getAzonosito());
            prstmt.setInt(2, mobil.getGyarto_az());
            prstmt.setString(3, mobil.getKamera());
            prstmt.setString(4, mobil.getCpu());
            prstmt.setString(5, mobil.getGpu());
            prstmt.setDate(6, mobil.getPiacra_helyezes());

            System.out.println(prstmt.executeUpdate());
        } else {
            System.out.println("Nem található gyártó a megadott azonosítóval: " + mobil.getGyarto_az());
        }
     }

    public static void getAllGyarto(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Gyartok");

        List<Gyarto> gyList = new ArrayList<>();

        while (rs.next()) {
            Gyarto gyarto = new Gyarto(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3)
            );
            gyList.add(gyarto);
        }
        for (Gyarto gyarto : gyList) {
            System.out.println("Gyártók: \n" + gyList);
        }
    }

    public static void getAllMobiltelefon(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Mobiltelefonok");

        List<Mobiltelefon> mList = new ArrayList<>();

        while (rs.next()) {
            Mobiltelefon mobiltelefon = new Mobiltelefon(
                    rs.getString(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getDate(6)
            );
            mList.add(mobiltelefon);
        }
        for (Mobiltelefon mobil : mList) {
            System.out.println("Mobilok: \n" + mobil);
        }
    }

    public static void deleteGyartoById(Connection conn, int id) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("DELETE FROM Gyartok WHERE gyarto_azonosito=?");
        prstmt.setInt(1, id);
        System.out.println(prstmt.executeUpdate());
    }

    public static void deleteMobilById(Connection conn, String id) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("DELETE FROM Gyartok WHERE azonsito=?");
        prstmt.setString(1, id);
        System.out.println(prstmt.executeUpdate());
    }

    public static void updateNevOfGyartoById(Connection conn, int id, String nev) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("UPDATE Gyartok SET nev=? WHERE id=?");
        prstmt.setString(1, nev);
        prstmt.setInt(2, id);
        System.out.println(prstmt.executeUpdate());
    }

    public static void getMobilokSzamaIntervallumbanGyartonkent(Connection conn, Date kezdet, Date vege) throws SQLException {
        PreparedStatement prstmt = conn.prepareStatement("SELECT gyarto_azonosito, nev, telephely, COUNT(azonosoito) AS mobilok_szama FROM Gyartok" +
                "JOIN Mobiltelefonok ON Gyartok.gyarto_azonosito = Mobiltelefonok.gyarto_az WHERE Mobiltelefon.piacra_helyezes_datuma BETWEEN ? AND ?" +
                "GROUP BY Gyartok.gyarto_azonosito");
        prstmt.setDate(1, kezdet);
        prstmt.setDate(2, vege);

        ResultSet rs = prstmt.executeQuery();

        while (rs.next()) {
            System.out.println("\n gyartó azonosító: " + rs.getInt(1) + ", név: " + rs.getString(2) +
                    ", telephely: " + rs.getString(3) + ", piacra helyezett mobilok száma: " + rs.getInt(4));
        }
    }

    public static void closeDB(Connection conn) throws SQLException {
        conn.close();
        System.out.println("A program futása véget ért.");
    }

}
