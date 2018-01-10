package com.iqmsoft;
import com.iqmsoft.model.DBManager;
import com.iqmsoft.model.Person;
import com.iqmsoft.model.Groups;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class ViewModelBinder {

    public void createTeam(String name, String description) {
        DBManager.createTeam(name, description);
    }

    public void createMember(String name, String surname, String skills, Groups team) {
        DBManager.createMember(name, surname, skills, team);
    }

    public List<Groups> listAllTeams() {
        return DBManager.listAllTeams();
    }

    public List<String> getTeamNames() {
        List<String> names = new ArrayList<>();
        for (Groups team : listAllTeams()) {
            names.add(team.getName());
        }
        return names;
    }

    public List<Person> listAllMembers() {
        return DBManager.listAllMembers();
    }

    public void updateMember(int memberId, String name, String surname,
                                    String skills, Groups team) {
        DBManager.updateMember(memberId, name, surname, skills, team);
    }

    public void updateTeam(int teamId, String name, String description) {
        DBManager.updateTeam(teamId, name, description);
    }

    public Groups getTeamByName(String name) {
        return DBManager.getTeamByName(name);
    }

    public List<Person> getMembersByTeam(String name) {
        return DBManager.getMembersByTeam(name);
    }

    public void removeTeam(int teamId, String name) {
        DBManager.removeTeam(teamId, name);
    }

    public void removeMember(int memberId) {
        DBManager.removeMember(memberId);
    }

}