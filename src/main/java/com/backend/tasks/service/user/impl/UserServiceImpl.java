package com.backend.tasks.service.user.impl;

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
    private UserRepository repository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<User> getAllUsersOfOrganization(Long orgId) {
        return repository.findByOrganizationId(orgId);
    }

    @Override
    public User getUserOfOrganization(Long orgId, Long userId) {

        return repository.findById(userId).map(user -> {
            if(organizationRepository.findById(orgId).get()!=user.getOrganization()){
                throw new RuntimeException("User does not belongs to Organization");
            }
            return user;
        }).orElseThrow(()->new RuntimeException("User with ID: "+ userId +" could not be found!"));

    }

    @Override
    public User saveUserIntoOrganization(Long orgId, User user) {
        return organizationRepository.findById(orgId).map(organization -> {
            user.setOrganization(organization);
            return repository.save(user);
        }).orElseThrow(()->new RuntimeException("Org with ID: "+orgId+" could not be found!"));

    }

    @Override
    public void deleteUserOnOrganization(Long orgId, Long userId) {
        User u=repository.findById(userId).get();
        if(organizationRepository.findById(orgId).get()!=u.getOrganization())
            throw new RuntimeException("User does not belongs to Organization");
        repository.delete(u);
    }


}
