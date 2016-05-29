package gq.vaccum121.ui.order;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import gq.vaccum121.data.*;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;
import gq.vaccum121.ui.kitchen.DishContainer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@SpringView(name = OrderUIView.VIEW_NAME)
@UIScope
public class OrderUIView extends HorizontalLayout implements View, ReloadEntriesEvent.ReloadEntriesListener {
    /*
     * This view is registered automatically based on the @SpringView annotation.
     * As it has an empty string as its view name, it will be shown when navigating to the Homepage
     */
    public static final String VIEW_NAME = "";
    private static final Log LOG = LogFactory.getLog(OrderUIView.class);


    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DishContainer dishesContainer;

    @Autowired
    private FullOrderContainer orderContainer;

    @Autowired
    private EventSystem eventSystem;

    private PopupDateField orderTimeDateField;
    private PopupDateField deliveryTimeDateField;
    private NativeSelect customerNativeSelect;
    private Button createOrderButton;
    private TwinColSelect dishTwinColSelect;
    private Label totalPriceLabel;
    private VerticalLayout verticalLayout;
    private Table ordersTable;
    private Button cancelOrderButton;

    //Full list of dishes
    private List<Dish> dishList;

    //String that consist indexes of final order list
    private String finalSelectedDishes;
    //Full list of customers
    private List<Customer> customerList;
    //Index of selected customer
    private int selectedCustomerIndex;

    private String selectedId;
    private Order selectedOrder;

    @PostConstruct
    void init() {
        registerEvents();
        initLayout();
        initData();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // the view is constructed in the init() method()
    }

    private void registerEvents() {
        eventSystem.addListener(this);
    }

    private void initData() {
        //init PopupDateFields
        orderTimeDateField.setValue(new Date());
        deliveryTimeDateField.setValue(new Date());
        orderTimeDateField.setResolution(Resolution.MINUTE);
        deliveryTimeDateField.setResolution(Resolution.MINUTE);
        //fill twinCol by dishList
        dishList = dishRepository.findAll();
        for (int i = 0; i < dishList.size(); i++) {
            dishTwinColSelect.addItem(i);
            dishTwinColSelect.setItemCaption(i, dishList.get(i).getName() + " " + dishList.get(i).getPrice() + " RUB");
        }
        dishTwinColSelect.setImmediate(true);
        dishTwinColSelect.setRows(dishList.size());
        dishTwinColSelect.setLeftColumnCaption("Available dishes");
        dishTwinColSelect.setRightColumnCaption("In order");
        //fill nativeSelect by customerList
        customerList = customerRepository.findAll();
        for (int i = 0; i < customerList.size(); i++) {
            customerNativeSelect.addItem(i);
            customerNativeSelect.setItemCaption(i, customerList.get(i).getFirstName() + " " + customerList.get(i).getLastName());
        }
        customerNativeSelect.setImmediate(true);

        // load data
        List<Order> all = orderRepository.findAll();
        LOG.info(all);
        // clear table
        orderContainer.removeAllItems();
        // set table data
        orderContainer.addAll(all);
    }

