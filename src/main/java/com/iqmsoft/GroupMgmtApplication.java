package com.iqmsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iqmsoft.model.DBManager;

@SpringBootApplication
public class GroupMgmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupMgmtApplication.class, args);
        //some test data:
        DBManager.createTeam("Group1", "Group 1");
        DBManager.createTeam("Group2", "Group 2");
        DBManager.createMember("Test1", "Test2", "Test2", DBManager.getTeamByName("Group1"));
        DBManager.createMember("Test2", "Test2", "Test2", DBManager.getTeamByName("Group2"));
        DBManager.createMember("Test3", "Test3", "Test3", DBManager.getTeamByName("Group2"));
//        DBManager.finish();
    }
}
