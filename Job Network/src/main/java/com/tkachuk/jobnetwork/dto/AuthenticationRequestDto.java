package com.tkachuk.jobnetwork.dto;

import lombok.Data;

/**
 * DTO class for authentication request.
 *
 * @author Svitlana Tkachuk
 */

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}