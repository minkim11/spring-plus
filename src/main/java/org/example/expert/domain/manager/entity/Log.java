package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "log")
public class Log extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long todoId;
    private Long userId;
    private Long managerId;

    public Log(Long todoId, Long userId, Long managerId) {
        this.todoId = todoId;
        this.userId = userId;
        this.managerId = managerId;
    }
}
