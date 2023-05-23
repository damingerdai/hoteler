package org.daming.hoteler.config.service;

import org.daming.hoteler.base.exceptions.HotelerException;

/**
 * @author gming001
 * @version 2022-10-08 17:27
 */
public interface ISecretPropService {

    String getSalt() throws HotelerException;

    String getKey() throws HotelerException;

    String getPersonSalt() throws HotelerException;
}
