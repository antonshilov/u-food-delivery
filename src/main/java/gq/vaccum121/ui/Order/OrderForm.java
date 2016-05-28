package gq.vaccum121.ui.Order;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import gq.vaccum121.data.Dish;
import gq.vaccum121.data.DishRepository;
import gq.vaccum121.data.OrderRepository;
import gq.vaccum121.ui.MongoDBContainer;
import gq.vaccum121.ui.event.EventSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dezmo on 28.05.2016.
 */
@Component
@Scope("prototype")
public class OrderForm extends FormLayout {
    private Logger log = LoggerFactory.getLogger(OrderForm.class);

    @Autowired
    private OrderRepository orderService;

    @Autowired
    private EventSystem eventSystem;

    @Autowired
    private DishRepository dishRepository ;

    private String id = null;
    private Label orderTime = new Label("Order time: ");
    private Label deliveryTime = new Label("Delivery time: ");
    private Label phoneNumber = new Label("Phone number: ");
    private Label price = new Label("Totally price: ");
    private Label dishes = new Label("Dishes: ");
    private ComboBox dishList = new ComboBox();
    private InlineDateField orderDate = new InlineDateField();
    private InlineDateField deliveryDate = new InlineDateField();
    private Button dishAdd = new Button("Add dish:");
    private int totalCost = 0;
    private List<Dish> selectedDishes;

    public OrderForm() {
        initForm();
    }

    private void initForm() {
        //дата заказ
        orderDate.setValue(new Date());
        orderDate.setImmediate(true);
        orderDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        orderDate.setLocale(Locale.US);
        orderDate.setResolution(Resolution.MINUTE);
        //дата доставки
        deliveryDate.setValue(new Date());
        deliveryDate.setImmediate(true);
        deliveryDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        deliveryDate.setLocale(Locale.US);
        deliveryDate.setResolution(Resolution.MINUTE);

        List <Dish> dishAll = dishRepository.findAll();

        for(int i=0;i<dishAll.size();i++){
            dishList.addItem(dishAll.get(i).getName());
        }

        addComponent(orderTime);
        addComponent(orderDate);
        addComponent(deliveryTime);
        addComponent(deliveryDate);
        addComponent(dishes);
        addComponent(dishList);
        addComponent(dishAdd);


        dishAdd.addClickListener((Button.ClickListener) event -> {
            Dish dish = dishAll.get((int)dishList.getValue());
            totalCost+=dish.getPrice();

            //add to table

        });

    }
}
