/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 *
 * @author jsie
 */
@HazelcastReplicatedMap
@ApplicationScoped
public class RecordsManager2 implements RecordsManager {

    private ReplicatedMap<String, List<String>> replicatedMap;

    private int maxSize;

    private static final Logger LOG = Logger.getLogger(RecordsManager2.class.getName());

    @PostConstruct
    @Override
    public void init() {

        // limits the size of the list
        Config config = ConfigProvider.getConfig();
        this.maxSize = Integer.parseInt(
                config.getOptionalValue("RECORDS_MAX_SIZE", String.class)
                        .orElse("20"));

        Hazelcast.getAllHazelcastInstances().forEach(hz -> {
            LOG.log(Level.WARNING, "Hazelcast instance : {0}", hz.getName());
        });

        LOG.log(Level.WARNING, "{0} is now initialized", this.getClass().getSimpleName());

    }

    @PreDestroy
    @Override
    public void close() {
        LOG.log(Level.WARNING, "Destroying {0}", this.getClass().getSimpleName());
    }

    @Override
    public List<String> add(String value) {
        return new LinkedList<>();
    }

}
