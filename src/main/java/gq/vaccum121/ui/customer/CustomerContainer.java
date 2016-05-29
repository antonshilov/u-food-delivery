package gq.vaccum121.ui.customer;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Customer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class CustomerContainer extends BeanContainer<String, Customer> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {"firstName", "lastName", "phoneNumber","address"};
    public static final String[] HEADERS = {"First Name", "Last Name", "Phone Number", "Address"};
    private static final long serialVersionUID = 3090067422968423191L;

    public CustomerContainer() {
        super(Customer.class);
        setBeanIdProperty(BEAN_ID);
    }
}
