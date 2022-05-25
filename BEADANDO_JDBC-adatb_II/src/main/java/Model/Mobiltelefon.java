package Model;

import java.sql.Date;

public class Mobiltelefon {
    String azonosito;
    int gyarto_az;
    String kamera;
    String cpu;
    String gpu;
    Date piacra_helyezes;

    public Mobiltelefon(String azonosito, int gyarto_az, String kamera, String cpu, String gpu, Date piacra_helyezes) {
        this.azonosito = azonosito;
        this.gyarto_az = gyarto_az;
        this.kamera = kamera;
        this.cpu = cpu;
        this.gpu = gpu;
        this.piacra_helyezes = piacra_helyezes;
    }

    public String getAzonosito() {
        return azonosito;
    }

    public int getGyarto_az() {
        return gyarto_az;
    }

    public String getKamera() {
        return kamera;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public Date getPiacra_helyezes() {
        return piacra_helyezes;
    }

    public void setAzonosito(String azonosito) {
        this.azonosito = azonosito;
    }

    public void setGyarto_az(int gyarto_az) {
        this.gyarto_az = gyarto_az;
    }

    public void setKamera(String kamera) {
        this.kamera = kamera;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public void setPiacra_helyezes(Date piacra_helyezes) {
        this.piacra_helyezes = piacra_helyezes;
    }

    @Override
    public String toString() {
        return  "azonosító: " + azonosito +
                ", gyártó az: " + gyarto_az +
                ", kamera: " + kamera +
                ", CPU: " + cpu +
                ", GPU: " + gpu +
                ", piacra helyezés dátuma:" + piacra_helyezes + "\n";
    }
}
