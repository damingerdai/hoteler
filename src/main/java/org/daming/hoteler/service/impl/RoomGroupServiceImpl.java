package org.daming.hoteler.service.impl;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.base.logger.LoggerManager;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.constants.RoomGroupErrorCodeConstants;
import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.RoomGroupDashboardRow;
import org.daming.hoteler.pojo.RoomGroupWithCountRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;
import org.daming.hoteler.pojo.request.AssignRoomGroupRequest;
import org.daming.hoteler.pojo.request.CreateRoomGroupRequest;
import org.daming.hoteler.pojo.request.UpdateRoomGroupRequest;
import org.daming.hoteler.repository.jdbc.IRoomDao;
import org.daming.hoteler.repository.jdbc.IRoomGroupDao;
import org.daming.hoteler.service.IErrorService;
import org.daming.hoteler.service.IRoomGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RoomGroupServiceImpl implements IRoomGroupService {

    private final IRoomGroupDao roomGroupDao;
    private final IRoomDao roomDao;

    private IErrorService errorService;

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    @Override
    public RoomGroup create(CreateRoomGroupRequest request, String operator) throws HotelerException {
        if (request.getType() == null) {
            throw this.errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, "type is required");
        }
        if (request.getCode() == null || request.getCode().isBlank()) {
            throw this.errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, "code is required");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw this.errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, "name is required");
        }

        // TODO: 业务唯一性校验（若产品要求 (type, code) 唯一）
        // if (roomGroupDao.existsByTypeAndCode(request.getType(), request.getCode())) {
        //     throw new HotelerException(ErrorCodeConstants.CONFLICT,
        //             "room group code already exists: " + request.getCode());
        // }
        try {
            var group = new RoomGroup();
            group.setType(RoomGroupType.fromCode(request.getType()));
            group.setCode(request.getCode());
            group.setName(request.getName());
            if (Objects.nonNull(request.getSortOrder())) {
                group.setSortOrder(request.getSortOrder());
            }
            var groupId = this.roomGroupDao.create(group, operator);
            group.setId(groupId);
            return group;
        } catch (Exception ex) {
            LoggerManager.getCommonLogger().error("fail to create room group", ex);
            throw this.errorService.createHotelerException(RoomGroupErrorCodeConstants.CREATE_ROOM_GROUP_ERROR_CODE, ex);
        }

    }

    @Override
    public RoomGroup update(String groupId, UpdateRoomGroupRequest request, String operator) throws HotelerException {
        var group = this.roomGroupDao.get(groupId).orElseThrow(() -> this.errorService.createHotelerException(RoomGroupErrorCodeConstants.ROOM_GROUP_ERROR_NOT_FOUND_CODE));
        if (request.getName() != null) {
            if (request.getName().isBlank()) {
                throw this.errorService.createHotelerException(ErrorCodeConstants.BAD_REQUEST_ERROR_CODEE, "name must not be blank");
            }
            group.setName(request.getName());
        }
        if (request.getSortOrder() != null) {
            group.setSortOrder(request.getSortOrder());
        }
        if (request.getActive() != null) {
            group.setActive(request.getActive());
        }

        int affected = roomGroupDao.update(group, operator);
        if (affected == 0) {
            // 并发下可能被软删除，兜底
            LoggerManager.getCommonLogger().error( "room group not found or deleted: " + groupId);
            throw this.errorService.createHotelerException(RoomGroupErrorCodeConstants.ROOM_GROUP_ERROR_NOT_FOUND_CODE);
        }
        return group;
    }

    @Override
    @Transactional
    public void delete(String groupId, String operator) throws HotelerException {
        throw new RuntimeException("fail to delete group id");
    }

    @Override
    public RoomGroup get(String groupId) throws HotelerException {
        var group = this.roomGroupDao.get(groupId).orElseThrow(() -> this.errorService.createHotelerException(RoomGroupErrorCodeConstants.ROOM_GROUP_ERROR_NOT_FOUND_CODE));
        return group;
    }

    @Override
    public List<RoomGroupWithCountRow> list(RoomGroupType type, Boolean active) {
        return this.roomGroupDao.listWithRoomCount(type, active);
    }

    @Override
    public void assignRoom(String roomId, AssignRoomGroupRequest request, String operator) {
        throw new RuntimeException("fail to assign room");
    }

    @Override
    public List<RoomGroupDashboardRow> dashboard(RoomGroupType type, Boolean active) {
        return roomGroupDao.dashboard(type, active);

    }

    public RoomGroupServiceImpl(IRoomGroupDao roomGroupDao, IRoomDao roomDao) {
        this.roomGroupDao = roomGroupDao;
        this.roomDao = roomDao;
    }
}
