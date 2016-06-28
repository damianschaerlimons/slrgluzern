package ch.slrg.mvp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Member.
 */
@Entity
@Table(name = "member")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "brevetnr", precision=10, scale=2)
    private BigDecimal brevetnr;

    @Column(name = "adress")
    private String adress;

    @Column(name = "plz")
    private Integer plz;

    @Column(name = "place")
    private String place;

    @Column(name = "aquateam")
    private Boolean aquateam;

    @Column(name = "skipper")
    private Boolean skipper;

    @Column(name = "boatdriver")
    private Boolean boatdriver;

    @Column(name = "rescue")
    private Boolean rescue;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @ManyToOne
    private Membertype membertype;

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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBrevetnr() {
        return brevetnr;
    }

    public void setBrevetnr(BigDecimal brevetnr) {
        this.brevetnr = brevetnr;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Integer getPlz() {
        return plz;
    }

    public void setPlz(Integer plz) {
        this.plz = plz;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Boolean isAquateam() {
        return aquateam;
    }

    public void setAquateam(Boolean aquateam) {
        this.aquateam = aquateam;
    }

    public Boolean isSkipper() {
        return skipper;
    }

    public void setSkipper(Boolean skipper) {
        this.skipper = skipper;
    }

    public Boolean isBoatdriver() {
        return boatdriver;
    }

    public void setBoatdriver(Boolean boatdriver) {
        this.boatdriver = boatdriver;
    }

    public Boolean isRescue() {
        return rescue;
    }

    public void setRescue(Boolean rescue) {
        this.rescue = rescue;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Membertype getMembertype() {
        return membertype;
    }

    public void setMembertype(Membertype membertype) {
        this.membertype = membertype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        if(member.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Member{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lastname='" + lastname + "'" +
            ", birthday='" + birthday + "'" +
            ", brevetnr='" + brevetnr + "'" +
            ", adress='" + adress + "'" +
            ", plz='" + plz + "'" +
            ", place='" + place + "'" +
            ", aquateam='" + aquateam + "'" +
            ", skipper='" + skipper + "'" +
            ", boatdriver='" + boatdriver + "'" +
            ", rescue='" + rescue + "'" +
            ", phone='" + phone + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
