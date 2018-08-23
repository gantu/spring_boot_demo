package com.backend.tasks.service.org.impl;

import com.backend.tasks.Application;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;
import static org.mockito.Mockito.when;

/**
 * Implement tests for UserServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrganizationServiceImplTest {

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Mock
    private OrganizationRepository organizationRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void whenValidID_thenOrganizationShouldBeFound() {


        Long orgId=1L;
        Organization organization=new Organization();
        organization.setId(orgId);
        organization.setName("Org1");
        when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));

        Organization found = organizationService.getOrganizationById(orgId);

        Assert.assertEquals(organization,found);
    }

    @Test
    public void whenFindAll_thenShouldRetunrAllOrganizations(){

        Organization org1 = new Organization();
        org1.setId(1l);
        org1.setName("org1");

        Organization org2 = new Organization();
        org2.setId(2l);
        org2.setName("org2");

        List<Organization> organizationList = Arrays.asList(org1,org1);
        when(organizationRepository.findAll()).thenReturn(organizationList);

        List<Organization> found = organizationService.getAllOrganization();

        Assert.assertEquals(found.size(),organizationList.size());

    }



}