package com.bbarke.hackathon.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transaction {
    private final String status;
    private final String date;
    private final String description;
    private final String category;
    private final BigDecimal amount;

    public Transaction(String status, String date, String description, String category, BigDecimal amount) {
        this.status = status;
        this.date = date;
        this.description = description;
        this.category = category;
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(date, that.date) &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, date, description, category, amount);
    }
}
