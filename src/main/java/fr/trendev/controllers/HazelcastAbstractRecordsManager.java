/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

/**
 *
 * @author jsie
 */
public abstract class HazelcastAbstractRecordsManager implements RecordsManager {

    protected HazelcastInstance hz;
    protected int maxSize;
    protected final String key = "records";

    public HazelcastAbstractRecordsManager() {
        List<HazelcastInstance> hzInstances
                = new ArrayList<>(Hazelcast.getAllHazelcastInstances());

        if (hzInstances.isEmpty()) {
            throw new IllegalStateException("No Hazelcast instance available");
        } else {
            this.hz = hzInstances.get(0);
        }
    }

    @PostConstruct
    @Override
    public void init() {

        // limits the size of the list
        Config config = ConfigProvider.getConfig();
        this.maxSize = Integer.parseInt(
                config.getOptionalValue("RECORDS_MAX_SIZE", String.class)
                        .orElse("20"));

    }
}
