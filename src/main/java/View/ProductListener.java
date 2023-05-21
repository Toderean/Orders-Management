package View;

import Model.Product;
import dao.ProductDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ProductListener {
    ProductView v = new ProductView();

    public ProductListener() throws Exception {
        v.ReturnButton(new ReturnButtonListener());
        v.FindButton(new FindButton());
        v.UpdateButton(new UpdateButton());
        v.CreateButton(new CreateButton());
        v.DeleteButton(new DeleteButton());
    }

    class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Controller c = new Controller();
            v.dispose();
        }
    }

    class FindButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = JOptionPane.showInputDialog(v, "Enter Id");
            int searchedUser = Integer.parseInt(id);
           try {
                if (new ProductDao().findById(searchedUser) != null) {
                    JOptionPane.showMessageDialog(v, new ProductDao().findById(searchedUser).toString());
                }
            } catch (IndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(v,"Wrong input!");
            }
        }
    }

    class CreateButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame insert = new JFrame();
            ProductDao productDao = new ProductDao();
            Field[] fields = productDao.Type().getDeclaredFields();
            int size = productDao.Type().getDeclaredFields().length;
            JTextField[] texts = new JTextField[size];
            JLabel[] labels = new JLabel[size];
            JButton create = new JButton("Create");
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            for (int i = 1; i < size; i++) {
                labels[i] = new JLabel(fields[i].getName());
                texts[i] = new JTextField(10);
                panel.add(labels[i]);
                panel.add(texts[i]);
            }
            panel.add(create);
            insert.add(panel);
            insert.setResizable(false);
            insert.setSize(new Dimension(1000, 200));
            insert.setLocationRelativeTo(null);
            insert.setVisible(true);
            create.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] data = new String[size];
                    for (int i = 1; i < size; i++) {
                        data[i] = texts[i].getText();
                    }
                    productDao.insert(null, data);
                    v.dispose();
                    insert.dispose();
                    try {
                        new ProductListener();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

        }
    }

    class DeleteButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = JOptionPane.showInputDialog(v, "Enter Id");
            ProductDao c = new ProductDao();
            try {
                for (int i = 0; i < v.table.getRowCount(); i++) {
                    for (int j = 0; j < v.table.getColumnCount(); j++) {
                        if (v.row[i][j].equals(id)) {
                            c.delete(Integer.parseInt(id));
                            v.dispose();
                            try {
                                new ProductListener();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }
            } catch (IndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(v,"Wrong input!");
            }
        }
    }

    class UpdateButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) throws RuntimeException {
            int row = v.table.getSelectedRow();
            int col = 0;
//            System.out.println(v.table.getValueAt(row,col));
            ProductDao productDao = new ProductDao();
            if (Integer.parseInt(String.valueOf(v.table.getValueAt(row, col))) != -1) {
                Product product = productDao.findById(Integer.parseInt(String.valueOf(v.table.getValueAt(row, col))));
                JFrame edit = new JFrame();
                Field[] fields = productDao.Type().getDeclaredFields();
                int size = productDao.Type().getDeclaredFields().length;
                JTextField[] texts = new JTextField[size];
                JLabel[] labels = new JLabel[size];
                JButton create = new JButton("Update");
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                for (int i = 1; i < size; i++) {
                    try {
                        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fields[i].getName(), product.getClass());
                        labels[i] = new JLabel(fields[i].getName());
                        texts[i] = new JTextField(10);
                        texts[i].setText(String.valueOf(propertyDescriptor.getReadMethod().invoke(product)));
                        panel.add(labels[i]);
                        panel.add(texts[i]);
                    } catch (IntrospectionException | InvocationTargetException | IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                panel.add(create);
                edit.add(panel);
                edit.setResizable(false);
                edit.setSize(new Dimension(1000, 200));
                edit.setLocationRelativeTo(null);
                edit.setVisible(true);
                create.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] data = new String[size];
                        for (int i = 1; i < size; i++) {
                            data[i] = texts[i].getText();
                        }
                        productDao.update(Integer.parseInt(String.valueOf(v.table.getValueAt(row, col))), data);
                        v.dispose();
                        edit.dispose();
                        try {
                            new ProductListener();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
            }
        }
    }
}
