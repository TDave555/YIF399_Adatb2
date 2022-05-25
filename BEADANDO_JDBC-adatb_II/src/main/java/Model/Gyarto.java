package Model;

public class Gyarto {
    int gyarto_azonosito;
    String nev;
    String telephely;

    public Gyarto(int gyarto_azonosito, String nev, String telephely) {
        this.gyarto_azonosito = gyarto_azonosito;
        this.nev = nev;
        this.telephely = telephely;
    }

    public int getGyarto_azonosito() {
        return gyarto_azonosito;
    }

    public String getNev() {
        return nev;
    }

    public String getTelephely() {
        return telephely;
    }

    public void setGyarto_azonosito(int gyarto_azonosito) {
        this.gyarto_azonosito = gyarto_azonosito;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setTelephely(String telephely) {
        this.telephely = telephely;
    }

    @Override
    public String toString() {
        return "gyártó azonosító: " + gyarto_azonosito +
                ", név: " + nev +
                ", telephely: " + telephely + "\n";
    }
}
