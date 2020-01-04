package org.daming.hoteler.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    private IUserService userService;

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    public List<User> allUsers() {
        return this.userService.list();
    }
}
