package gq.vaccum121.ui;

import com.vaadin.data.util.BeanContainer;
import gq.vaccum121.data.Customer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class MongoDBContainer extends BeanContainer<String, Customer> {

    public static final String BEAN_ID = "id";
    public static final Object[] PROPERTIES = {BEAN_ID, "firstName", "lastName", "phoneNumber"};
    public static final String[] HEADERS = {"ID", "First Name", "Last Name", "Phone Number"};
    private static final long serialVersionUID = 3090067422968423191L;

    public MongoDBContainer() {
        super(Customer.class);
        setBeanIdProperty(BEAN_ID);
    }
}
