package org.daming.hoteler.repository.jdbc;

import org.daming.hoteler.pojo.Pageable;
import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.RoomGroupDashboardRow;
import org.daming.hoteler.pojo.RoomGroupWithCountRow;
import org.daming.hoteler.pojo.dto.RoomGroupRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;

import java.util.List;
import java.util.Optional;

/**
 * room_groups 表的 DAO。
 *
 * <p>约定：</p>
 * <ul>
 *   <li>所有查询默认过滤 {@code deleted_at IS NULL}（软删除）；</li>
 *   <li>返回值优先使用领域对象 {@link RoomGroup}，dashboard 聚合返回专用 Row；</li>
 *   <li>写操作由调用方（Service）负责事务边界。</li>
 * </ul>
 */
public interface IRoomGroupDao {

    /**
     * 插入一个分组。{@code id} 由 Service 生成后传入，审计字段由本方法填充。
     *
     * @param group 领域对象，{@code id} 必须非空
     * @param operator 当前操作人，可为 {@code null}
     */
    String create(RoomGroup group, String operator);

    default String  create(RoomGroup group) {
       return this.create(group, null);
    }

    /**
     * 更新分组的可变字段（name / sort_order / active）。
     * 不更新 type / code（创建后不可变）。
     *
     * @param group 领域对象，{@code id} 必须非空
     * @param operator 当前操作人，可为 {@code null}
     * @return 受影响行数；0 表示分组不存在或已删除
     */
    int update(RoomGroup group, String operator);

    default int update(RoomGroup group) {
        return this.update(group, null);
    }

    /**
     * 软删除分组：{@code UPDATE room_groups SET deleted_at = now() WHERE id = ? AND deleted_at IS NULL}。
     *
     * <p>注意：本方法<b>不会</b>修改 rooms.group_id；把房间置为 ungrouped
     * 由 Service 在同一事务中调用 {@code IRoomDao#clearGroupByGroupId} 完成，
     * 因为那是 rooms 表的职责。</p>
     *
     * @param groupId 分组 ID
     * @param operator 当前操作人
     * @return 受影响行数；0 表示不存在或已删除
     */
    int delete(String groupId, String operator);

    default int delete(String groupId) {
        return this.delete(groupId, null);
    }

    /**
     * 按 ID 查询未删除的分组。
     *
     * @param groupId 分组 ID
     * @return 分组；不存在或已删除时返回 {@link Optional#empty()}
     */
    Optional<RoomGroup> get(String groupId);


    /**
     * 查询未删除的行对象（含审计字段），用于需要审计信息的场景。
     * 大多数业务用 {@link #get(String)} 即可。
     */
    Optional<RoomGroupRow> getRow(String groupId);

    /**
     * 按条件查询未删除的分组列表。
     *
     * @param type   类型过滤，可为 {@code null}
     * @param active 启用状态过滤，可为 {@code null}
     * @return 按 {@code sort_order, code} 排序的列表，永不为 {@code null}
     */
    List<RoomGroup> list(RoomGroupType type, Boolean active);

    /**
     * 查询分组列表并附带"活跃房间数"（active rooms count）。
     * 用于 {@code GET /api/v1/room-groups} 的响应。
     *
     * @param type   类型过滤，可为 {@code null}
     * @param active 启用状态过滤，可为 {@code null}
     * @return 行对象列表（含 {@code roomCount}）
     */
    List<RoomGroupWithCountRow> listWithRoomCount(RoomGroupType type, Boolean active);

    /**
     * 查询分组列表并附带"活跃房间数"（active rooms count）。
     * 用于 {@code GET /api/v1/room-groups} 的响应。
     *
     * @param type   类型过滤，可为 {@code null}
     * @param active 启用状态过滤，可为 {@code null}
     * @param pageable 分页信息，可为 {@code null}
     * @return 行对象列表（含 {@code roomCount}）
     */
    List<RoomGroupWithCountRow> listWithRoomCount(RoomGroupType type, Boolean active, Pageable pageable);

    int countWithRoomCount(RoomGroupType type, Boolean active);

    /**
     * 分组 dashboard 聚合查询。
     *
     * <p>聚合在 SQL 中完成（{@code COUNT ... FILTER} / {@code GROUP BY}），
     * 不把房间加载到内存。排除已删除的分组和房间。</p>
     *
     * @param type   类型过滤，可为 {@code null}
     * @param active 启用状态过滤，可为 {@code null}
     * @return 每个分组一行聚合结果，按 {@code sort_order, code} 排序
     */
    List<RoomGroupDashboardRow> dashboard(RoomGroupType type, Boolean active);
}
