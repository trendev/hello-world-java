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
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 *
 * @author jsie
 */
@Clustered(callPostConstructOnAttach = true, callPreDestoyOnDetach = false,
        lock = DistributedLockType.INHERIT, keyName = "records")
@Singleton
@ClusteredSingleton
public class RecordsManager1 implements RecordsManager {

    private LinkedList<String> records;

    private int maxSize;

    private static final Logger LOG = Logger.getLogger(RecordsManager1.class.getName());

    public RecordsManager1() {
    }

    @PostConstruct
    @Override
    public void init() {

        // limits the size of the list
        Config config = ConfigProvider.getConfig();
        this.maxSize = Integer.parseInt(
                config.getOptionalValue("RECORDS_MAX_SIZE", String.class)
                        .orElse("20"));

        if (records == null) {
            records = new LinkedList<>();
            LOG.log(Level.WARNING, "records was null and {0} is now initialized", RecordsManager1.class.getSimpleName());
        } else {
            LOG.log(Level.WARNING, "{0} started but does not need to be initialized", RecordsManager1.class.getSimpleName());
        }

    }

    @PreDestroy
    @Override
    public void close() {
        LOG.log(Level.WARNING, "Destroying {0}", RecordsManager1.class.getSimpleName());
    }

    @Override
    synchronized public List<String> add(String value) {

        if (isNull()) {
            throw new IllegalStateException("Records cannot be null");
        }

        //pop old entries
        if (records.size() >= maxSize) {
            while (records.size() != maxSize - 1) {
                records.remove();
            }
        }
        records.add(value);
        return Collections.unmodifiableList(records);
    }

    protected List<String> getRecords() {
        return Collections.unmodifiableList(records);
    }

    @Override
    public boolean isNull() {
        return records == null;
    }

}
