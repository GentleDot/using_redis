package net.gentledot.client_spring.common.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CacheProperty {
    private String name;
    private Integer timeToLive;

    private CacheProperty(String name, Integer timeToLive) {
        this.name = name;
        this.timeToLive = timeToLive;
    }

    public static CacheProperty of(String name, Integer timeToLive) {
        return new CacheProperty(name, timeToLive);
    }
}
