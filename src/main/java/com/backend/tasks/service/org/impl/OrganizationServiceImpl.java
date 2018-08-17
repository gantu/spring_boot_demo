package com.backend.tasks.service.org.impl;

import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.service.org.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository repository;

    @Override
    public List<Organization> getAllOrganization() {
        return repository.findAll();
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Organization saveOrganization(Organization organization) {
        return repository.save(organization);
    }


    @Override
    public void deleteOrganization(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Boolean organizationExists(Long id){
        return repository.existsById(id);
    }

}
