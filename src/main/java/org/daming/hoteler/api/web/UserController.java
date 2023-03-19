package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.User;
import org.daming.hoteler.pojo.builder.UserBuilder;
import org.daming.hoteler.pojo.request.CreateUserRequest;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListResponse;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "用户controller")
@RestController()
@RequestMapping("api/v1")
public class UserController {

    private IUserService userService;

    private IErrorService errorService;

    @Operation(summary = "获取所有的用户",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("users")
    public ListResponse<User> listUser() {
        try {
            var users = this.userService.list();
            return new ListResponse<>(users);
        } catch (HotelerException ex) {
            LoggerManager.getApiLogger().error("HotelerException: " + ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("Exception: " + ex.getMessage());
            throw ex;
        }

    }

    @Operation(summary = "创建用户",security = { @SecurityRequirement(name = "bearer-key") })
    @PostMapping("user")
    public DataResponse<User> create(@RequestBody  CreateUserRequest request) {
        var user = UserBuilder.fromCreateUserRequest(request);
        user.setPasswordType("md5");
        this.userService.create(user);
        return new DataResponse<>(user);
    }

    @Operation(summary = "获取用户",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/user/{id}")
    public DataResponse<User> getByUserId(@PathVariable("id") String userId) {
        try {
            var id = Long.parseLong(userId);
            var user = this.userService.get(id);
            return new DataResponse<>(user);
        } catch (NumberFormatException nfe) {
            var params = new Object[] { nfe.getMessage() };
            throw errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, params, nfe);
        } catch (Exception ex) {
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex);
        }
    }

    @Operation(summary = "获取当前用户",security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("/user")
    public DataResponse<User> get() {
        var context = ThreadLocalContextHolder.get();
        var user = context.getUser();
        return new DataResponse<>(user);
    }


    public UserController(IUserService userService, IErrorService errorService) {
        super();
        this.userService = userService;
        this.errorService = errorService;
    }
}
