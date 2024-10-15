package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class CurrencyTable {

    private final JFrame frame;
    private final DefaultTableModel tableModel;

    public CurrencyTable() {
        frame = new JFrame("Currency Exchange");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());


        JPanel topPanel = new JPanel(new FlowLayout());
        JButton button = new JButton("Показать");
        String[] dates = {"13.10.2024", "12.10.2024", "11.10.2024"};
        JComboBox<String> dateBox = new JComboBox<>(dates);
        topPanel.add(button);
        topPanel.add(dateBox);
        frame.add(topPanel, BorderLayout.NORTH);


        String[] columnNames = {"Валюта", "Покупка", "Продажа"};
        Object[][] data = {
                {"USD", "", ""},
                {"EUR", "", ""},
                {"RUB", "", ""}
        };

        tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);


        button.addActionListener(e -> {
            String selectedDate = (String) dateBox.getSelectedItem();
            CurrencyFetcher currencyFetcher = new CurrencyFetcher();
            List<Currency> currencies = currencyFetcher.parseCurrencyRates(currencyFetcher.getCurrencyRates(selectedDate));

            if (currencies.isEmpty()) {
                System.err.println("Не удалось получить курсы валют.");
                return;
            }

            for (Currency currency : currencies) {
                if (currency.getName().equals("USD")) {
                    tableModel.setValueAt(currency.getName(), 0, 0);
                    tableModel.setValueAt(currency.getBuyRate(), 0, 1);
                    tableModel.setValueAt(currency.getSellRate(), 0, 2);
                } else if (currency.getName().equals("EUR")) {
                    tableModel.setValueAt(currency.getName(), 1, 0);
                    tableModel.setValueAt(currency.getBuyRate(), 1, 1);
                    tableModel.setValueAt(currency.getSellRate(), 1, 2);
                } else if (currency.getName().equals("RUB")) {
                    tableModel.setValueAt(currency.getName(), 2, 0);
                    tableModel.setValueAt(currency.getBuyRate(), 2, 1);
                    tableModel.setValueAt(currency.getSellRate(), 2, 2);
                }
            }
        });


        frame.setVisible(true);
    }
}