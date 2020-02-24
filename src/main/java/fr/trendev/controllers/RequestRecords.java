/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import fish.payara.cluster.Clustered;
import fish.payara.cluster.DistributedLockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author jsie
 */
@Clustered(callPostConstructOnAttach = false, callPreDestoyOnDetach = false,
        lock = DistributedLockType.LOCK, keyName = "white-map")
@Singleton
@Startup
public class RequestRecords {

    public RequestRecords() {
    }
    
}
