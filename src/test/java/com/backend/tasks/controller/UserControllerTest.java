package com.backend.tasks.controller;

import com.backend.tasks.Application;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.repository.User;
import com.backend.tasks.service.org.OrganizationService;
import com.backend.tasks.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
/**
 * Implement tests for UserController
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @MockBean
    private OrganizationService orgService;

    @Test
    public void givenOrganizationsID_whenGetUsersOfOrganization_thenReturnJsonArray()
            throws Exception {

        Organization org1 = new Organization();
        org1.setName("org1");
        org1.setId(1L);

        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("qwerty");
        user1.setOrganization(org1);
        User user2 = new User();
        user1.setUsername("username2");
        user1.setPassword("qwerty");
        user1.setOrganization(org1);

        List<User> allUsersOfOrganization = Arrays.asList(user1,user2);

        given(service.getAllUsersOfOrganization(1L)).willReturn(allUsersOfOrganization);


        mvc.perform(get("/orgs/1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void givenOrganizationsIDandUserId_shouldReturnUser()
            throws Exception {

        Organization org1 = new Organization();
        org1.setName("org1");
        org1.setId(1L);

        User user1 = new User();
        user1.setUsername("username1");
        user1.setPassword("qwerty");
        user1.setOrganization(org1);
        user1.setId(1L);
        User user2 = new User();
        user2.setUsername("username2");
        user2.setPassword("qwerty");
        user2.setOrganization(org1);
        user2.setId(2L);

        List<User> allUsersOfOrganization = Arrays.asList(user1,user2);

        given(service.getUserOfOrganization(1L,1L)).willReturn(user1);


        mvc.perform(get("/orgs/1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username",is("username1")));
    }


}