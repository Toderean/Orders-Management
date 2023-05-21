package View;

import Model.Client;
import Model.Product;
import dao.ClientDao;
import dao.ProductDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OrdersView extends JFrame {
    JComboBox<Client> client;
    JComboBox<Product> product;
    ProductDao p = new ProductDao();
    ClientDao c = new ClientDao();
    JButton back = new JButton("Back");
    JButton submit = new JButton("Submit");
    JPanel panel = new JPanel();
    JTextField amount = new JTextField();

    OrdersView() {
        this.setTitle("Orders");
        this.setSize(new Dimension(1000, 150));
        this.setResizable(false);
        submit.setPreferredSize(new Dimension(75, 25));
        amount.setPreferredSize(new Dimension(75,25));
        client = new JComboBox<>();
        for (Client temp : c.findAll()) {
            client.addItem(temp);
        }
        product = new JComboBox<>();
        for (Product temp : p.findAll()) {
            product.addItem(temp);
        }
        panel.add(submit);
        panel.add(back);
        panel.add(client);
        panel.add(product);
        panel.add(amount);
        panel.add(submit);
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public Product getProduct(){
        return (Product) product.getSelectedItem();
    }
    public Client getClient(){
        return (Client) client.getSelectedItem();
    }

    public String getAmount() {
        return amount.getText();
    }

    public void SubmitButton(ActionListener e) {
        submit.addActionListener(e);
    }

    public void BackButton(ActionListener e) {
        back.addActionListener(e);
    }
}
