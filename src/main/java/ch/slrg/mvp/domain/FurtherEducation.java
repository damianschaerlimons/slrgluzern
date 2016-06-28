package ch.slrg.mvp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FurtherEducation.
 */
@Entity
@Table(name = "further_education")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FurtherEducation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "niveau")
    private String niveau;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Member teacher;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Member getTeacher() {
        return teacher;
    }

    public void setTeacher(Member member) {
        this.teacher = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FurtherEducation furtherEducation = (FurtherEducation) o;
        if(furtherEducation.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, furtherEducation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FurtherEducation{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", niveau='" + niveau + "'" +
            ", date='" + date + "'" +
            '}';
    }
}
