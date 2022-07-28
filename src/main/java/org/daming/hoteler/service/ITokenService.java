package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.UserToken;

public interface ITokenService {

    UserToken createToken(String username, String password);

    UserToken refreshToken(String refreshToken);

    User verifyToken(String token) throws HotelerException;
}
