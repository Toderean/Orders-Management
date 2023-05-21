package View;

import Model.Client;
import Model.Product;
import dao.BillDao;
import dao.CommandDao;
import dao.ProductDao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdersListener {
    OrdersView v = new OrdersView();

    public OrdersListener() {
        v.BackButton(new ReturnButtonListener());
        v.SubmitButton(new SubmitListener());
    }

    class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Controller c = new Controller();
            v.dispose();
        }
    }

    class SubmitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int amount = Integer.parseInt(v.getAmount());
            Client client = v.getClient();
            Product product = v.getProduct();
            ProductDao productDao = new ProductDao();
            if (productDao.checkAmount(product.getId(), amount)) {
                productDao.modifyAmount(product.getId(), amount);
                CommandDao commandDao = new CommandDao();
                commandDao.insert(null,new String[]{null,String.valueOf(client.getId()),String.valueOf(product.getId()),String.valueOf(amount* product.getPret()),String.valueOf(product.getCantitate())});
                BillDao bill = new BillDao();
                bill.createBill(new String[]{null,String.valueOf(product.getCantitate()),String.valueOf(amount* product.getPret()),String.valueOf(client.getId()),String.valueOf(product.getId())});
                JOptionPane.showMessageDialog(v,"Success");
                v.dispose();
                new OrdersListener();
            } else {
                  JOptionPane.showMessageDialog(v,"Error!");
            }

        }
    }
}
