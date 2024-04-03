package org.example.core.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Comment {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
