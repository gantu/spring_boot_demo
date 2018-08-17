package com.backend.tasks.service.org;

import com.backend.tasks.repository.Organization;

import java.util.List;

public interface OrganizationService {

    Organization getOrganizationById(Long id);

    List<Organization> getAllOrganization();
    void deleteOrganization(Long id);
    Organization saveOrganization(Organization organization);
    Boolean organizationExists(Long id);
}
