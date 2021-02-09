package org.daming.hoteler.repository.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.daming.hoteler.pojo.Customer;

/**
 * customer mapper
 *
 * @author gming001
 * @create 2020-12-25 15:40
 **/
@Mapper
public interface CustomerMapper {

    /**
     * @deprecated please use {@link org.daming.hoteler.repository.jdbc.ICustomerDao#create(Customer)}
     */
    @Deprecated
    @Insert("insert into customers (id, name, gender, card_id, phone, create_dt, create_user, update_dt, update_user) values (#{id}, #{name}, #{gender}, #{cardId}, #{phone}, statement_timestamp(), 'system', statement_timestamp(), 'system')")
    void create(Customer customer);
}
