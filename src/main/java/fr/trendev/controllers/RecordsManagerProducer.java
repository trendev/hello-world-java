/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import fr.trendev.controllers.qualifiers.HazelcastReplicatedMap;
import fr.trendev.controllers.qualifiers.ClusteredSingleton;
import fr.trendev.controllers.qualifiers.HazelcastDistributedMap;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 *
 * @author jsie
 */
@ApplicationScoped
public class RecordsManagerProducer {

    private final static String HAZELCAST_REPLICATED_MAP
            = "hazelcast-replicated-map";

    private final static String CLUSTERED_SINGLETON
            = "clustered-singleton";

    private final static String HAZELCAST_DISTRIBUTED_MAP
            = "hazelcast-distributed-map";

    @Inject
    @ConfigProperty(name = "CLUSTERED_OBJECT_TYPE", defaultValue = HAZELCAST_REPLICATED_MAP)
    private String clusterObjectType;

    @Inject
    @ClusteredSingleton
    private RecordsManager rm1;

    @Inject
    @HazelcastReplicatedMap
    private RecordsManager rm2;

    @Inject
    @HazelcastDistributedMap
    private RecordsManager rm3;

    private RecordsManager rm;

    private List<RecordsManagerSelector> selectors;

    @PostConstruct
    public void init() {
        selectors = Arrays.asList(
                new RecordsManagerSelector(CLUSTERED_SINGLETON, rm1),
                new RecordsManagerSelector(HAZELCAST_REPLICATED_MAP, rm2),
                new RecordsManagerSelector(HAZELCAST_DISTRIBUTED_MAP, rm3));
    }

    @Produces
    @Default
    public RecordsManager getRecordsManager() {

        if (this.rm == null) {
            this.rm = selectors.stream()
                    .map(rms -> rms.select(clusterObjectType))
                    .filter(o -> o.isPresent())
                    .findFirst()
                    .map(o -> o.get())
                    .orElseThrow(()
                            -> new WebApplicationException("clusterObjectType="
                            + clusterObjectType
                            + " : it is not a supported clustered object type!"));
        }

        return this.rm;

    }

    private class RecordsManagerSelector {

        private final String type;
        private final RecordsManager rm;

        RecordsManagerSelector(String type, RecordsManager rm) {
            this.type = type;
            this.rm = rm;
        }

        Optional<RecordsManager> select(String type) {
            if (this.type.equals(type)) {
                return Optional.of(rm);
            } else {
                return Optional.empty();
            }

        }

    }
}
