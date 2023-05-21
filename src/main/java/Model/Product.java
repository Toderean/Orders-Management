package Model;

public class Product {
    private int id;
    private int cantitate;
    private int pret;
    private String denumire;

    public Product() {
    }

    public Product(int cantitate, int pret, String denumire) {
        this.cantitate = cantitate;
        this.pret = pret;
        this.denumire = denumire;
    }

    public Product(int id, int cantitate, int pret, String denumire) {
        this.id = id;
        this.cantitate = cantitate;
        this.pret = pret;
        this.denumire = denumire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", cantitate=" + cantitate +
                ", pret=" + pret +
                ", denumire='" + denumire + '\'' +
                '}';
    }
}
