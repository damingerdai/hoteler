package org.daming.hoteler.pojo;

import org.daming.hoteler.pojo.enums.Gender;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * customer
 *
 * @author gming001
 * @create 2020-12-25 15:21
 **/
public class Customer implements Serializable {

    private static final long serialVersionUID = -6886394623640158367L;

    private long id;

    private String name;

    private Gender gender;

    private String cardId;

    private long phone;

    public long getId() {
        return id;
    }

    public Customer setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Customer setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public Customer setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public long getPhone() {
        return phone;
    }

    public Customer setPhone(long phone) {
        this.phone = phone;
        return this;
    }

    public Customer(long id, String name, Gender gender, String cardId, long phone) {
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.cardId = cardId;
        this.phone = phone;
    }

    public Customer() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("gender=" + gender)
                .add("cardId='" + cardId + "'")
                .add("phone='" + phone + "'")
                .toString();
    }
}
