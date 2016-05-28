package gq.vaccum121.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import gq.vaccum121.data.Customer;
import gq.vaccum121.data.CustomerRepository;
import gq.vaccum121.ui.event.EventSystem;
import gq.vaccum121.ui.event.ReloadEntriesEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class CustomerForm extends FormLayout {

    private Logger log = LoggerFactory.getLogger(CustomerForm.class);

    @Autowired
    private CustomerRepository customerService;

    @Autowired
    private EventSystem eventSystem;

    private String id = null;
    private TextField firstName = new TextField("First Name:");
    private TextField lastName = new TextField("Last Name:");
    private TextField phoneNumber = new TextField("Phone Number:");
    private TextField address = new TextField("Address:");

    public CustomerForm() {
        initForm();
    }

    public void setData(Customer customer) {
        id = customer.getId();
        firstName.setValue(customer.getFirstName());
        lastName.setValue(customer.getLastName());
        phoneNumber.setValue(customer.getPhoneNumber());
        address.setValue(customer.getAddress());
    }

    private void initForm() {
        addComponent(firstName);
        addComponent(lastName);
        addComponent(phoneNumber);
        addComponent(address);

        final Button commit = new Button("Commit");
        final Button cancel = new Button("Cancel");

        cancel.addClickListener((Button.ClickListener) event -> clearAndHide());
        commit.addClickListener((Button.ClickListener) event -> {
            commitForm();
            fireCommitEvent();
            clearAndHide();
        });

        final HorizontalLayout buttonBar = new HorizontalLayout();

        buttonBar.addComponent(commit);
        buttonBar.addComponent(cancel);

        addComponent(buttonBar);
    }

    private void commitForm() {
        if (id != null) {
            log.info("Update user with id {}, name {} and address {}", id, firstName.getValue(), lastName.getValue());
            Customer customer = customerService.findOne(id);
            customer.setFirstName(firstName.getValue());
            customer.setLastName(lastName.getValue());
            customer.setPhoneNumber(phoneNumber.getValue());
            customer.setAddress(address.getValue());
            customerService.save(customer);
        } else {
            log.info("Creating user with name {} and address {}", firstName.getValue(), lastName.getValue());
            customerService.save(new Customer(firstName.getValue(), lastName.getValue(), phoneNumber.getValue()));
        }
    }

    private void clearAndHide() {
        firstName.setValue("");
        lastName.setValue("");
        phoneNumber.setValue("");
        address.setValue("");
        id = null;
        setVisible(false);
    }

    private void fireCommitEvent() {
        log.info("Fire commit event!");
        eventSystem.fire(new ReloadEntriesEvent());
    }
} 