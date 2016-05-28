package gq.vaccum121.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.VerticalLayout;
import gq.vaccum121.ui.Order.OrderForm;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;

import javax.annotation.PostConstruct;

@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View,ReloadEntriesEvent.ReloadEntriesListener {
    /*
     * This view is registered automatically based on the @SpringView annotation.
     * As it has an empty string as its view name, it will be shown when navigating to the Homepage
     */
    public static final String VIEW_NAME = "";


    private OrderForm orderForm = new OrderForm();

    //@Autowired
    private EventSystem eventSystem = new EventSystem();


    @PostConstruct
    void init() {
        registerEvents();
        initData();
        initLayout();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }

    private void registerEvents() {
        eventSystem.addListener(this);
    }


    @SuppressWarnings("serial")
    private void initLayout() {
        setMargin(true);
        setSpacing(true);
        addComponent(orderForm);
    }

    private void initData() {

    }

    @Override
    public void reloadEntries(ReloadEntriesEvent event) {

    }
}