import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class Menu {
    String name;
    double price;
    String category;

    public Menu(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}

public class Main {
    private static JFrame mainFrame;
    private static JFrame menuFrame;
    private static List<Menu> menuList;
    private static Map<Menu, Integer> cart;

    public static void main(String[] args) {
        menuList = new ArrayList<>();
        menuList.add(new Menu("Nasi Padang", 25000, "makanan"));
        menuList.add(new Menu("Ayam Goreng", 20000, "makanan"));
        menuList.add(new Menu("Es Teh Manis", 5000, "minuman"));
        menuList.add(new Menu("Sate Ayam", 30000, "makanan"));

        cart = new HashMap<>();

        SwingUtilities.invokeLater(() -> {
            createWelcomeFrame();
        });
    }

    private static void createWelcomeFrame() {
        mainFrame = new JFrame("Selamat Datang");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Selamat Datang di Aplikasi Pemesanan Restoran");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton startButton = new JButton("Mulai");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                createMenuFrame();
            }
        });

        mainFrame.add(welcomeLabel, BorderLayout.CENTER);
        mainFrame.add(startButton, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    private static void createMenuFrame() {
        menuFrame = new JFrame("Menu Restoran");
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel(new GridLayout(0, 4));

        for (Menu menu : menuList) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            ImageIcon menuIcon = new ImageIcon("path/to/menu_image.jpg");
            JLabel imageLabel = new JLabel(menuIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(imageLabel, BorderLayout.CENTER);

            JLabel nameLabel = new JLabel(menu.name);
            nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(nameLabel, BorderLayout.NORTH);

            JLabel priceLabel = new JLabel("Harga: " + menu.price);
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(priceLabel, BorderLayout.CENTER);

            JButton orderButton = new JButton("Pesan");
            orderButton.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(orderButton, BorderLayout.SOUTH);

            orderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String input = JOptionPane.showInputDialog("Masukkan jumlah pesanan:");
                    try {
                        int quantity = Integer.parseInt(input);
                        if (quantity > 0) {
                            cart.put(menu, quantity);
                            JOptionPane.showMessageDialog(menuFrame, "Anda telah menambahkan " + quantity + " " + menu.name + " ke keranjang.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(menuFrame, "Masukkan jumlah pesanan yang valid.");
                    }
                }
            });

            menuPanel.add(panel);
        }

        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuFrame.add(menuScrollPane, BorderLayout.CENTER);

        JButton viewCartButton = new JButton("Keranjang Belanja");
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCartFrame();
            }
        });

        menuFrame.add(viewCartButton, BorderLayout.SOUTH);

        menuFrame.pack();
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setVisible(true);
    }

    private static void createCartFrame() {
        JFrame cartFrame = new JFrame("Keranjang Belanja");
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel cartPanel = new JPanel();
        cartPanel.setLayout(new GridLayout(cart.size() + 2, 2));

        for (Map.Entry<Menu, Integer> entry : cart.entrySet()) {
            Menu menu = entry.getKey();
            int quantity = entry.getValue();

            JLabel nameLabel = new JLabel(menu.name);
            cartPanel.add(nameLabel);

            JLabel quantityLabel = new JLabel("Jumlah: " + quantity);
            cartPanel.add(quantityLabel);
        }

        JLabel totalLabel = new JLabel("Total Belanja: Rp. " + calculateTotal(cart));
        cartPanel.add(totalLabel);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cart.clear();
                cartFrame.dispose();
                JOptionPane.showMessageDialog(menuFrame, "Terima kasih atas pesanan Anda!");
            }
        });

        cartPanel.add(checkoutButton);

        cartFrame.add(cartPanel);
        cartFrame.pack();
        cartFrame.setLocationRelativeTo(null);
        cartFrame.setVisible(true);
    }

    private static double calculateTotal(Map<Menu, Integer> cart) {
        double total = 0;
        for (Map.Entry<Menu, Integer> entry : cart.entrySet()) {
            Menu menu = entry.getKey();
            int quantity = entry.getValue();
            total += menu.price * quantity;
        }
        return total;
    }
}
