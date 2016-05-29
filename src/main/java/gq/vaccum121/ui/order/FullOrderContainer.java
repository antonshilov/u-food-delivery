package gq.vaccum121.ui.order;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Order;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by dezmo on 29.05.2016.
 */
@Component
@Scope("prototype")
public class FullOrderContainer extends BeanContainer<String, Order> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {BEAN_ID, "orderTime","deliveryTime","phoneNumber","price","address","status"};
    public static final String[] HEADERS = {"ID","Order Time","Delivery Time","Phone Number","Price","Address","status"};
    private static final long serialVersionUID = 3090067422968423191L;

    public FullOrderContainer() {
        super(Order.class);
        setBeanIdProperty(BEAN_ID);
    }
}
