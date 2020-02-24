/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;

/**
 *
 * @author jsie
 */
@Clustered(callPostConstructOnAttach = true, callPreDestoyOnDetach = false,
        lock = DistributedLockType.INHERIT, keyName = "records")
@Singleton
public class RecordsManager implements Serializable {

    private List<String> RECORDS;

    private static final Logger LOG = Logger.getLogger(RecordsManager.class.getName());

    public RecordsManager() {
    }

    @PostConstruct
    public void init() {
        if (RECORDS == null) {
            RECORDS = Collections.synchronizedList(new LinkedList<>());
            LOG.log(Level.WARNING, "RECORDS was null and {0} is now initialized", RecordsManager.class.getSimpleName());
        } else {
            LOG.log(Level.WARNING, "{0} started but does not need to be initialized", RecordsManager.class.getSimpleName());
        }

    }

    @PreDestroy
    public void close() {
        LOG.log(Level.WARNING, "Destroying {0}", RecordsManager.class.getSimpleName());
    }

    synchronized public List<String> add(String value) {
        RECORDS.add(value);
        return Collections.unmodifiableList(RECORDS);
    }

    protected List<String> getRecords() {
        return Collections.unmodifiableList(RECORDS);
    }

    public boolean isNull() {
        return RECORDS == null;
    }

}
