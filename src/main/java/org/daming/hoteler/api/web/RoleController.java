package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.daming.hoteler.pojo.Role;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListResponse;
import org.daming.hoteler.service.IRoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gming001
 * @since 2021-12-29 20:45
 **/
@Tag(name = "角色 controller")
@RestController
@RequestMapping("api/v1")
public class RoleController {

    private IRoleService roleService;

    @Operation(summary = "获取角色", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("role/{id}")
    public DataResponse<Role> get(@PathVariable(value = "id")long id) {
        var role = this.roleService.get(id);
        var response = new DataResponse<>(role);

        return response;
    }

    @Operation(summary = "获取所有角色", security = { @SecurityRequirement(name = "bearer-key") })
    @GetMapping("roles")
    public ListResponse<Role> list() {
        var roles = this.roleService.list();
        var response = new ListResponse<>(roles);

        return response;
    }


    public RoleController(IRoleService roleService) {
        super();
        this.roleService = roleService;
    }
}
