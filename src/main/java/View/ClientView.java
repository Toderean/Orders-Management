package View;

import dao.ClientDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientView extends JFrame {
    ClientDao client = new ClientDao();
    JPanel controlPanel = new JPanel();
    JButton update = new JButton("Update");
    JButton create = new JButton("Create");
    JButton delete = new JButton("Delete");
    JButton find = new JButton("Find");
    JButton back = new JButton("<-" + " Back");
    JPanel tablePanel = new JPanel();
    String[] header = client.getHeader().toArray(new String[0]);
    String[][] row = client.listToMatrix(client.findAll());
    JTable table = new JTable(row, header);
    JScrollPane sp = new JScrollPane(table);
    Font font = new Font("Verdana", Font.CENTER_BASELINE, 12);

    ClientView() throws Exception {
        this.setTitle("Client");
        this.setSize(new Dimension(800, 600));
        this.setResizable(false);
        controlPanel.setSize(new Dimension(800, 100));
        controlPanel.setLayout(new FlowLayout(getInsets().left));
        tablePanel.setSize(new Dimension(800, 500));
        back.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        find.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        delete.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        update.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        create.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        controlPanel.add(back);
        controlPanel.add(find);
        controlPanel.add(delete);
        controlPanel.add(update);
        controlPanel.add(create);
        table.setSize(800,300);
        table.setFont(font);
        tablePanel.add(sp);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(controlPanel);
        panel.add(tablePanel);
        this.add(panel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }


    public void ReturnButton(ActionListener e) {
        back.addActionListener(e);
    }

    public void FindButton(ActionListener e) {
        find.addActionListener(e);
    }

    public void CreateButton(ActionListener e) {
        create.addActionListener(e);
    }

    public void DeleteButton(ActionListener e) {
        delete.addActionListener(e);
    }

    public void UpdateButton(ActionListener e) {
        update.addActionListener(e);
    }
}
