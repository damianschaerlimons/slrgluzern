package ch.slrg.mvp.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Education entity.
 */
public class EducationDTO implements Serializable {

    private Long id;

    private String note;

    private LocalDate valid;


    private Long memberId;

    private String memberName;

    private String memberLastname;


    private Long educationtypeId;


    private String educationtypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public LocalDate getValid() {
        return valid;
    }

    public void setValid(LocalDate valid) {
        this.valid = valid;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getEducationtypeId() {
        return educationtypeId;
    }

    public void setEducationtypeId(Long educationtypeId) {
        this.educationtypeId = educationtypeId;
    }


    public String getEducationtypeName() {
        return educationtypeName;
    }

    public void setEducationtypeName(String educationtypeName) {
        this.educationtypeName = educationtypeName;
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

        EducationDTO educationDTO = (EducationDTO) o;

        if ( ! Objects.equals(id, educationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EducationDTO{" +
            "id=" + id +
            ", note='" + note + "'" +
            ", valid='" + valid + "'" +
            '}';
    }
}
