package com.backend.tasks.controller;

import com.backend.tasks.repository.Organization;
import com.backend.tasks.service.org.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Implement create, read, update, delete  rest controller endpoints for organization.
 * Map endpoints to /orgs path.
 * 1. Post to /orgs endpoint should create and return organization. Response status should be 201.
 * 2. Put to /orgs/{orgId} endpoint should update, save and return organization with id=orgId.
 * 3. Get to /orgs/{orgId} endpoint should fetch and return organization with id=orgId.
 * 4. Delete to /orgs/{orgId} endpoint should delete organization with id=orgId. Response status should be 204.
 * 5. Get to /orgs endpoint should return list of all organizations
 */
@RestController
public class OrganizationController {

    @Autowired
    private OrganizationService service;

    @GetMapping("/orgs")
    @ResponseStatus(HttpStatus.OK)
    public List<Organization> getAllOrganizations(){
        return service.getAllOrganization();
    }

    @GetMapping("/orgs/{orgId}")
    public Organization getOrganization(@PathVariable(value = "orgId") Long orgId){
        return service.getOrganizationById(orgId);
    }

    @PostMapping("/orgs")
    ResponseEntity<?> createOrganization(@Valid @RequestBody Organization organization){
        Organization org1 = service.saveOrganization(organization);
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.status(201).headers(responseHeaders).body(org1);
    }

    @PutMapping("/orgs/{orgId}")
    public Organization updateOrganization(@PathVariable(value="orgId") Long orgId,
                                           @Valid @RequestBody Organization organization){
        Organization org = service.getOrganizationById(orgId);
        org.setUsers(organization.getUsers());
        org.setName(organization.getName());
        return service.saveOrganization(org);
    }

    @DeleteMapping("/orgs/{orgId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable(value="orgId") Long orgId){
        if(!service.organizationExists(orgId))
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        service.deleteOrganization(orgId);
        return ResponseEntity.status(204).build();
    }
}
