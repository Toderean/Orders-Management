package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    JPanel panel1 = new JPanel();
    JButton client = new JButton("Clients");
    JButton products = new JButton("Products");
    JButton orders = new JButton("Orders");

    public View() {
        this.setSize(new Dimension(300, 200));
        this.setResizable(false);
        this.setTitle("Shop");
        panel1.setLayout(new GridLayout(2, 2));
        client.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        products.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        orders.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        panel1.add(client);
        panel1.add(products);
        panel1.add(orders);
        this.add(panel1);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    public void Client(ActionListener e) {
        client.addActionListener(e);
    }

    public void Products(ActionListener e) {
        products.addActionListener(e);
    }

    public void Orders(ActionListener e) {
        orders.addActionListener(e);
    }

}
