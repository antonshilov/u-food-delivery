package gq.vaccum121.ui.kitchen;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class OrderContainer extends BeanContainer<String, Order> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {BEAN_ID, "deliveryTime", "price"};
    public static final String[] HEADERS = {"ID", "Delivery Time", "Price"};
    private static final long serialVersionUID = 3090067422968423191L;

    public OrderContainer() {
        super(Order.class);
        setBeanIdProperty(BEAN_ID);
    }
}
