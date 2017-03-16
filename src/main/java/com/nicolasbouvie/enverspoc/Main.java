package com.nicolasbouvie.enverspoc;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.nicolasbouvie.enverspoc.model.Person;
import com.nicolasbouvie.enverspoc.util.HibernateUtil;

public class Main {
	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		  
        session.beginTransaction();
 
        Person p1 = new Person();
        p1.setName("AAA");
        session.save(p1);
 
        Person p2 = new Person();
        p2.setName("BBB");
        p2.setParent(p1);
        session.save(p2);
        
        session.getTransaction().commit();
 
        Query<Person> q = session.createQuery("from Person", Person.class);
        List<Person> resultList = q.list();
        System.out.println("num of people:" + resultList.size());
        for (Person p : resultList) {
            System.out.println("next person: " + p);
        }
    }
}
