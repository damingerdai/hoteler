package org.daming.hoteler.api.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.daming.hoteler.base.context.ThreadLocalContextHolder;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.RoomGroupWithCountRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;
import org.daming.hoteler.pojo.request.CreateRoomGroupRequest;
import org.daming.hoteler.pojo.request.UpdateRoomGroupRequest;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.pojo.response.ListPageResponse;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IRoomGroupService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/room-groups")
@Validated
@Tag(name = "房型分组管理", description = "提供房型分组的创建与维护接口")
public class RoomGroupController {

    private final IRoomGroupService roomGroupService;

    private final IErrorService errorService;

    @Operation(
            summary = "创建房型分组",
            description = "根据请求参数创建新的房型分组，创建人取自当前登录上下文"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "创建成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数校验失败",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录或登录态已失效",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PostMapping
    public DataResponse create(
            @Valid @RequestBody CreateRoomGroupRequest request) {
        try {
            var context = ThreadLocalContextHolder.get();
            var currentUser = context.getUser();
            var group = this.roomGroupService.create(request, currentUser.getId());
            return DataResponse.ok(group);
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("fail to create room group", ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex.getMessage(), ex);
        }
    }

    @Operation(
            summary = "更新房型分组",
            description = "根据房型分组 ID 更新分组信息，操作人取自当前登录上下文"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "更新成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "请求参数校验失败",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录或登录态已失效",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "房型分组不存在",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping("/{groupId}")
    public DataResponse update(@PathVariable("groupId") String groupId, @Valid @RequestBody UpdateRoomGroupRequest request) {
        try {
            var context = ThreadLocalContextHolder.get();
            var currentUser = context.getUser();
            var group = this.roomGroupService.update(groupId, request, currentUser.getId());
            return DataResponse.ok(group);
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("fail to update room group", ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex.getMessage(), ex);
        }

    }

    @Operation(
            summary = "查询房型分组详情",
            description = "根据房型分组 ID 查询对应的房型分组信息"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "路径参数不合法",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录或登录态已失效",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "房型分组不存在",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/{id}")
    public DataResponse get(@PathVariable("id") String groupId) {
        try {
            var group = this.roomGroupService.get(groupId);
            return DataResponse.ok(group);
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("fail to get room group", ex);
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex.getMessage(), ex);
        }
    }


    @Operation(
            summary = "分页查询房型分组列表",
            description = "支持按页码、每页条数、房型分组类型及启用状态筛选房型分组"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "查询成功",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ListPageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "查询参数不合法",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "未登录或登录态已失效",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("")
    public ListPageResponse<RoomGroupWithCountRow> list(
            @Parameter(description = "页码，从 1 开始", example = "1")
            @RequestParam(name = "page", required = false) Integer page,
            @Parameter(description = "每页记录数", example = "10")
            @RequestParam(name = "pageSize", required = false) Integer pageSize,
            @Parameter(
                    description = "房型分组类型（支持 FLOOR/floor、ZONE/zone）",
                    schema = @Schema(implementation = RoomGroupType.class),
                    example = "FLOOR"
            )
            @RequestParam(name = "type", required = false) RoomGroupType roomGroupType,
            @Parameter(description = "是否启用", example = "true")
            @RequestParam(name = "active", required = false) Boolean active
    ) {
        try {
            var pageable = new Pageable();
            pageable.setPageNo(Objects.requireNonNullElse(page, 1));
            pageable.setPageSize(Objects.requireNonNullElse(pageSize, 10));
            var groups = this.roomGroupService.list(roomGroupType, active, pageable);
            var count = this.roomGroupService.count(roomGroupType, active);
            return new ListPageResponse<RoomGroupWithCountRow>(groups, count);
        } catch (Exception ex) {
            LoggerManager.getApiLogger().error("fail to list room group", ex);
            throw errorService.createHotelerException(ErrorCodeConstants.SYSTEM_ERROR_CODEE, ex.getMessage(), ex);
        }
    }

    public RoomGroupController(IRoomGroupService roomGroupService, IErrorService errorService) {
        this.roomGroupService = roomGroupService;
        this.errorService = errorService;
    }
}
