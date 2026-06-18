package org.example.expert.domain.todo.dto.response;

import java.time.LocalDateTime;

public record TodoSummaryResponse(
        String title,
        int managerCount,
        int commentCount,
        LocalDateTime createdAt
) {
}
