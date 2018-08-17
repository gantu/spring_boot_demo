package com.backend.tasks.service.user.impl;

import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.repository.User;
import com.backend.tasks.repository.UserRepository;
import com.backend.tasks.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<User> getAllUsersOfOrganization(Long orgId) {
        return userRepository.findByOrganizationId(orgId);
    }

    @Override
    public User getUserOfOrganization(Long orgId, Long userId) {
        return userRepository.findByOrganizationIdAndId(orgId,userId);
    }

    @Override
    public User saveUserIntoOrganization(Long orgId, User user) {
        Organization organization=organizationRepository.findById(orgId).get();
        user.setOrganization(organization);
        return userRepository.save(user);
    }

    @Override
    public void deleteUserOnOrganization(Long orgId, Long userId) {
        User user=userRepository.findByOrganizationIdAndId(orgId,userId);
        userRepository.delete(user);
    }


}
