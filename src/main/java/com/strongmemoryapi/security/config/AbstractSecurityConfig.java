package com.strongmemoryapi.security.config;

import com.strongmemoryapi.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractSecurityConfig {

    @Autowired
    protected SecurityCommonConfig commonConfig;

    @Autowired
    protected JwtAuthenticationFilter filter;

}
