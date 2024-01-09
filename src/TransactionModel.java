import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;

public class TransactionModel {
    static Map<String, String[]> productData;

    private final JTextField barcodeInput = new JTextField("");
    private JLabel poleDisplay;
    JTextArea virtualJournal = new JTextArea();
    private final JButton nextDollar = new JButton("Next Dollar");
    private final JButton voidTransaction = new JButton("Void Transaction");


    public static void main(String[] args) {
        SwingUtilities.invokeLater(TransactionModel::new);

        loadProductData();
        System.out.println("I");
        for (String name: productData.keySet()) {
            String key = name.toString();
            String value = productData.get(name).toString();

            System.out.println(key + " " + value);

        }
        System.out.println("I");
    }

    private static HashMap<String, String> readFromTSV(String filename) throws IOException {
        HashMap<String, String> items = null;
        BufferedReader br;
        String line;
        try {
            items = new HashMap<String, String>();
            br = new BufferedReader(new FileReader("Tsv.tsv"));
            line = null;

            while ((line = br.readLine()) != null) {
                String[] str = line.split("\t");
                for (int i = 1; i < str.length; i++) {
                    String[] arr = str[i].split(":");
                    items.put(arr[0], arr[1]);
                }
            }
            return items;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    public TransactionModel() {
            loadProductData();
            // readFromTSV("pricebook.tsv");
            initializeUI();

    }

    private static void loadProductData() {
        productData = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Tsv.tsv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                productData.put(values[0], new String[]{values[1], values[2]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        JFrame frame = new JFrame("Gas Station Transaction Model");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setMinimumSize(new Dimension(800, 600));

        frame.add(createBarcodeScannerPanel(), BorderLayout.NORTH);
        frame.add(createVirtualJournalPanel(), BorderLayout.CENTER);
        frame.add(createPoleDisplayPanel(), BorderLayout.SOUTH);
        frame.add(createActionButtonsPanel(), BorderLayout.EAST);
        frame.add(createQuickKeysPanel(), BorderLayout.WEST);

        frame.pack();
        frame.setVisible(true);

        barcodeInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String barcode = barcodeInput.getText();
                String[] productInfo = productData.get(barcode);
                if (productInfo != null) {
                    poleDisplay.setText("Name: " + productInfo[0] + ", Cost: " + productInfo[1]);
                } else {
                    poleDisplay.setText("Product not found");
                }
            }

        });

        voidTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                virtualJournal.setText(" ");
            }
        });

        nextDollar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private JPanel createBarcodeScannerPanel() {
        JPanel barcodePanel = new JPanel(new BorderLayout());
        JTextField barcodeInput = new JTextField();
        barcodePanel.add(barcodeInput, BorderLayout.CENTER);
        return barcodePanel;
    }

    private JScrollPane createVirtualJournalPanel() {
        // JTextArea virtualJournal = new JTextArea();
        return new JScrollPane(virtualJournal);
    }

    private JLabel createPoleDisplayPanel() {
        return new JLabel("Item Name and Cost");
    }

    private JPanel createQuickKeysPanel() {
        JPanel quickKeysPanel = new JPanel(new GridLayout(3, 3));

        quickKeysPanel.add(new JButton("Item 1"));
        quickKeysPanel.add(new JButton("Item 2"));

        return quickKeysPanel;
    }

    private JPanel createActionButtonsPanel() {
        JPanel actionButtonsPanel = new JPanel(new GridLayout(1, 2));

        actionButtonsPanel.add(nextDollar);
        actionButtonsPanel.add(voidTransaction);

        return actionButtonsPanel;
    }


    private static PriceBook createPriceBook(String[] data) {
        String code = data[0];
        String name = data[1];
        float price = Float.parseFloat(data[2]);

        return new PriceBook(name, code, price);
    }

}






