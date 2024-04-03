package org.example.core;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}
