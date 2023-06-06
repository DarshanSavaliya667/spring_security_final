package com.usersecurityApp.user_security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.usersecurityApp.user_security.controller.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;


@Setter
@Getter
@Entity
@Table(name = "security_users")
@NoArgsConstructor
@AllArgsConstructor

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE  extends  Auditable<Long> extends  Auditable<Long>
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Boolean isAdmin;

    private Boolean isSuperAdmin;





}
