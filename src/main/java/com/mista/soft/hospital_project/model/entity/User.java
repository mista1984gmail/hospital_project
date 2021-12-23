package com.mista.soft.hospital_project.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="User")
@Table(name = "user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id",
            updatable = false)
    private Integer id;

    @Column(name="first_name",
            nullable = false)
    private String firstName;

    @Column(name="last_name",
            nullable = false)
    private String lastName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="birthday")
    private LocalDate birthday;

    @Transient
    private Integer age;

    @Column(name="address",
            nullable = false)
    private String address;

    @Column(name="email",
            nullable = false)
    @Email
    private String email;

    @Column(name="telephone",
            nullable = false)
    private String telephone;

    @Column(name="name_user",
            nullable = false,
            length = 128)
    @Size(min=5, message = "At least 5 characters")
    private String username;

    @Column(name = "password",
            length = 128,
            nullable = false)
    @Size(min=5, message = "At least 5 characters")
    private String password;

    @Transient
    private String passwordConfirm;

    @Column
    private boolean active;

    @Column (name="activation_code")
    private String activationCode;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<HistorySick> historySicks = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getAge() {

        return Period.between(this.birthday,LocalDate.now()).getYears();
    }

    public void addHistory(HistorySick historySick){
        historySicks.add(historySick);
    }

}
