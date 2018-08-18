package com.backend.tasks.controller;

import com.backend.tasks.repository.User;
import com.backend.tasks.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Implement create, read, update, delete  rest controller endpoints for user.
 * Map endpoints to /orgs/{orgId}/users path
 * 1. Post to /orgs/{orgId}/users endpoint should create and return user for organization with id=orgId. Response status should be 201.
 * 2. Put to /orgs/{orgId}/users/{userId} endpoint should update, save and return user with id=userId for organization with id=orgId.
 * 3. Get to /orgs/{orgId}/users/{userId} endpoint should fetch and return user with id=userId for organization with id=orgId.
 * 4. Delete to /orgs/{orgId}/users/{userId} endpoint should delete user with id=userId for organization with id=orgId. Response status should be 204.
 * 5. Get to /orgs/{orgId}/users endpoint should return list of all users for organization with id=orgId
 */
@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/orgs/{orgId}/users")
    public List<User> getAllUsersOfOrganization(@PathVariable(value = "orgId") Long orgId) {
        return service.getAllUsersOfOrganization(orgId);
    }

    @GetMapping("/orgs/{orgId}/users/{userId}")
    public User getUserOfOrganization(@PathVariable(value = "orgId") Long orgId,
                                      @PathVariable(value = "userId") Long userId) {
        return service.getUserOfOrganization(orgId, userId);
    }

    @PostMapping("/orgs/{orgId}/users")
    public User createOrganization(@PathVariable(value = "orgId") Long orgId,
                                   @Valid @RequestBody User user) {
        return service.saveUserIntoOrganization(orgId, user);
    }

    @DeleteMapping("/orgs/{orgId}/users/{userId}")
    public ResponseEntity<?> deleteUserOnOrganization(@PathVariable(value = "orgId") Long orgId,
                                                      @PathVariable(value = "userId") Long userId) {
        service.deleteUserOnOrganization(orgId, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/orgs/{orgId}/users/{userId}")
    public User saveUserOfOrganization(@PathVariable(value = "orgId") Long orgId,
                                       @PathVariable(value = "userId") Long userId,
                                       @Valid @RequestBody User user) {
        User u = service.getUserOfOrganization(orgId, userId);
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        return service.saveUserIntoOrganization(orgId, u);
    }
}
