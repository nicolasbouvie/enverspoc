package com.nicolasbouvie.enverspoc.model;

import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

@Entity
@Table
@Audited
public class Person {
	private Long id;
	private String name;
	private BigDecimal salary;
	private Collection<Person> children;
	private Person parent;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
	public Collection<Person> getChildren() {
		return children;
	}

	public void setChildren(Collection<Person> children) {
		this.children = children;
	}

	@ManyToOne
	public Person getParent() {
		return parent;
	}

	public void setParent(Person parent) {
		this.parent = parent;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"id\": %d, \"name\": \"%s\", \"salary\": %s, \"parent\": %s", 
				id, name, salary, parent);
	}
}
