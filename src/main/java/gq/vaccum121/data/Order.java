package gq.vaccum121.data;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vaccum121 on 11.04.16.
 */
public class Order {

    @Id
    private String id;

    private LocalDateTime orderTime;
    private LocalDateTime deliveryTime;
    private String phoneNumber;
    private Integer price;
    private List<Dish> dishes;
    private Address address;

    public Order() {
    }

    public Order(String id, LocalDateTime orderTime, LocalDateTime deliveryTime, String phoneNumber, Address address) {
        this.id = id;
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public Order(LocalDateTime orderTime, LocalDateTime deliveryTime) {
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
    }

    public Order(LocalDateTime orderTime, LocalDateTime deliveryTime, String phoneNumber) {
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
        this.phoneNumber = phoneNumber;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
