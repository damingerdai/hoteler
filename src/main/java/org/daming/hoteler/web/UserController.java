package org.daming.hoteler.web;

import org.daming.hoteler.pojo.User;
import org.daming.hoteler.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController()
@RequestMapping("api/v1")
public class UserController {

    private IUserService userService;

    @GetMapping("users")
    public ResponseEntity<List<User>> listUser() {
        var users = this.userService.list();
        if (Objects.nonNull(users) && !users.isEmpty()) {
            return new ResponseEntity(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public UserController(IUserService userService) {
        super();
        this.userService = userService;
    }
}
