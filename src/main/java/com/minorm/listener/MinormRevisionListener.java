package com.minorm.listener;

import com.minorm.entity.Revision;
import org.hibernate.envers.RevisionListener;

public class MinormRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
//        SecurityContext.getUser().getId()
        ((Revision) revisionEntity).setUsername("dmdev");
    }
}
