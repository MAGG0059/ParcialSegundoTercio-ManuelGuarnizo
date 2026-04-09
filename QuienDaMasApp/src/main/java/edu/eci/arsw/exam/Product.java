package edu.eci.arsw.exam;

public class Product {
    private String code;
    private String description;
    private int startPrice;

    public Product() {}

    public Product(String code, String description, int startPrice) {
        this.code = code;
        this.description = description;
        this.startPrice = startPrice;
    }

    public Product(byte[] body) {
        String content = new String(body);
        String[] parts = content.split("\\|");
        if (parts.length >= 3) {
            this.code = parts[0];
            this.description = parts[1];
            this.startPrice = Integer.parseInt(parts[2]);
        } else {
            this.code = content;
            this.description = "";
            this.startPrice = 0;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public byte[] getBody() {
        return (code + "|" + description + "|" + startPrice).getBytes();
    }
}