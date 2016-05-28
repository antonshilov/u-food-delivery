package gq.vaccum121.ui.AddDishes;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Dish;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by indertan on 28.05.2016.
 */
@Component
@Scope("prototype")
public class AddDishesContainer extends BeanContainer<String, Dish> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {BEAN_ID, "name", "weight", "price"};
    public static final String[] HEADERS = {"ID", "name", "weight", "price"};
    private static final long serialVersionUID = 3090067422978423191L;

    public AddDishesContainer() {
        super(Dish.class);
        setBeanIdProperty(BEAN_ID);
    }
}