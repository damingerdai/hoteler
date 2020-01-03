package org.daming.hoteler.service.impl;

import org.daming.hoteler.dao.mapper.UserMapper;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return this.userMapper.list();
    }

    public UserServiceImpl(UserMapper userMapper) {
        super();
        this.userMapper = userMapper;
    }
}
