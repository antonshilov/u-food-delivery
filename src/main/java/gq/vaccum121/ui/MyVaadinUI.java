package gq.vaccum121.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import gq.vaccum121.ui.AddDishes.AddDishesView;
import gq.vaccum121.ui.Release.ReleaseView;
import gq.vaccum121.ui.courier.CourierUIView;
import gq.vaccum121.ui.customer.CustomerUIView;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;
import gq.vaccum121.ui.kitchen.KitchenUIView;
import gq.vaccum121.ui.order.OrderUIView;
import org.springframework.beans.factory.annotation.Autowired;


@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    @Autowired
    EventSystem eventSystem;
    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        initLayout();
        registerEvents();
    }

    private void registerEvents() {
        eventSystem.registerEvent(ReloadEntriesEvent.ReloadEntriesListener.class, ReloadEntriesEvent.class);
    }

    private void initLayout() {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);

        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        navigationBar.addComponent(createNavigationButton("Order View",
                OrderUIView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Customer View",
                CustomerUIView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Kitchen View",
                KitchenUIView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Add dishes View",
                AddDishesView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Release View",
                ReleaseView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton("Courier View",
                CourierUIView.VIEW_NAME));

        root.addComponent(navigationBar);

        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }
}