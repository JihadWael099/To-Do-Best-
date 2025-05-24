package org.springboot.userservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
@Entity
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @NotBlank(message = "passord is requird")
    private String password;

    @NotBlank(message = "provide a valid username")
    private String username;

    @Email(message ="provide valid email")
    @NotBlank(message = "provide a valid email")
    private String email;


    @Column(nullable = false)
    private boolean enable = false;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
    private List<OTP> otp;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<JWT> jwt;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return  true;
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
        return this.enable;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Users(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public Users(int id, String password, String username, String email, boolean enable) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.enable = enable;
    }

    public Users(String password, String username, String email, boolean enable) {
        this.password = password;
        this.username = username;
        this.email = email;
        this.enable = enable;
    }

    public Users() {
    }
}
