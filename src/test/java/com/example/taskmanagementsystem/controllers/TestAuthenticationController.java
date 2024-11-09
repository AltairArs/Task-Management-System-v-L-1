package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.RefreshJwtTokenRequest;
import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.domain.dto.responses.JwtAuthenticationResponse;
import com.example.taskmanagementsystem.repo.UserRepository;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestAuthenticationController {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17-alpine");

    static RedisContainer redisContainer = new RedisContainer("redis:6.2.6");

    @BeforeAll
    static void beforeAll() {
        redisContainer.start();
        postgreSQLContainer.start();
    }

    @AfterAll
    static void afterAll() {
        redisContainer.stop();
        postgreSQLContainer.stop();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redisContainer::getRedisHost);
        registry.add("spring.data.redis.port", redisContainer::getRedisPort);

        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @AfterEach
    void setUp(){
        userRepository.deleteAll();
    }

    /**
     * Проверка регистрации
     * @throws Exception
     */
    @Test
    @Order(1)
    void testRegistry() throws Exception {
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .email("abc@gmail.com")
                .password("12345678")
                .confirmPassword("12345678")
                .build();
        mvc.perform(post("/api/v1/accounts/register/")
                .content(mapper.writeValueAsString(userRegistrationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(content().string(containsString("accessToken")))
                .andExpect(content().string(containsString("refreshToken")));
    }

    /**
     * Проверка, что нельзя зарегистрироваться, под одним и тем же Email
     * @throws Exception
     */
    @Test
    @Order(2)
    void testTwoRegistration() throws Exception {
        UserRegistrationRequest userRegistrationRequest1 = UserRegistrationRequest.builder()
                .email("abc@gmail.com")
                .password("12345678")
                .confirmPassword("12345678")
                .build();
        UserRegistrationRequest userRegistrationRequest2 = UserRegistrationRequest.builder()
                .email("abc@gmail.com")
                .password("12345678")
                .confirmPassword("12345678")
                .build();
        mvc.perform(post("/api/v1/accounts/register/")
                        .content(mapper.writeValueAsString(userRegistrationRequest1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(201))
                .andExpect(content().string(containsString("accessToken")))
                .andExpect(content().string(containsString("refreshToken")));
        mvc.perform(post("/api/v1/accounts/register/")
                        .content(mapper.writeValueAsString(userRegistrationRequest2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    /**
     * Проверка обновления токена
     * @throws Exception
     */
    @Test
    @Order(3)
    void testRefreshToken() throws Exception {
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .email("abc@gmail.com")
                .password("12345678")
                .confirmPassword("12345678")
                .build();
        MvcResult result = mvc.perform(post("/api/v1/accounts/register/")
                .content(mapper.writeValueAsString(userRegistrationRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        JwtAuthenticationResponse old = mapper.readValue(result.getResponse().getContentAsString(), JwtAuthenticationResponse.class);
        RefreshJwtTokenRequest refreshJwtTokenRequest = RefreshJwtTokenRequest.builder()
                .refreshToken(old.getRefreshToken())
                .build();
        mvc.perform(post("/api/v1/accounts/refresh-token/")
                .content(mapper.writeValueAsString(refreshJwtTokenRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
