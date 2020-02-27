/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.trendev.controllers;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author jsie
 */
public interface RecordsManager extends Serializable {

    List<String> add(String value);

    @PreDestroy
    void close();

    @PostConstruct
    void init();
    
}
