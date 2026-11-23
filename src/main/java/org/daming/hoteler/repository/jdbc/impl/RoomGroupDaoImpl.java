package org.daming.hoteler.repository.jdbc.impl;

import org.daming.hoteler.base.logger.SqlLoggerUtil;
import org.daming.hoteler.constants.ErrorCodeConstants;
import org.daming.hoteler.pojo.RoomGroup;
import org.daming.hoteler.pojo.RoomGroupDashboardRow;
import org.daming.hoteler.pojo.RoomGroupWithCountRow;
import org.daming.hoteler.pojo.converter.RoomGroupConverter;
import org.daming.hoteler.pojo.dto.RoomGroupRow;
import org.daming.hoteler.pojo.enums.RoomGroupType;
import org.daming.hoteler.repository.jdbc.AbstractBaseDao;
import org.daming.hoteler.repository.jdbc.IRoomGroupDao;
import org.daming.hoteler.service.IErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RoomGroupDaoImpl extends AbstractBaseDao<RoomGroup> implements IRoomGroupDao {

    private static final String DEFAULT_OPERATOR = "system";

    @Autowired
    public void setErrorService(IErrorService errorService) {
        this.errorService = errorService;
    }

    @Override
    public String create(RoomGroup group, String operator) {
        var in = Instant.now();
        var sql = """
                INSERT INTO room_groups
                   (type, code, name, sort_order, active, create_dt, create_user, update_dt, update_user)
                VALUES (?,  ?, ?, ?, ?, now(), ?, now(), ?)
                RETURNING id
                """;
        var defaultOperator = Objects.requireNonNullElse(operator, DEFAULT_OPERATOR);
        var params = new Object[]{
                RoomGroupConverter.toDbValue(group.getType()),
                group.getCode(),
                group.getName(),
                group.getSortOrder(),
                group.isActive(),
                defaultOperator,
                defaultOperator
        };
        try {
            return this.getJdbcTemplate().queryForObject(sql, String.class,params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.getErrorService().createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[]{sql}, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public int update(RoomGroup group, String operator) {
        var in = Instant.now();
        var sql = """
            UPDATE room_groups
            SET name = ?, sort_order = ?, active = ?, update_dt = now(), update_user = ?
            WHERE id = ?::uuid AND deleted_at IS NULL
        """;
        var defaultOperator = Objects.requireNonNullElse(operator, DEFAULT_OPERATOR);
        var params = new Object[] {
                group.getName(),
                group.getSortOrder(),
                group.isActive(),
                defaultOperator,
                group.getId()
        };
        try {
            return this.getJdbcTemplate().update(sql, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }

    }

    @Override
    public int delete(String groupId, String operator) {
        var in = Instant.now();
        var sql = """
            UPDATE room_groups
            SET deleted_at = now(),  update_dt = now(), update_user = ?
            WHERE id = ? AND deleted_at IS NULL
        """;
        var defaultOperator = Objects.requireNonNullElse(operator, DEFAULT_OPERATOR);
        var params = new Object[] { defaultOperator, groupId };
        try {
            return this.getJdbcTemplate().update(sql, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }

    }

    @Override
    public Optional<RoomGroup> get(String groupId) {
        var in = Instant.now();
        var sql = """
            SELECT
                id, type, code, name, sort_order, active
            FROM
                room_groups
            WHERE id = ?::uuid AND deleted_at IS NULL
            LIMIT 1
        """;
        var params = new Object[] { groupId };
        try {
            var rg = this.getJdbcTemplate().query(sql, (rs) -> {
                if (!rs.next()) {
                    return null;
                }
                var roomGroup = this.toRoomGroup(rs);
                return roomGroup;
            },   params);
            return Optional.ofNullable(rg);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public Optional<RoomGroupRow> getRow(String groupId) {
        var in = Instant.now();
        var sql = """
            SELECT
                id, type, code, name, sort_order, active,
                create_dt, create_user, update_dt, update_user, deleted_at
            FROM
                room_groups
            WHERE id = ? AND deleted_at IS NULL
            LIMIT 1
        """;
        var params = new Object[] { groupId };
        try {
            var rgr = this.getJdbcTemplate().query(sql, this.extractRoomGroupRowData,   params);
            return Optional.ofNullable(rgr);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<RoomGroup> list(RoomGroupType type, Boolean active) {
        var in = Instant.now();
        var sqlBuilder = new StringBuilder("SELECT id, type, code, name, sort_order, active FROM room_groups WHERE deleted_at IS NULL");
        List<Object> args = new ArrayList<>();
        appendFilter(sqlBuilder, args, type, active);
        sqlBuilder.append(" ORDER BY sort_order ASC, code ASC");
        var sql = sqlBuilder.toString();
        var params = args.toArray();
        try {
            var rgs = this.getJdbcTemplate().query(sql, (rs, i) -> toRoomGroup(rs), params);
            return rgs;
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<RoomGroupWithCountRow> listWithRoomCount(RoomGroupType type, Boolean active) {
        var in = Instant.now();
        var sqlBuilder = new StringBuilder("SELECT rg.*, COUNT(r.id) AS room_count ");
        sqlBuilder.append(" FROM room_groups rg ");
        sqlBuilder.append(" LEFT JOIN rooms r ON r.group_id = rg.id ");
        sqlBuilder.append(" AND r.deleted_at IS NULL ");
        sqlBuilder.append(" AND r.status <> 2 ");
        sqlBuilder.append(" WHERE rg.deleted_at IS NULL ");
        List<Object> args = new ArrayList<>();
        appendFilter(sqlBuilder, args, type, active, "rg.");
        sqlBuilder.append(" GROUP BY rg.id, rg.type, rg.code, rg.name, rg.sort_order, rg.active, " +
                "         rg.create_dt, rg.create_user, rg.update_dt, rg.update_user, rg.deleted_at ");
        sqlBuilder.append(" ORDER BY rg.sort_order ASC, rg.code ASC ");
        var sql = sqlBuilder.toString();
        var params = args.toArray();
        try {
            return this.getJdbcTemplate().query(sql, (rs, i) -> {
                var row = new RoomGroupWithCountRow();
                row.setId(rs.getString("id"));
                row.setType(rs.getString("type"));
                row.setCode(rs.getString("code"));
                row.setName(rs.getString("name"));
                row.setSortOrder(rs.getInt("sort_order"));
                row.setActive(rs.getBoolean("active"));
                row.setCreateDt(toLocalDateTime(rs.getTimestamp("create_dt")));
                row.setCreateUser(rs.getString("create_user"));
                row.setUpdateDt(toLocalDateTime(rs.getTimestamp("update_dt")));
                row.setUpdateUser(rs.getString("update_user"));
                row.setDeletedAt(toLocalDateTime(rs.getTimestamp("deleted_at")));
                row.setRoomCount(rs.getLong("room_count"));
                return row;
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    @Override
    public List<RoomGroupDashboardRow> dashboard(RoomGroupType type, Boolean active) {
        var in = Instant.now();
        var sqlBuilder = new StringBuilder("SELECT rg.id, rg.type, rg.code, rg.name, rg.sort_order,");
        sqlBuilder.append(" COUNT(r.id) AS total, ");
        sqlBuilder.append(" COUNT(r.id) FILTER (WHERE r.status = 0) AS vacant,  ");
        sqlBuilder.append(" COUNT(r.id) FILTER (WHERE r.status = 1) AS reserved, ");
        sqlBuilder.append(" COUNT(r.id) FILTER (WHERE r.status = 2) AS occupied, ");
        sqlBuilder.append(" COUNT(r.id) FILTER (WHERE r.status = 3) AS cleaning ");
        sqlBuilder.append(" FROM room_groups rg ");
        sqlBuilder.append(" LEFT JOIN rooms r ON r.group_id = rg.id AND r.deleted_at IS NULL  ");
        sqlBuilder.append(" WHERE rg.deleted_at IS NULL ");
        List<Object> args = new ArrayList<>();
        appendFilter(sqlBuilder, args, type, active, "rg.");
        sqlBuilder.append(" GROUP BY rg.id, rg.type, rg.code, rg.name, rg.sort_order ");
        sqlBuilder.append(" ORDER BY rg.sort_order ASC, rg.code ASC");
        var sql = sqlBuilder.toString();
        var params = args.toArray();
        try {
            return this.getJdbcTemplate().query(sql, (rs, i) -> {
                var row = new RoomGroupDashboardRow();
                row.setGroupId(rs.getString("id"));
                row.setType(rs.getString("type"));
                row.setCode(rs.getString("code"));
                row.setName(rs.getString("name"));
                row.setSortOrder(rs.getInt("sort_order"));
                row.setTotal(rs.getInt("total"));
                row.setVacant(rs.getInt("vacant"));
                row.setReserved(rs.getInt("reserved"));
                row.setOccupied(rs.getInt("occupied"));
                row.setCleaning(rs.getInt("cleaning"));
                return row;
            }, params);
        } catch (Exception ex) {
            SqlLoggerUtil.logSqlException(sql, params, ex);
            throw this.errorService.createHotelerException(ErrorCodeConstants.SQL_ERROR_CODE, new Object[] { sql }, ex);
        } finally {
            SqlLoggerUtil.logSql(sql, params, Duration.between(in, Instant.now()));
        }
    }

    private RowMapper<RoomGroupRow> toRoomGroupRowMapper = (rs, rowNum) -> {
        var row = this.toRoomGroupRow(rs);
        return row;
    };

    private ResultSetExtractor<RoomGroupRow> extractRoomGroupRowData = (rs)  -> {
        if (!rs.next()) {
            return null;
        }
        var row = this.toRoomGroupRow(rs);
        return row;
    };

    private RoomGroupRow toRoomGroupRow(ResultSet rs) throws SQLException {
        var row = new RoomGroupRow();
        row.setId(rs.getString("id"));
        row.setType(rs.getString("type"));
        row.setCode(rs.getString("code"));
        row.setName(rs.getString("name"));
        row.setSortOrder(rs.getInt("sort_order"));
        row.setActive(rs.getBoolean("active"));
        row.setCreateDt(toLocalDateTime(rs.getTimestamp("create_dt")));
        row.setCreateUser(rs.getString("create_user"));
        row.setUpdateDt(toLocalDateTime(rs.getTimestamp("update_dt")));
        row.setUpdateUser(rs.getString("update_user"));
        row.setDeletedAt(toLocalDateTime(rs.getTimestamp("deleted_at")));
        return row;
    }

    private RoomGroup toRoomGroup(ResultSet rs) throws SQLException {
        var roomGroup = new RoomGroup();
        roomGroup.setId(rs.getString("id"));
        roomGroup.setType(RoomGroupConverter.toEnum(rs.getString("type")));
        roomGroup.setCode(rs.getString("code"));
        roomGroup.setName(rs.getString("name"));
        roomGroup.setSortOrder(rs.getInt("sort_order"));
        roomGroup.setActive(rs.getBoolean("active"));
        return roomGroup;
    }

    private void appendFilter(StringBuilder sql, List<Object> args,
                              RoomGroupType type, Boolean active) {
        appendFilter(sql, args, type, active, "");
    }

    private void appendFilter(StringBuilder sql, List<Object> args,
                              RoomGroupType type, Boolean active, String alias) {
        if (type != null) {
            sql.append(" AND ").append(alias).append("type = ?");
            args.add(RoomGroupConverter.toDbValue(type));
        }
        if (active != null) {
            sql.append(" AND ").append(alias).append("active = ?");
            args.add(active);
        }
    }

    private LocalDateTime toLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

    public RoomGroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
