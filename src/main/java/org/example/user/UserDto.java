package org.example.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class UserDto{
        Long id;
        String name;
        String username;
        String email;
        String address;
        String phone;
        String website;
        String company;
}