    @SuppressWarnings("serial")
    private void initLayout() {
        setMargin(true);
        setSpacing(true);

        orderTimeDateField = new PopupDateField("Order time:");
        deliveryTimeDateField = new PopupDateField("Delivery time:");

        customerNativeSelect = new NativeSelect("Select customer:");
        customerNativeSelect.addValueChangeListener(e -> selectedCustomerIndex = Integer.parseInt(String.valueOf(e.getProperty().getValue())));

        dishTwinColSelect = new TwinColSelect("Choose dishes:");
        dishTwinColSelect.addValueChangeListener(e -> countTotalPrice(String.valueOf(e.getProperty().getValue())));

        totalPriceLabel = new Label("Total price is <font color = '#197de1'> 0 </font>RUB");
        totalPriceLabel.setContentMode(ContentMode.HTML);

        createOrderButton = new Button("Create Order", clickEvent -> createOrder());

        ordersTable = new Table("Orders");
        ordersTable.setContainerDataSource(orderContainer);
        ordersTable.setVisibleColumns(FullOrderContainer.PROPERTIES);
        ordersTable.setColumnHeaders(FullOrderContainer.HEADERS);
        ordersTable.setSelectable(true);
        ordersTable.setWidth("100%");
        ordersTable.setHeight("100%");

        // table select listener
        ordersTable.addItemClickListener((ItemClickEvent.ItemClickListener) event -> {
            selectedId = (String) event.getItemId();
            selectedOrder = orderContainer.getItem(selectedId).getBean();
            LOG.info("Selected item id {" + selectedId + "}");
        });

        cancelOrderButton = new Button("Cancel",clickEvent -> cancelOrder());

        verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(orderTimeDateField);
        verticalLayout.addComponent(deliveryTimeDateField);
        verticalLayout.addComponent(dishTwinColSelect);
        verticalLayout.addComponent(totalPriceLabel);
        verticalLayout.addComponent(customerNativeSelect);
        verticalLayout.addComponent(createOrderButton);

        addComponent(verticalLayout);
        addComponent(ordersTable);
        addComponent(cancelOrderButton);
    }

    //create the order
    private void createOrder() {
        //create dish list for order
        List<Dish>orderDishList = new ArrayList<Dish>();
        List<Integer> dishIndexesList = parseDishIndexesList(finalSelectedDishes);
        for(int i=0;i<dishIndexesList.size();i++){
            orderDishList.add(dishList.get(dishIndexesList.get(i)));
        }
        //add new order
        orderRepository.save(new Order(
                parseLocalDateTime(orderTimeDateField.getValue()),
                parseLocalDateTime(deliveryTimeDateField.getValue()),
                customerList.get(selectedCustomerIndex).getPhoneNumber(),
                countTotalPrice(dishIndexesList),
                orderDishList,
                customerList.get(selectedCustomerIndex).getAddress()));

        Notification.show("Order has been successfully created!", Notification.Type.TRAY_NOTIFICATION);
        reloadEntries(new ReloadEntriesEvent());
    }


    //parse Date to LocalDateTime
    private LocalDateTime parseLocalDateTime(Date date){
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    //parse List of dishes from String
    private List<Integer> parseDishIndexesList(String selectedDishes){
        List<Integer> dishIndexesList = new ArrayList<Integer>();
        selectedDishes = selectedDishes.substring(1);
        while (selectedDishes.contains(",")) {
            dishIndexesList.add(Integer.parseInt(selectedDishes.substring(0, selectedDishes.indexOf(","))));
            selectedDishes = selectedDishes.substring(selectedDishes.indexOf(','));
            selectedDishes = selectedDishes.substring(2);
        }
        dishIndexesList.add(Integer.parseInt(selectedDishes.substring(0, selectedDishes.indexOf("]"))));
        return dishIndexesList;
    }

    //create list of dishes indexes and count total cost of the order
    private void countTotalPrice(String selectedDishes){
        //list of dishes indexes in order
        List<Integer> dishIndexesList = parseDishIndexesList(selectedDishes);
        totalPriceLabel.setValue("Total price is <font color = '#197de1'>" + countTotalPrice(dishIndexesList) + " </font>RUB");
        finalSelectedDishes = selectedDishes;
    }

    //count total cost of the order
    private int countTotalPrice(List<Integer>dishIndexes) {
        int totalPrice = 0;

        for (int i = 0; i < dishIndexes.size(); i++) {
            totalPrice += dishList.get(dishIndexes.get(i)).getPrice();
        }
        return totalPrice;
    }

    private void cancelOrder(){
        selectedOrder.setStatus(Order.Status.CANCEL);
        orderRepository.save(selectedOrder);
        eventSystem.fire(new ReloadEntriesEvent());
    }

    @Override
    public void reloadEntries(ReloadEntriesEvent event) {
        initData();
    }

}