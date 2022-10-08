package org.daming.hoteler.config.service;

/**
 * @author gming001
 * @version 2022-10-08 17:27
 */
public interface ISecretPropService {

    String getSalt();

    String getKey();
}
