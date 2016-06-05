package gq.vaccum121.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
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
import org.vaadin.spring.security.VaadinSecurity;


/**
 * Created by vaccum121 on 05.06.16.
 */
@UIScope
@SpringComponent
public class MainScreen extends CustomComponent {
    EventSystem eventSystem;
    private SpringViewProvider viewProvider;

    @Autowired
    public MainScreen(final VaadinSecurity vaadinSecurity, SpringViewProvider viewProvider, EventSystem eventSystem) {
        this.viewProvider = viewProvider;
        this.eventSystem = eventSystem;
        initLayout();
        registerEvents();
    }

    private void registerEvents() {
        eventSystem.registerEvent(ReloadEntriesEvent.ReloadEntriesListener.class, ReloadEntriesEvent.class);
    }

    private void initLayout() {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setCompositionRoot(root);
        setSizeFull();

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

        Navigator navigator = new Navigator(UI.getCurrent(), viewContainer);
        navigator.addProvider(viewProvider);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }
}
