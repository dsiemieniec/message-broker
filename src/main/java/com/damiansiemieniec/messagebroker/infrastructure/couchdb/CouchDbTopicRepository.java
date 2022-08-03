package com.damiansiemieniec.messagebroker.infrastructure.couchdb;

import com.damiansiemieniec.messagebroker.domain.entity.Topic;
import com.damiansiemieniec.messagebroker.domain.repository.TopicRepository;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.GenerateView;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CouchDbTopicRepository extends CouchDbRepositorySupport<Topic> implements TopicRepository {
    public CouchDbTopicRepository(CouchDbConnector db) {
        super(Topic.class, db);
        //initStandardDesignDocument();
    }

    @Override
    public void save(Topic topic) {
        if (topic.getId() != null) {
            this.update(topic);
        } else {
            this.add(topic);
        }
    }

    public Optional<Topic> findOneByName(String name) {
        return this.findByName(name).stream().findFirst();
    }

    @GenerateView
    private List<Topic> findByName(String name) {
        return queryView("by_name", name);
    }
}
