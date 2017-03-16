package com.nicolasbouvie.enverspoc;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;

import com.nicolasbouvie.enverspoc.model.Person;
import com.nicolasbouvie.enverspoc.util.HibernateUtil;

public class Main {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        createPeople(session);
        updatePersonSalary(session);
 
        AuditReader reader = AuditReaderFactory.get(session); //Can use entityManager as well.
        
        System.out.println(reader.find(Person.class, 1L, 1));
        System.out.println(reader.find(Person.class, 2L, 1));
        
        System.out.println(reader.find(Person.class, 1L, 2));
        System.out.println(reader.find(Person.class, 2L, 2));
        
        System.out.println(reader.getRevisions(Person.class, 1L));
        
        HibernateUtil.shutdown();
    }

	private static void updatePersonSalary(Session session) {
		session.beginTransaction();
		session.find(Person.class, 1L).setSalary(BigDecimal.valueOf(5555.55));
        session.getTransaction().commit();
	}

	private static void createPeople(Session session) {
		session.beginTransaction();
        Person p1 = new Person();
        p1.setName("AAA");
        session.save(p1);
        Person p2 = new Person();
        p2.setName("BBB");
        p2.setParent(p1);
        session.save(p2);
        session.getTransaction().commit();
	}
}
