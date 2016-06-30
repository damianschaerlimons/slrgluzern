package ch.slrg.mvp.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the FurtherEducation entity.
 */
public class FurtherEducationDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private String niveau;

    private LocalDate date;


    private Long memberId;

    private String memberLastname;


    private String memberName;

    private Long teacherId;


    private String teacherName;

    private String teacherLastname;

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long memberId) {
        this.teacherId = memberId;
    }


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String memberName) {
        this.teacherName = memberName;
    }

    public String getMemberLastname() {
        return memberLastname;
    }

    public void setMemberLastname(String memberLastname) {
        this.memberLastname = memberLastname;
    }

    public String getTeacherLastname() {
        return teacherLastname;
    }

    public void setTeacherLastname(String teacherLastname) {
        this.teacherLastname = teacherLastname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FurtherEducationDTO furtherEducationDTO = (FurtherEducationDTO) o;

        if ( ! Objects.equals(id, furtherEducationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FurtherEducationDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", niveau='" + niveau + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
