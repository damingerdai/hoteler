package org.daming.hoteler.pojo.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.daming.hoteler.pojo.enums.Gender;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * create customer request
 *
 * @author gming001
 * @create 2020-12-25 22:15
 **/
@Schema(name = "创建客户请求")
public class CreateCustomerRequest implements Serializable {

    private static final long serialVersionUID = -7819607546063963266L;

    @Schema(name = "名字")
    private String name;

    private Gender gender;

    private String cardId;

    private long phone;

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

    public CreateCustomerRequest(String name, Gender gender, String cardId, long phone) {
        super();
        this.name = name;
        this.gender = gender;
        this.cardId = cardId;
        this.phone = phone;
    }

    public CreateCustomerRequest() {
        super();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CreateCustomerRequest.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("gender=" + gender)
                .add("cardId='" + cardId + "'")
                .add("phone='" + phone + "'")
                .toString();
    }
}
