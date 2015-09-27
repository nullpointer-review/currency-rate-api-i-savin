package ru.newpointer.currency;

/**
 * Created by isavin on 27.09.15.
 */
public class Currency {

    private String code;
    private String rate = "unknown";
    private String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", rate='" + rate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}