package com.example.diabetesnote.DataModel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Information extends RealmObject {
    @PrimaryKey
    long id;
    int amount;
    String date;
    String hour;
    String condition;
    String food;
    String drugType;
    String measure;
    String detail;
    String dayOfWeek;

    public void setAll(long id , int amount , String date , String hour , String condition , String food , String drugType , String measure , String detail ,String dayOfWeek){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.hour = hour;
        this.condition = condition;
        this.detail = detail;
        this.drugType = drugType;
        this.food = food;
        this.measure = measure;
        this.dayOfWeek = dayOfWeek;
    }
    public void setAllForEdit( int amount , String date , String hour , String condition , String food , String drugType , String measure , String detail ,String dayOfWeek){
        this.amount = amount;
        this.date = date;
        this.hour = hour;
        this.condition = condition;
        this.detail = detail;
        this.drugType = drugType;
        this.food = food;
        this.measure = measure;
        this.dayOfWeek = dayOfWeek;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
