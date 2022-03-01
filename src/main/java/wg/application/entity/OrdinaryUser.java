// package wg.application.entity;
//
// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
//
// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;
//
// /**
//  * Created by cxr1205628673 on 2020/3/16.
//  */
//
// @Document("user")
// public class OrdinaryUser extends User implements UserDetails, Serializable {
//     //User要继承 UserDetails接口，实现是否可用、上锁、getAuthorties等方法
//     @Id
//     private String id;
//     private String username;
//     private String password;
//     private List<Role> roles;
//
//     public List<Role> getRoles() {
//         return roles;
//     }
//
//     public void setRoles(List<Role> roles) {
//         this.roles = roles;
//     }
//
//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() {
//         List<SimpleGrantedAuthority> auth = new ArrayList<>();
//
//         for (Role role : roles) {
//             auth.add(new SimpleGrantedAuthority(role.getRoleName()));
//         }
//         return auth;
//     }
//
//     @Override
//     public boolean isAccountNonExpired() {
//         return true;
//     }
//
//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }
//
//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;
//     }
//
//     @Override
//     public boolean isEnabled() {
//         return true;
//     }
//
//     public String getUsername() {
//         return username;
//     }
//
//     public void setUsername(String username) {
//         this.username = username;
//     }
//
//     public String getPassword() {
//         return password;
//     }
//
//     public void setPassword(String password) {
//         this.password = password;
//     }
// }