package org.daming.hoteler.api.web;

import org.daming.hoteler.pojo.UserRoom;
import org.daming.hoteler.pojo.request.CreateUserRoomRequest;
import org.daming.hoteler.pojo.request.UpdateUserRoomRequest;
import org.daming.hoteler.pojo.response.CommonResponse;
import org.daming.hoteler.pojo.response.DataResponse;
import org.daming.hoteler.service.IUserRoomService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户房间关联
 *
 * @author gming001
 * @create 2021-03-04 16:44
 **/
@RestController
@RequestMapping("api/v1")
public class UserRoomController {

    private IUserRoomService userRoomService;

    @PostMapping("users/rooms")
    public DataResponse<Long> createUserRoomRelationship(@RequestBody CreateUserRoomRequest request) {
        var ur = new UserRoom()
                .setUserId(request.getUserId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.userRoomService.create(ur);
        return new DataResponse<>(1L);
    }

    @PutMapping("users/rooms")
    public CommonResponse updateUserRoomRelationship(@RequestBody UpdateUserRoomRequest request) {
        var ur = new UserRoom()
                .setId(request.getId())
                .setUserId(request.getUserId())
                .setRoomId(request.getRoomId())
                .setBeginDate(request.getBeginDate())
                .setEndDate(request.getEndDate());
        this.userRoomService.update(ur);
        return new DataResponse<>(1L);
    }

    @GetMapping("users/rooms/:id")
    public CommonResponse getUserRoomRelationship(@PathVariable("id") long id) {
        var ur = this.userRoomService.get(id);
        return new DataResponse<>(ur);
    }

    @DeleteMapping("users/rooms/:id")
    public CommonResponse deleteUserRoomRelationship(@PathVariable("id") long id) {
        this.userRoomService.delete(id);
        return new CommonResponse();
    }

    public UserRoomController(IUserRoomService userRoomService) {
        super();
        this.userRoomService = userRoomService;
    }
}
