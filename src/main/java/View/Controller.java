package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    View v = new View();
    public Controller(){
        v.Client(new ClientViewListener());
        v.Products(new ProductViewListener());
        v.Orders(new OrdersViewListener());
    }
    class ClientViewListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ClientListener view = new ClientListener();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            v.dispose();
        }
    }

    class ProductViewListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ProductListener view = new ProductListener();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            v.dispose();
        }
    }

    class OrdersViewListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            OrdersListener o = new OrdersListener();
            v.dispose();
        }
    }
}
