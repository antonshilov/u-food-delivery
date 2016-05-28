package gq.vaccum121.data;

import org.springframework.data.annotation.Id;

/**
 * Created by vaccum121 on 11.04.16.
 */
public class Dish {

    @Id
    private String id;

    private String name;
    private Integer weight;
    private Integer price;

    public Dish(String id, String name, Integer weight, Integer price) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Dish(String name, Integer weight, Integer price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Dish() {
    }

    public Dish(String name, Integer weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%s, name='%s', weight='%s']",
                id, name, weight);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
