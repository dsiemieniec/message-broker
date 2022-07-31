package com.damiansiemieniec.messagebroker.infrastructure.couchdb;

import com.damiansiemieniec.messagebroker.domain.entity.Subscriber;
import com.damiansiemieniec.messagebroker.domain.repository.SubscriberRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CouchDbSubscriberRepository extends CouchDbRepositorySupport<Subscriber> implements SubscriberRepository {
    public CouchDbSubscriberRepository(CouchDbConnector db) {
        super(Subscriber.class, db);
        //initStandardDesignDocument();
    }

    @Override
    public void save(Subscriber subscriber) {
        if (subscriber.getId() != null) {
            this.update(subscriber);
        } else {
            this.add(subscriber);
        }
    }

    @GenerateView
    public List<Subscriber> findByTopic(String topic) {
        return queryView("by_topic", topic);
    }
}
