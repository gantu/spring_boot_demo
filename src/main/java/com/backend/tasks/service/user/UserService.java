package com.backend.tasks.service.user;

import com.backend.tasks.repository.User;

import java.util.List;

public interface UserService {


    List<User> getAllUsersOfOrganization(Long orgId);
    User getUserOfOrganization(Long orgId,Long userId);
    User saveUserIntoOrganization(Long orgId,User user);
    void deleteUserOnOrganization(Long orgId,Long userId);

}
