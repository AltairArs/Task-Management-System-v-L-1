package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.domain.dto.requests.UserRegistrationRequest;
import com.example.taskmanagementsystem.enums.UserRoleEnum;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestUserController {
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

    void registryUser(String email) throws Exception {
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .email(email)
                .password("12345678")
                .confirmPassword("12345678")
                .build();
        mvc.perform(post("/api/v1/accounts/register/")
                .content(mapper.writeValueAsString(userRegistrationRequest))
                .contentType(MediaType.APPLICATION_JSON));
    }

    /**
     * Проверка, что у первого пользователя в системе роль ADMIN, в отличие от остальных
     * @throws Exception
     */
    @Test
    @Order(1)
    void twoRoles() throws Exception{
        registryUser("abc@gmail.com");
        registryUser("abcd@gmail.com");
        Assertions.assertEquals(userRepository.getByEmail("abc@gmail.com").get().getRole(), UserRoleEnum.ADMIN);
        Assertions.assertEquals(userRepository.getByEmail("abcd@gmail.com").get().getRole(), UserRoleEnum.USER);
    }
}
