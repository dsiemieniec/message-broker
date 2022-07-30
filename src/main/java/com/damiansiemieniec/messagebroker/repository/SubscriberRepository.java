package com.damiansiemieniec.messagebroker.repository;

import com.damiansiemieniec.messagebroker.entity.Subscriber;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriberRepository extends CouchDbRepositorySupport<Subscriber> {
    public SubscriberRepository(CouchDbConnector db) {
        super(Subscriber.class, db);
        initStandardDesignDocument();
    }

    @GenerateView
    public List<Subscriber> findByTopic(String topic) {
        return queryView("by_topic", topic);
    }
}
