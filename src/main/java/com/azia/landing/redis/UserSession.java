package com.azia.landing.redis;

import com.azia.landing.entity.User;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash(value = "userSession", timeToLive = 60 * 60 * 24 * 3)
public class UserSession {
    @Id
    private String id;

    private User value;
}

