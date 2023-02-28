package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.UserToken;

public interface ITokenService {

    UserToken createToken(String username, String password) throws HotelerException;

    UserToken refreshToken(String refreshToken) throws HotelerException;

    User verifyToken(String token) throws HotelerException;
}
