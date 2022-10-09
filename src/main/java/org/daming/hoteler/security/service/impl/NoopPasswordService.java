package org.daming.hoteler.security.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.security.service.IPasswordService;
import org.springframework.stereotype.Service;

/**
 * @author gming001
 * @version 2022-10-09 13:25
 */
@Service("noopPasswordService")
public class NoopPasswordService implements IPasswordService {

    @Override
    public String decrypt(String encoderContent) throws HotelerException {
        return encoderContent;
    }

    @Override
    public String encrypt(String content) throws HotelerException {
        return content;
    }
}
