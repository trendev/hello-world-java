/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import com.hazelcast.core.IMap;
import fr.trendev.controllers.qualifiers.HazelcastDistributedMap;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author jsie
 */
@ApplicationScoped
@HazelcastDistributedMap
public class HazelcastDistributedMapRecordsManager extends HazelcastAbstractRecordsManager {

    private IMap<String, LinkedList<String>> map;

    private static final Logger LOG = Logger.getLogger(HazelcastDistributedMapRecordsManager.class.getName());

    public HazelcastDistributedMapRecordsManager() {
        super();
        this.map = hz.getMap(this.getClass().getName());
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
        LOG.log(Level.WARNING, "{0} is now initialized", this.getClass().getSimpleName());
    }

    @PreDestroy
    @Override
    public void close() {
        LOG.log(Level.WARNING, "Destroying {0}", this.getClass().getSimpleName());
    }

    @Override
    public List<String> add(String value) {

        LinkedList<String> records = new LinkedList<>();

        // replace with FencedLock when CP system will be enabled
        Lock hzLock = hz.getLock(this.getClass().getName());
        hzLock.lock();

        try {
            records = this.map.getOrDefault(key, new LinkedList<>());

            //pop old entries
            if (records.size() >= maxSize) {
                while (records.size() != maxSize - 1) {
                    records.remove();
                }
            }
            records.add(value);

            this.map.put(key, records);

        } finally {
            hzLock.unlock();
        }

        return Collections.unmodifiableList(records);

    }
}
