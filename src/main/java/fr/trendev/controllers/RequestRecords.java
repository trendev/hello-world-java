/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author jsie
 */
@Clustered(callPostConstructOnAttach = false, callPreDestoyOnDetach = false,
        lock = DistributedLockType.LOCK, keyName = "request-records")
@Singleton
@Startup
public class RequestRecords {

    private static volatile List<String> RECORDS
            = Collections.synchronizedList(new LinkedList<>());

    private static final Logger LOG = Logger.getLogger(RequestRecords.class.getName());

    public RequestRecords() {
    }

    @PostConstruct
    public void init() {
        LOG.log(Level.INFO, "{0} initialized", RequestRecords.class.getSimpleName());
    }

    @PreDestroy
    public void close() {
        LOG.log(Level.INFO, "Destroying {0}", RequestRecords.class.getSimpleName());
    }

    public void add(String value) {
        RECORDS.add(value);
    }

    public static List<String> getRecords() {
        return Collections.unmodifiableList(RECORDS);
    }

}
