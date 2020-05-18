package com.haulmont.test_task1.view;

import com.haulmont.test_task1.interfaces.MechanicStatProvider;
import com.haulmont.test_task1.model.enums.Status;
import com.vaadin.ui.*;

public class StatisticView extends Window {
    protected VerticalLayout subContent = new VerticalLayout();
    protected Button ok = new Button("OK");

    protected String stat1Text = "Выполнено: ";
    protected String stat2Text = "Запланировано: ";
    protected String stat3Text = "Клиентов принято: ";
    protected Label stat1 = new Label(stat1Text);
    protected Label stat2 = new Label(stat2Text);
    protected Label stat3 = new Label(stat3Text);
    private MechanicStatProvider mechanicStatProvider;

    private void initBaseUI() {
        setModal(true);
        setResizable(false);
        setClosable(false);

        Layout h = new HorizontalLayout();

        h.addComponents(ok);

        subContent.addComponents(stat1, stat2, stat3, h);
        subContent.setComponentAlignment(stat1, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(stat2, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(stat3, Alignment.TOP_CENTER);
        subContent.setComponentAlignment(h, Alignment.TOP_CENTER);

        stat1.setValue(stat1Text + mechanicStatProvider.getOrdersCountByStatus(Status.COMPLETED));
        stat2.setValue(stat2Text + mechanicStatProvider.getOrdersCountByStatus(Status.SCHEDULED));
        stat3.setValue(stat3Text + mechanicStatProvider.getOrdersCountByStatus(Status.ACCEPTED_BY_CLIENT));

        ok.addClickListener(clickEvent -> close());

        setContent(subContent);
    }

    StatisticView(MechanicStatProvider mechanicStatProvider) {
        super("Статистика");
        this.mechanicStatProvider = mechanicStatProvider;
        initBaseUI();
    }
}
