package gq.vaccum121.ui.kitchen;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
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
 * Created by vaccum121 on 28.05.16.
 */
@SpringView(name = KitchenUIView.VIEW_NAME)
@UIScope
public class KitchenUIView extends HorizontalLayout implements View, ReloadEntriesEvent.ReloadEntriesListener {
    public static final String VIEW_NAME = "kitchen";
    private static final Log LOG = LogFactory.getLog(KitchenUIView.class);

    private Table entityTable;
    private String selectedId;
    private Order selectedOrder;

    @Autowired
    private OrderContainer orderContainer;

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
        // vaadin table
        entityTable = new Table();
        entityTable.setContainerDataSource(orderContainer);
        entityTable.setVisibleColumns(OrderContainer.PROPERTIES);
        entityTable.setColumnHeaders(OrderContainer.HEADERS);
        entityTable.setSelectable(true);
        entityTable.setWidth("100%");
        entityTable.setHeight("100%");

        // table select listener
        entityTable.addItemClickListener((ItemClickEvent.ItemClickListener) event -> {
            selectedId = (String) event.getItemId();
            selectedOrder = orderContainer.getItem(selectedId).getBean();

            LOG.info("Selected item id {" + selectedId + "}");
        });
        // button bar
//        final AbstractLayout buttonBar = initButtonBar();
//        buttonBar.setWidth("100%");

        addComponent(entityTable);
//        addComponent(buttonBar);
//        addComponent(editForm);
    }

    private void registerEvents() {
        eventSystem.addListener(this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void initData() {
        // load data
        List<Order> all = service.findByStatus(Order.Status.TO_COOK);
        LOG.info(all);
        // clear table
        orderContainer.removeAllItems();
        // set table data
        orderContainer.addAll(all);
    }

    @Override
    public void reloadEntries(ReloadEntriesEvent event) {

    }
}
