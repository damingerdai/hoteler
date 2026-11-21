package org.daming.hoteler.pojo.converter;

import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.dto.RoomGroupRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * RoomGroup 与持久化行对象之间的纯转换工具。
 *
 * <p>本类不依赖任何持久化框架（Spring JDBC / MyBatis），
 * 因此可同时被 {@code RoomGroupRowMapper}（JdbcTemplate）
 * 和 {@code RoomGroupTypeHandler}（MyBatis）复用。</p>
 *
 * <p>所有方法均为静态、无状态、线程安全。</p>
 */
public final class RoomGroupConverter {

    private RoomGroupConverter() {}

    /**
     * 将数据库行对象转换为领域对象。
     *
     * <p>注意：{@link RoomGroupRow} 中的 {@code type} 是 {@code String}，
     * 这里统一转换为 {@link org.daming.hoteler.pojo.enums.RoomGroupType} 枚举。若 DB 中出现枚举未定义的值，
     * 抛出 {@link IllegalStateException}（属于数据问题，不是用户输入问题）。</p>
     *
     * @param row 数据库行，可为 {@code null}
     * @return 领域对象；{@code row} 为 {@code null} 时返回 {@code null}
     * @throws IllegalStateException {@code row.type} 不是合法的 {@link org.daming.hoteler.pojo.enums.RoomGroupType}
     */
    public static RoomGroup toDomain(RoomGroupRow row) {
        if (row == null) return null;
        return new RoomGroup(
                row.getId(),
                toEnum(row.getType()),
                row.getCode(),
                row.getName(),
                row.getSortOrder(),
                row.isActive()
        );
    }

    /**
     * 将领域对象转换为数据库行对象（仅填充业务列）。
     *
     * <p><b>审计列与软删除列不由本方法填充</b>：
     * {@code create_dt / create_user / update_dt / update_user / deleted_at}
     * 由 DAO 的 insert/update 方法根据当前时钟和登录用户补齐。
     * 本方法只负责业务字段的映射，保证转换逻辑单一职责。</p>
     *
     * <p>典型用途：单元测试构造期望 Row、或 MyBatis 自定义写入前的中间转换。</p>
     *
     * @param group 领域对象，可为 {@code null}
     * @return 行对象；{@code group} 为 {@code null} 时返回 {@code null}
     */
    public static RoomGroupRow toRow(RoomGroup group) {
        if (group == null) {
            return null;
        }
        RoomGroupRow row = new RoomGroupRow();
        row.setId(group.getId());
        row.setType(toDbValue(group.getType()));
        row.setCode(group.getCode());
        row.setName(group.getName());
        row.setSortOrder(group.getSortOrder());
        row.setActive(group.isActive());
        // 审计列 / deleted_at 由调用方填充
        return row;
    }


    /**
     * {@code String -> RoomGroupType}，用于 DB 读取。
     *
     * <p>DB 中出现非法枚举值时抛 {@link IllegalStateException}，
     * 语义是"持久化数据已损坏"，应触发告警而不是返回 400。</p>
     *
     * @param value DB 中的字符串值，可为 {@code null}
     * @return 枚举值；{@code value} 为 {@code null} 时返回 {@code null}
     * @throws IllegalStateException {@code value} 不是合法的 {@link RoomGroupType}
     */
    public static RoomGroupType toEnum(String value) {
        if (value == null) {
            return null;
        }
        try {
            return RoomGroupType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Unknown RoomGroupType: " + value, e);
        }
    }

    /**
     * {@code String -> RoomGroupType}，用于用户输入反序列化。
     *
     * <p>与 {@link #toEnum(String)} 的区别：非法值抛 {@link IllegalArgumentException}，
     * 由全局异常处理转换为 400 Bad Request。</p>
     *
     * @param value 用户输入值，可为 {@code null}
     * @return 枚举值；{@code value} 为 {@code null} 时返回 {@code null}
     * @throws IllegalArgumentException {@code value} 不是合法的 {@link RoomGroupType}
     */
    public static RoomGroupType fromNullable(String value) {
        if (value == null) {
            return null;
        }
        try {
            return RoomGroupType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid RoomGroupType: " + value, e);
        }
    }

    /**
     * {@code RoomGroupType -> String}，用于写库。
     *
     * @param type 枚举值，可为 {@code null}
     * @return DB 字符串；{@code type} 为 {@code null} 时返回 {@code null}
     */
    public static String toDbValue(RoomGroupType type) {
        return type == null ? null : type.name();
    }


    /**
     * 判断两个领域对象在<b>业务字段</b>上是否相等（忽略审计/软删除）。
     * 主要给单元测试用，避免手写字段比较。
     */
    public static boolean businessEquals(RoomGroup a, RoomGroup b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.getSortOrder() == b.getSortOrder()
                && a.isActive() == b.isActive()
                && Objects.equals(a.getId(), b.getId())
                && a.getType() == b.getType()
                && Objects.equals(a.getCode(), b.getCode())
                && Objects.equals(a.getName(), b.getName());
    }

    /**
     * 带审计字段的重载。审计值由调用方提供，Converter 不感知时钟和登录用户。
     *
     * @param group      领域对象
     * @param createDt   创建时间
     * @param createUser 创建人，可为 null
     * @param updateDt   更新时间
     * @param updateUser 更新人，可为 null
     * @param deletedAt  软删除时间，null 表示未删除
     */
    public static RoomGroupRow toRow(RoomGroup group,
                                     LocalDateTime createDt, String createUser,
                                     LocalDateTime updateDt, String updateUser,
                                     LocalDateTime deletedAt) {
        RoomGroupRow row = toRow(group);
        if (row == null) {
            return null;
        }
        row.setCreateDt(createDt);
        row.setCreateUser(createUser);
        row.setUpdateDt(updateDt);
        row.setUpdateUser(updateUser);
        row.setDeletedAt(deletedAt);
        return row;
    }

}
