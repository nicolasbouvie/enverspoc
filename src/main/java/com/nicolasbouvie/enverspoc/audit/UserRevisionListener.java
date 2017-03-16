package com.nicolasbouvie.enverspoc.audit;

import org.hibernate.envers.RevisionListener;

public class UserRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		UserRevEntity exampleRevEntity = (UserRevEntity) revisionEntity;
		exampleRevEntity.setUsername("username");
	}
}
