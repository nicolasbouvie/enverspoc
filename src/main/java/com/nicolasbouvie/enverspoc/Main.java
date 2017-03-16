package com.nicolasbouvie.enverspoc;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

import com.nicolasbouvie.enverspoc.audit.UserRevEntity;
import com.nicolasbouvie.enverspoc.model.Person;
import com.nicolasbouvie.enverspoc.util.HibernateUtil;

public class Main {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();

        createPeople(session);
        updatePersonSalary(session);

        AuditReader reader = AuditReaderFactory.get(session); //Can use entityManager as well.

        for (Number rev : reader.getRevisions(Person.class, 1L)) {
        	System.out.println(reader.find(Person.class, 1L, rev));
        }
        for (Number rev : reader.getRevisions(Person.class, 2L)) {
        	System.out.println(reader.find(Person.class, 2L, rev));
        }

        {
	        //Retrieve all revisions from Person 1
	        AuditQuery query = reader.createQuery()
	                .forRevisionsOfEntity(Person.class, false, false)
	                .add(AuditEntity.property("id").eq(1L));
	
	        List<Object[]> revs = (List<Object[]>) query.getResultList();
	        for (Object[] rev : revs) {
		        Person person = (Person) rev[0];
		        UserRevEntity userRevEntity = (UserRevEntity) rev[1];
		        RevisionType type = (RevisionType) rev[2];
		        System.out.println(person);
		        System.out.println(userRevEntity);
		        System.out.println(type);
	        }
        }

        {
	        //Retrieve all revisions from Person with parent 1
	        AuditQuery query = reader.createQuery()
	                .forRevisionsOfEntity(Person.class, false, false)
	                .add(AuditEntity.relatedId("parent").eq(1L));
	
	        List<Object[]> revs = (List<Object[]>) query.getResultList();
	        for (Object[] rev : revs) {
		        Person person = (Person) rev[0];
		        UserRevEntity userRevEntity = (UserRevEntity) rev[1];
		        RevisionType type = (RevisionType) rev[2];
		        System.out.println(person);
		        System.out.println(userRevEntity);
		        System.out.println(type);
	        }
        }

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
