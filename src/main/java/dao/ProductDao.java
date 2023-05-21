package dao;

import Model.Product;

public class ProductDao extends AbstractDao<Product> {
    public boolean checkAmount(int id,int amount){
        Product temp = findById(id);
        return temp.getCantitate() >= amount;
    }

    public void modifyAmount(int id,int amount){
        Product temp = findById(id);
        String[] dates = new String[]{null,String.valueOf(temp.getCantitate()- amount), String.valueOf(temp.getPret()), temp.getDenumire()};
        update(id,dates);
    }
}
