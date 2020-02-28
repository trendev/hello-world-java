/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author jsie
 */
@ApplicationScoped
public class RecordsManagerProducer {

    private final static String HAZELCAST_REPLICATED_MAP = "hazelcast-replicated-map";
    private final static String CLUSTERED_SINGLETON = "clustered-singleton";

    @Inject
    @ConfigProperty(name = "CLUSTERED_OBJECT_TYPE", defaultValue = HAZELCAST_REPLICATED_MAP)
    private String clusterObjectType;

    @Inject
    @ClusteredSingleton
    private RecordsManager rm1;

    @Inject
    @HazelcastReplicatedMap
    private RecordsManager rm2;

    @Produces
    @Default
    public RecordsManager getRecordsManager() {
        if (CLUSTERED_SINGLETON.equals(this.clusterObjectType)) {
            return rm1;
        }
        return rm2;
    }

}
