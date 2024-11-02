package com.example.taskmanagementsystem.repo;


import com.example.taskmanagementsystem.domain.models.redis.JwtTokenRedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtTokenRepository extends CrudRepository<JwtTokenRedisHash, String> {
    Optional<JwtTokenRedisHash> getByToken(String token);
    void deleteAllByUserEmail(String userEmail);
    void deleteByToken(String token);
}
