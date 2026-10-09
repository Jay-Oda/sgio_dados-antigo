package cv.pn.processmanagement.commons;

import javax.persistence.*;
@MappedSuperclass
public class CommonsParametrizationAttributes extends CommonsAttributes{
    @Column(name = "description")
    private String description;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "sequence", nullable = false)
    private int order;
    @Column(name = "self_code")
    private String selfCode;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSelfCode() {
        return selfCode;
    }

    public void setSelfCode(String selfCode) {
        this.selfCode = selfCode;
    }


}
