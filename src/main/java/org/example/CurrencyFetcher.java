package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CurrencyFetcher {


    public Document getCurrencyRates(String date) {
        String apiUrl = "https://nationalbank.kz/rss/get_rates.cfm?fdate=" + date;
        try {
            System.out.println("Запрашиваемый URL: " + apiUrl);
            return Jsoup.connect(apiUrl).get();  
        } catch (IOException e) {
            System.err.println("Ошибка при получении данных: " + e.getMessage());
            return null;
        }
    }


    public List<Currency> parseCurrencyRates(Document document) {
        List<Currency> currencies = new ArrayList<>();
        if (document != null) {
            Elements currencyList = document.select("item"); 

            for (Element currency : currencyList) {
                String currencyName = currency.select("title").text(); 
                String description = currency.select("description").text(); 

                System.out.println("Обрабатываем валюту: " + currencyName);
                System.out.println("Описание: " + description);


                if (currencyName.contains("USD") || currencyName.contains("EUR") || currencyName.contains("RUB")) {
                    String[] parts = description.split(",");  

                    String buyRate = (parts.length >= 1) ? extractRate(parts[0]) : "N/A";
                    String sellRate = (parts.length >= 2) ? extractRate(parts[1]) : buyRate; 

                    currencies.add(new Currency(currencyName, buyRate, sellRate));
                }
            }
        } else {
            System.err.println("Документ не загружен.");
        }
        return currencies;
    }

    private String extractRate(String rateInfo) {
        String[] parts = rateInfo.split(":");
        if (parts.length == 2) {
            return parts[1].trim();
        } else {
            return rateInfo.trim();
        }
    }
}
