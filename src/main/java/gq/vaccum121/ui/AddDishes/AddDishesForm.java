package gq.vaccum121.ui.AddDishes;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import gq.vaccum121.data.*;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;
import gq.vaccum121.ui.kitchen.DishContainer;
import gq.vaccum121.ui.kitchen.OrderContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by indertan on 28.05.2016.
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class AddDishesForm extends FormLayout {

    private Logger log = LoggerFactory.getLogger(AddDishesForm.class);

    @Autowired
    private DishRepository dishService;

    @Autowired
    private EventSystem eventSystem;

    private String id = null;
    private TextField name = new TextField("Dish name");
    private TextField weight = new TextField("Dish weight:");
    private TextField price = new TextField("Dish price:");

    public AddDishesForm() {
        initForm();
    }

    public void setData(Dish dish) {
        id = dish.getId();
        name.setValue(dish.getName());
        weight.setValue(String.valueOf(dish.getWeight()));
        price.setValue(String.valueOf(dish.getPrice()));
    }

    private void initForm() {
        addComponent(name);
        addComponent(weight);
        addComponent(price);

        final Button commit = new Button("Commit");
        final Button cancel = new Button("Cancel");

        cancel.addClickListener((Button.ClickListener) event -> clearAndHide());
        commit.addClickListener((Button.ClickListener) event -> {
            commitForm();
            fireCommitEvent();
            clearAndHide();
        });

        final HorizontalLayout buttonBar = new HorizontalLayout();

        buttonBar.addComponent(commit);
        buttonBar.addComponent(cancel);

        addComponent(buttonBar);
    }

    private void commitForm() {
        int dishPrice, dishWeight;
        String dishName = (name.getValue() == null || name.getValue() == "") ? "WithoutName" : name.getValue();

        try{
            dishWeight = Integer.parseInt(weight.getValue());
        } catch (NumberFormatException | NullPointerException e) {
            dishWeight = 1;
        }

        try{
            dishPrice = Integer.parseInt(price.getValue());
        } catch (NumberFormatException | NullPointerException e) {
            dishPrice = 1;
        }

        if (id != null) {
            log.info("Update dish with id {}, name {}", id, name.getValue());
            Dish dish = dishService.findOne(id);
            dish.setName(dishName);
            dish.setWeight(dishWeight);
            dish.setPrice(dishPrice);

            dishService.save(dish);
        } else {
            log.info("Creating dish with name {}", name.getValue());
            dishService.save(new Dish(dishName, dishWeight, dishPrice));
        }
    }

    private void clearAndHide() {
        name.setValue("");
        weight.setValue("");
        price.setValue("");
        id = null;
        setVisible(false);
    }

    private void fireCommitEvent() {
        log.info("Fire commit event!");
        eventSystem.fire(new ReloadEntriesEvent());
    }
}
