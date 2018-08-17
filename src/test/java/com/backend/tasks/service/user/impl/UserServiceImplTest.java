package com.backend.tasks.service.user.impl;

import com.backend.tasks.Application;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.OrganizationRepository;
import com.backend.tasks.repository.User;
import com.backend.tasks.repository.UserRepository;
import com.backend.tasks.service.org.OrganizationService;
import com.backend.tasks.service.org.impl.OrganizationServiceImpl;
import com.backend.tasks.service.user.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Implement tests for UserServiceImpl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrganizationRepository organizationRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenValidOrgIDisGiven_shouldReturnAllUsersOfOrganization(){

        Long orgId=1L;
        Organization organization=new Organization();
        organization.setId(orgId);
        organization.setName("Org1");
      //  when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));

        User user1=  new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("qwerty");
        user1.setOrganization(organization);

        User user2=  new User();
        user2.setId(2L);
        user2.setUsername("user1");
        user2.setPassword("qwerty");
        user2.setOrganization(organization);

        List<User> userList= Arrays.asList(user1,user2);
        when(userRepository.findByOrganizationId(orgId)).thenReturn(userList);

        List<User> found = userService.getAllUsersOfOrganization(orgId);
        Assert.assertEquals(userList.size(),found.size());
        Assert.assertEquals(found.get(0).getUsername(),user1.getUsername());



    }

    @Test
    public void whenValidOrgIdAndUserIdGiven_shouldReturnSpecificUser(){
        Long orgId=1L;
        Organization organization=new Organization();
        organization.setId(orgId);
        organization.setName("Org1");

        User user1=  new User();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setPassword("qwerty");
        user1.setOrganization(organization);

        User user2=  new User();
        user2.setId(2L);
        user2.setUsername("user1");
        user2.setPassword("qwerty");
        user2.setOrganization(organization);


        when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));
        when(userRepository.findById(user1.getId())).thenReturn(Optional.of(user1));

        User found = userService.getUserOfOrganization(orgId,user1.getId());
        Assert.assertEquals(user1.getUsername(),found.getUsername());

    }


}