package com.example.expandapitesttask.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserRequest {
    private String username;
    private String password;
}
