package ch.slrg.mvp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Appearances.
 */
@Entity
@Table(name = "appearances")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appearances implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "valid")
    private LocalDate valid;

    @Column(name = "hours")
    private Integer hours;

    @ManyToOne
    private Member member;

    @ManyToOne
    private AppearancesType appearancesType;

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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public AppearancesType getAppearancesType() {
        return appearancesType;
    }

    public void setAppearancesType(AppearancesType appearancesType) {
        this.appearancesType = appearancesType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appearances appearances = (Appearances) o;
        if(appearances.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, appearances.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appearances{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", valid='" + valid + "'" +
            ", hours='" + hours + "'" +
            '}';
    }
}
