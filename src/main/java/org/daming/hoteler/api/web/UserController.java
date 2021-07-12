package org.daming.hoteler.api.web;

import io.swagger.annotations.ApiOperation;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.builder.UserBuilder;
import org.daming.hoteler.pojo.request.CreateUserRequest;
import org.daming.hoteler.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("api/v1")
public class UserController {

    private IUserService userService;

    @ApiOperation(value = "list user", notes = "list all users api")
    @GetMapping("users")
    public ResponseEntity<List<User>> listUser() {
        try {
            var users = this.userService.list();
            if (Objects.nonNull(users) && !users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (HotelerException ex) {
            LoggerManager.getApiLogger().error("HotelerException: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("Exception: " + ex.getMessage());
            throw ex;
        }

    }

    @ApiOperation(value = "create user", notes = "create a new user api")
    @PostMapping("user")
    public ResponseEntity<User> create(CreateUserRequest request) {
        var user = UserBuilder.fromCreateUserRequest(request);
        this.userService.create(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    public UserController(IUserService userService) {
        super();
        this.userService = userService;
    }
}
