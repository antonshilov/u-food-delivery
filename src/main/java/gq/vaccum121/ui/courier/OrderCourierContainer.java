package gq.vaccum121.ui.courier;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by dezmo on 29.05.2016.
 */
@Component
@Scope("prototype")
public class OrderCourierContainer extends BeanContainer<String, Order> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {"deliveryTime","phoneNumber","price","address"};
    public static final String[] HEADERS = {"Delivery Time","Phone Number","Price","Address"};
    private static final long serialVersionUID = 3090067422968423191L;

    public OrderCourierContainer() {
        super(Order.class);
        setBeanIdProperty(BEAN_ID);
    }
}
