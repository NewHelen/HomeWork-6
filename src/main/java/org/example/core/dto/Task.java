package org.example.core.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Task {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}
