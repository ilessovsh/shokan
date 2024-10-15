package org.example;

public class Currency {
    private String name;
    private String buyRate;
    private String sellRate;

    public Currency(String name, String buyRate, String sellRate) {
        this.name = name;
        this.buyRate = buyRate;
        this.sellRate = sellRate;
    }

    public String getName() {
        return name;
    }

    public String getBuyRate() {
        return buyRate;
    }

    public String getSellRate() {
        return sellRate;
    }
}
