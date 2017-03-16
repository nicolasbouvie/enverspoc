package com.nicolasbouvie.enverspoc.audit;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(UserRevisionListener.class)
public class UserRevEntity extends DefaultRevisionEntity {
	private static final long serialVersionUID = -6194565815280186968L;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "UserRevEntity [getUsername()=" + getUsername() + ", getId()=" + getId() + ", getRevisionDate()="
				+ getRevisionDate() + ", getTimestamp()=" + getTimestamp() + "]";
	}
}
