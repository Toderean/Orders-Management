package Model;

public class Command {
    private int id;
    private int id_client;
    private int id_produs;
    private int suma;
    private int cantitate;

    public Command() {
    }

    public Command(int id, int id_client, int id_produs, int suma, int cantitate,int bill_id) {
        this.id = id;
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.suma = suma;
        this.cantitate = cantitate;
    }

    public Command(int id_client, int id_produs, int suma, int cantitate) {
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.suma = suma;
        this.cantitate = cantitate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
}
