package ch.slrg.mvp.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Appearances entity.
 */
public class AppearancesDTO implements Serializable {

    private Long id;

    private String name;

    private LocalDate valid;

    private Integer hours;


    private Long memberId;

    private String memberName;

    private String memberLastname;


    private Long appearancesTypeId;


    private String appearancesTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getValid() {
        return valid;
    }

    public void setValid(LocalDate valid) {
        this.valid = valid;
    }
    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getAppearancesTypeId() {
        return appearancesTypeId;
    }

    public void setAppearancesTypeId(Long appearancesTypeId) {
        this.appearancesTypeId = appearancesTypeId;
    }


    public String getAppearancesTypeName() {
        return appearancesTypeName;
    }

    public void setAppearancesTypeName(String appearancesTypeName) {
        this.appearancesTypeName = appearancesTypeName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberLastname() {
        return memberLastname;
    }

    public void setMemberLastname(String memberLastname) {
        this.memberLastname = memberLastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppearancesDTO appearancesDTO = (AppearancesDTO) o;

        if ( ! Objects.equals(id, appearancesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AppearancesDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", valid='" + valid + "'" +
            ", hours='" + hours + "'" +
            '}';
    }
}
