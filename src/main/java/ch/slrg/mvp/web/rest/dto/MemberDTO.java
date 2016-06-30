package ch.slrg.mvp.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Member entity.
 */
public class MemberDTO implements Serializable {

    private Long id;

    private String name;

    private String lastname;

    private LocalDate birthday;

    private BigDecimal brevetnr;

    private String adress;

    private Integer plz;

    private String place;

    private Boolean aquateam;

    private Boolean skipper;

    private Boolean boatdriver;

    private Boolean rescue;

    private String phone;

    private String email;

    private Boolean ownboat;

    private String skipperlevel;


    private Long membertypeId;
    

    private String membertypeName;

    private Long sectionId;
    

    private String sectionName;

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
    public Boolean getAquateam() {
        return aquateam;
    }

    public void setAquateam(Boolean aquateam) {
        this.aquateam = aquateam;
    }
    public Boolean getSkipper() {
        return skipper;
    }

    public void setSkipper(Boolean skipper) {
        this.skipper = skipper;
    }
    public Boolean getBoatdriver() {
        return boatdriver;
    }

    public void setBoatdriver(Boolean boatdriver) {
        this.boatdriver = boatdriver;
    }
    public Boolean getRescue() {
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
    public Boolean getOwnboat() {
        return ownboat;
    }

    public void setOwnboat(Boolean ownboat) {
        this.ownboat = ownboat;
    }
    public String getSkipperlevel() {
        return skipperlevel;
    }

    public void setSkipperlevel(String skipperlevel) {
        this.skipperlevel = skipperlevel;
    }

    public Long getMembertypeId() {
        return membertypeId;
    }

    public void setMembertypeId(Long membertypeId) {
        this.membertypeId = membertypeId;
    }


    public String getMembertypeName() {
        return membertypeName;
    }

    public void setMembertypeName(String membertypeName) {
        this.membertypeName = membertypeName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }


    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MemberDTO memberDTO = (MemberDTO) o;

        if ( ! Objects.equals(id, memberDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MemberDTO{" +
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
            ", ownboat='" + ownboat + "'" +
            ", skipperlevel='" + skipperlevel + "'" +
            '}';
    }
}
