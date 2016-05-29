package gq.vaccum121.ui.AddDishes;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Dish;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by dezmo on 29.05.2016.
 */
@Component
@Scope("prototype")
public class AddDishesContainer extends BeanContainer<String, Dish> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {"name", "weight", "price"};
    public static final String[] HEADERS = {"Name", "Weight", "Price"};
    private static final long serialVersionUID = 3090067422968423191L;

    public AddDishesContainer() {
        super(Dish.class);
        setBeanIdProperty(BEAN_ID);
    }
}

