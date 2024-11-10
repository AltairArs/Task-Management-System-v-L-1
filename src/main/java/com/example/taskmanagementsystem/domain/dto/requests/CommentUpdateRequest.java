package com.example.taskmanagementsystem.domain.dto.requests;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentUpdateRequest {
    String content;
}
