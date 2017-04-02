package dropwizard.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by allen on 16/8/15.
 */
public class UserPoJo {

    private String userId;
    private String userName;
    private String age;

    private List<Attr> attr;

    public UserPoJo() {
    }

    public UserPoJo(String userId, String userName, String age) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }

    @JsonProperty
    public String getUserId() {
        return userId;
    }

    @JsonProperty
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty
    public String getAge() {
        return age;
    }

    @JsonProperty
    public void setAge(String age) {
        this.age = age;
    }

    @JsonProperty
    public List<Attr> getAttr() {
        return attr;
    }

    @JsonProperty
    public void setAttr(List<Attr> attr) {
        this.attr = attr;
    }

    public static class Attr {
        @JsonProperty
        private String key;
        @JsonProperty
        private String value;

        public Attr(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

}