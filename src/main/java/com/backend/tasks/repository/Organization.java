package com.backend.tasks.repository;

import javax.persistence.*;
import java.util.Set;

/**
 * Implement entity:
 * 1. Map to users
 * 2. Add equals and hashCode methods
 */
@Entity
@Table(name="organization")
public class Organization {
    @Id
    @Column(name="organization_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    /**
     * Map organization with users.
     * Use OneToMany association and map by organization field in User class.
     * Fetch lazy, cascade all
     */
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private Set<User> users;

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


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
