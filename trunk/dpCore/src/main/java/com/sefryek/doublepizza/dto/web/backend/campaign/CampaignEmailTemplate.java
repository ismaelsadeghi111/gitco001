package com.sefryek.doublepizza.dto.web.backend.campaign;

import java.util.Date;

public class CampaignEmailTemplate extends CampaignEmail {
    private String name;
    private String imagePath;
    private String price;
    private String foodName;
    private String categoryId;
    private String productNumber;
    private Date creationDate;
    private String description;
    private String foodCategory;
    private String productName;
    private String webDescFR;
    private String webDescEN;
    private int category;


    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWebDescFR() {
        return webDescFR;
    }

    public void setWebDescFR(String webDescFR) {
        this.webDescFR = webDescFR;
    }

    public String getWebDescEN() {
        return webDescEN;
    }

    public void setWebDescEN(String webDescEN) {
        this.webDescEN = webDescEN;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
