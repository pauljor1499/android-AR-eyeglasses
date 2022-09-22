package com.example.tryonapp;

public class Model {

    private String brand;
    private String price;
    private String model;
    private int img;

    public Model(String brand, String price, String model, int img){
        this.brand = brand;
        this.price = price;
        this.model = model;
        this.img = img;

    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
