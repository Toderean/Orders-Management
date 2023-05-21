package Model;

public class Client {
    private int id;
    private String name;
    private String adresa;
    private int varsta;

    public Client(){
    }

    public Client(int id, String name, String adresa, int varsta) {
        this.id = id;
        this.name = name;
        this.adresa = adresa;
        this.varsta = varsta;
    }

    public Client(String name, String adresa, int varsta) {
        this.name = name;
        this.adresa = adresa;
        this.varsta = varsta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", adresa='" + adresa + '\'' +
                ", varsta=" + varsta +
                '}';
    }
}
