package gq.vaccum121.ui.courier;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import gq.vaccum121.data.Order;
import gq.vaccum121.data.OrderRepository;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by dezmo on 29.05.2016.
 */
@SpringView(name = CourierUIView.VIEW_NAME)
@UIScope
public class CourierUIView extends VerticalLayout implements View, ReloadEntriesEvent.ReloadEntriesListener {
    public static final String VIEW_NAME = "courier";
    private static final Log LOG = LogFactory.getLog(CourierUIView.class);

    private Table ordersTable;
    private Button deliveredBtn;
    private String selectedId;
    private Order selectedOrder;

    @Autowired
    private OrderCourierContainer orderContainer;

    @Autowired
    private OrderRepository service;

    @Autowired
    private EventSystem eventSystem;

    @PostConstruct
    void init() {
        registerEvents();
        initData();
        initLayout();
    }

    @SuppressWarnings("serial")
    private void initLayout() {
        setMargin(true);
        setSpacing(true);
        // orders table
        ordersTable = new Table("Orders");
        ordersTable.setContainerDataSource(orderContainer);
        ordersTable.setVisibleColumns(OrderCourierContainer.PROPERTIES);
        ordersTable.setColumnHeaders(OrderCourierContainer.HEADERS);
        ordersTable.setSelectable(true);
        ordersTable.setWidth("100%");
        ordersTable.setHeight("100%");

        // table select listener
        ordersTable.addItemClickListener((ItemClickEvent.ItemClickListener) event -> {
            selectedId = (String) event.getItemId();
            selectedOrder = orderContainer.getItem(selectedId).getBean();
            LOG.info("Selected item id {" + selectedId + "}");
        });

        deliveredBtn = new Button("Delivered", clickEvent -> setDelivered());
        addComponent(ordersTable);
        addComponent(deliveredBtn);
    }


    private void registerEvents() {
        eventSystem.addListener(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void initData() {
        // load data
        List<Order> all = service.findByStatus(Order.Status.DELIVERY);
        LOG.info(all);
        // clear table
        orderContainer.removeAllItems();
        // set table data
        orderContainer.addAll(all);
    }

    private void setDelivered() {
        selectedOrder.setStatus(Order.Status.DONE);
        service.save(selectedOrder);
        eventSystem.fire(new ReloadEntriesEvent());
    }

    @Override
    public void reloadEntries(ReloadEntriesEvent event) {
        initData();
    }
}