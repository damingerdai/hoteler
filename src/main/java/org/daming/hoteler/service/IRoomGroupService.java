package org.daming.hoteler.service;

import org.daming.hoteler.base.exceptions.HotelerException;
import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.RoomGroupDashboardRow;
import org.daming.hoteler.pojo.RoomGroupWithCountRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;
import org.daming.hoteler.pojo.request.AssignRoomGroupRequest;
import org.daming.hoteler.pojo.request.CreateRoomGroupRequest;
import org.daming.hoteler.pojo.request.UpdateRoomGroupRequest;

import java.util.List;

public interface IRoomGroupService {

    /**
     * 创建分组。id 由服务端生成，active 默认 true。
     *
     * @throws HotelerException
     *         当 type/code 重复（若业务要求唯一）或参数非法时
     */
    RoomGroup create(CreateRoomGroupRequest request, String operator) throws HotelerException;

    /**
     * 更新分组可变字段（name / sortOrder / active）。
     * type / code 不可变。
     tring
     * @throws HotelerException 分组不存在或已删除
     */
    RoomGroup update(String groupId, UpdateRoomGroupRequest request, String operator) throws HotelerException;

    /**
     * 软删除分组，并将其下所有房间置为 ungrouped（rooms.group_id = NULL）。
     * 房间本身不删除。
     *
     * @throws HotelerException 分组不存在或已删除
     */
    void delete(String groupId, String operator) throws HotelerException;

    /**
     * 查询单个未删除分组。
     */
    RoomGroup get(String groupId) throws HotelerException;

    /**
     * 列表查询，带"活跃房间数"。
     *
     * @param type   可为 null
     * @param active 可为 null
     */
    List<RoomGroupWithCountRow> list(RoomGroupType type, Boolean active);

    /**
     * 列表查询，带"活跃房间数"。
     *
     * @param type   可为 null
     * @param active 可为 null
     * @param pageable 不可为null
     */
    List<RoomGroupWithCountRow> list(RoomGroupType type, Boolean active, Pageable pageable);

    int count(RoomGroupType type, Boolean active);

    /**
     * 把房间分配到分组；groupId 为 null 时表示移除分组。
     *
     * @throws org.daming.hoteler.base.exceptions.HotelerException
     *         房间不存在/已删除、分组不存在/已删除/未启用
     */
    void assignRoom(String roomId, AssignRoomGroupRequest request, String operator);

    /**
     * 分组 dashboard 聚合查询。
     */
    List<RoomGroupDashboardRow> dashboard(RoomGroupType type, Boolean active);
}
