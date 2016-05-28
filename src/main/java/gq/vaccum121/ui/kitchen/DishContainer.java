package gq.vaccum121.ui.kitchen;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Dish;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class DishContainer extends BeanContainer<String, Dish> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {"name", "price"};
    public static final String[] HEADERS = {"Name", "Price"};
    private static final long serialVersionUID = 3090067422968423191L;

    public DishContainer() {
        super(Dish.class);
        setBeanIdProperty(BEAN_ID);
    }
}
