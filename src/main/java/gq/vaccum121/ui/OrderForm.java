package gq.vaccum121.ui;

import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import gq.vaccum121.data.OrderRepository;
import gq.vaccum121.ui.event.EventSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by dezmo on 28.05.2016.
 */
public class OrderForm extends FormLayout {
    private Logger log = LoggerFactory.getLogger(OrderForm.class);

    @Autowired
    private OrderRepository orderService;

    @Autowired
    private EventSystem eventSystem;

    private String id = null;
    private Label orderTime = new Label("Order time: ");
    private Label deliveryTime = new Label("Delivery time: ");
    private InlineDateField orderDate = new InlineDateField();
    private InlineDateField deliveryDate = new InlineDateField();

    public OrderForm() {
        initForm();
    }

    private void initForm() {
        orderDate.setValue(new Date());
        orderDate.setImmediate(true);
        orderDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        orderDate.setLocale(Locale.US);
        orderDate.setResolution(Resolution.MINUTE);
        deliveryDate.setValue(new Date());
        deliveryDate.setImmediate(true);
        deliveryDate.setTimeZone(TimeZone.getTimeZone("UTC"));
        deliveryDate.setLocale(Locale.US);
        deliveryDate.setResolution(Resolution.MINUTE);
        addComponent(orderTime);
        addComponent(orderDate);
        addComponent(deliveryTime);
        addComponent(deliveryDate);
        

    }
}
