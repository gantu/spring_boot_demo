package com.backend.tasks.controller;

import com.backend.tasks.Application;
import com.backend.tasks.repository.Organization;
import com.backend.tasks.service.org.OrganizationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implement tests for OrganizationController
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrganizationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrganizationService service;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void givenOrganizations_whenGetOrganizations_thenReturnJsonArray()
            throws Exception {

        Organization org1 = new Organization();
        org1.setName("org1");
        org1.setId(1L);

        List<Organization> allOrganizations = Arrays.asList(org1);

        given(service.getAllOrganization()).willReturn(allOrganizations);

        mvc.perform(get("/orgs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void givenOrganizations_whenGetOrganization_thenReturnJsonObject()
            throws Exception {
        Organization org1 = new Organization();
        org1.setName("org1");
        org1.setId(1L);

        given(service.getOrganizationById(1L)).willReturn(org1);


        mvc.perform(get("/orgs/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'name':'org1','users':null}"));

    }

    @Test
    public void givenValidOrganization_shouldCreateOrganization()
            throws Exception {
        Organization organization = new Organization();
        organization.setName("Org1");
        organization.setId(1L);
        String organizationJson = json(organization);

        mvc.perform(post("/orgs")
                .content(organizationJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    protected String json(Object o)
            throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}