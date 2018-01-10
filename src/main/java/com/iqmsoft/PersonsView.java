package com.iqmsoft;

import com.iqmsoft.model.Person;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = PersonsView.VIEW)
public class PersonsView extends VerticalLayout implements View {
    public static final String VIEW = "persons";

    @Autowired
    private ViewModelBinder binder;

    @PostConstruct
    void init() {
        Window editWin = new Window();
        VerticalLayout lay = new VerticalLayout();
        editWin.setContent(lay);
        TextField nameFld = new TextField();
        nameFld.setValue("First Name");
        nameFld.setWidth(300f, Unit.PIXELS);
        lay.addComponent(nameFld);
        TextField surnameFld = new TextField();
        surnameFld.setWidth(300f, Unit.PIXELS);
        surnameFld.setValue("Last Name");
        lay.addComponent(surnameFld);
        TextArea skillsArea = new TextArea();
        skillsArea.setHeight(50f, Unit.PIXELS);
        skillsArea.setValue("Skills");
        lay.addComponent(skillsArea);
        TextField teamNameFld = new TextField();
        teamNameFld.setWidth(300f, Unit.PIXELS);
        teamNameFld.setValue("Team Name");
        lay.addComponent(teamNameFld);

        Button saveBtn = new Button("Save");
        Button deleteBtn = new Button("Delete");
        lay.addComponent(saveBtn);
        lay.addComponent(deleteBtn);
        deleteBtn.setEnabled(false);
        editWin.center();

        Button addMember = new Button("New Person");
        addMember.setWidth(300f, Unit.PIXELS);
        addMember.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                nameFld.setValue("First Name");
                surnameFld.setValue("Last Name");
                skillsArea.setValue("Skills");
                teamNameFld.setValue("Team2");
                saveBtn.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        binder.createMember(nameFld.getValue(),
                                surnameFld.getValue(),
                                skillsArea.getValue(),
                                binder.getTeamByName(teamNameFld.getValue()));
                        editWin.close();
                        Notification.show("Created",
                                "new person created",
                                Notification.Type.HUMANIZED_MESSAGE);
                        Page.getCurrent().reload();
                    }
                });
                getUI().addWindow(editWin);
            }
        });
        addComponent(addMember);
        for (Person member : binder.listAllMembers()) {
            Button memBtn = new Button(member.getName() + " " + member.getSurname());
            memBtn.setWidth(300f, Unit.PIXELS);
            memBtn.setHeight(25f, Unit.PIXELS);
            memBtn.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                    nameFld.setValue(member.getName());
                    surnameFld.setValue(member.getSurname());
                    skillsArea.setValue(member.getSkills());
                    teamNameFld.setValue(member.getTeam().getName());

                    deleteBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.removeMember(member.getPersonId());
                            editWin.close();
                            Notification.show("Person removed",
                                    "person " + member.getName() + " " + member.getSurname() + " was removed",
                                    Notification.Type.HUMANIZED_MESSAGE);
                            Page.getCurrent().reload();
                        }
                    });
                    saveBtn.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent event) {
                            binder.updateMember(member.getPersonId(),
                                    nameFld.getValue(),
                                    surnameFld.getValue(),
                                    skillsArea.getValue(),
                                    binder.getTeamByName(teamNameFld.getValue()));
                            editWin.close();
                            Notification.show("Changes saved",
                                    "your changes to person were saved",
                                    Notification.Type.HUMANIZED_MESSAGE);
                        Page.getCurrent().reload();
                        }
                    });
                    deleteBtn.setEnabled(true);
                    getUI().addWindow(editWin);

                }
            });
            addComponent(memBtn);
        }
    }

    @Override
    public void enter(ViewChangeEvent event) {}
}