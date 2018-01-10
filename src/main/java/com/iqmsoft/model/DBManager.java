package com.iqmsoft.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class DBManager {
    private static SessionFactory sf;
    private static Session session;
    private static Transaction tx;
    private static void init() {
        sf = new Configuration().configure()
                .addAnnotatedClass(Groups.class)
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
        session = sf.openSession();
    }

    public static void createTeam(String name, String description) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Groups team = new Groups();
        team.setName(name);
        team.setDescription(description);
        session.save(team);
        tx.commit();
    }

    public static void createMember(String name, String surname, String skills, Groups team) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Person member = new Person();
        member.setName(name);
        member.setSurname(surname);
        member.setSkills(skills);
        member.setTeam(session.load(Groups.class, team.getTeamId()));
        session.save(member);
        tx.commit();
    }

    @SuppressWarnings("unchecked")
    public static List<Groups> listAllTeams() {
        if (session == null || !session.isOpen()) {
            init();
        }
        List<Groups> teams = session.createCriteria(Groups.class).list();
        return teams;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> listAllMembers() {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.createCriteria(Person.class).list();
    }

    public static void updateMember(int memberId, String name, String surname,
                                    String skills, Groups team) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Person member = session.get(Person.class, memberId);
        member.setName(name);
        member.setSurname(surname);
        member.setSkills(skills);
        member.setTeam(session.load(Groups.class, team.getTeamId()));
        session.save(member);
        tx.commit();
    }

    public static void updateTeam(int teamId, String name, String description) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Groups team = session.get(Groups.class, teamId);
        team.setName(name);
        team.setDescription(description);
        session.save(team);
        tx.commit();
    }

    public static Groups getTeamByName(String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.get(Groups.class, (Integer)session
                .createSQLQuery("select group_id from groups where name = :teamname")
                .setParameter("teamname", name).list().get(0));
    }
    @SuppressWarnings("unchecked")
    public static List<Person> getMembersByTeam(String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        return session.createSQLQuery("select * from person where person.group_id " +
                "in (select group_id from groups where groups.name = :teamname)")
                .setParameter("teamname", name).list();
    }
    public static void removeTeam(int teamId, String name) {
        if (session == null || !session.isOpen()) {
            init();
        }
        for (Person person : getMembersByTeam(name)) {
            updateMember(person.getPersonId(), person.getName(), person.getSurname(),
                    person.getSkills(), null);
        }
        tx = session.beginTransaction();
        Groups team = session.get(Groups.class, teamId);
        session.delete(team);
        tx.commit();
    }

    public static void removeMember(int memberId) {
        if (session == null || !session.isOpen()) {
            init();
        }
        tx = session.beginTransaction();
        Person member = session.get(Person.class, memberId);
        session.delete(member);
        tx.commit();
    }

    public static void finish() {
        session.close();
        sf.close();
    }
}
