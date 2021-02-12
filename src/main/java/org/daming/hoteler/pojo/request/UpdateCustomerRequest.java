package org.daming.hoteler.pojo.request;

import org.daming.hoteler.pojo.enums.Gender;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * update customer request body
 *
 * @author gming001
 * @create 2021-02-12 18:20
 **/
public class UpdateCustomerRequest implements Serializable {

    private static final long serialVersionUID = 6273140802741108457L;

    private long id;

    private String name;

    private Gender gender;

    private String cardId;

    private long phone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public UpdateCustomerRequest() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateCustomerRequest.class.getSimpleName() + "[", "]")
                .add("id='" + id + "'")
                .add("name='" + name + "'")
                .add("gender=" + gender)
                .add("cardId='" + cardId + "'")
                .add("phone=" + phone)
                .toString();
    }
}
