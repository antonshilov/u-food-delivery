package gq.vaccum121.data;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaccum121 on 11.04.16.
 */
public class Order {

    @Id
    private String id;
    private Status status;
    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String phoneNumber;
    private Integer price;
    private List<Dish> dishes;
    private String address;

    public Order() {
    }

    public Order(String id, LocalDateTime orderTime, LocalDateTime deliveryTime, String phoneNumber, String address) {
        this.id = id;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.setStatus(Status.TO_COOK);
    }

    public Order(LocalDateTime orderTime, LocalDateTime deliveryTime) {
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.setStatus(Status.TO_COOK);
    }

    public Order(LocalDateTime orderTime, LocalDateTime deliveryTime, String phoneNumber) {
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.phoneNumber = phoneNumber;
        this.setStatus(Status.TO_COOK);
    }

    public Order(LocalDateTime orderTime, LocalDateTime deliveryTime, String phoneNumber,Integer price,List<Dish>dishes,String address) {
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.price=price;
        this.phoneNumber = phoneNumber;
        this.dishes = dishes;
        this.address = address;
        this.setStatus(Status.TO_COOK);
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, orderTime='%s', deliveryTime='%s']",
                id, orderTime, deliveryTime);
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Dish> getDishes() {
        if (dishes == null) {
            dishes = new ArrayList<>();
        }
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {TO_COOK, TO_DELIVERY, DELIVERY, DONE, CANCEL}
}
