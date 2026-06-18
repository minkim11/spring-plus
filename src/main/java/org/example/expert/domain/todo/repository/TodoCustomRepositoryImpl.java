package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.TodoSummaryResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoCustomRepositoryImpl implements TodoCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo result = jpaQueryFactory
                .selectFrom(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<TodoSummaryResponse> findAllByFilters(Pageable pageable, String title, String nickname, LocalDateTime startDate, LocalDateTime endDate) {

        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isBlank()) {
            builder.and(todo.title.contains(title));
        }

        if (nickname != null && !nickname.isBlank()) {
            builder.and(
                    JPAExpressions
                            .selectOne()
                            .from(manager)
                            .join(manager.user, user)
                            .where(
                                    manager.todo.eq(todo),
                                    user.nickname.contains(nickname)
                            )
                            .exists()
            );
        }

        if (startDate != null) {
            builder.and(todo.createdAt.goe(startDate));
        }

        if (endDate != null) {
            builder.and(todo.createdAt.loe(endDate));
        }

        List<TodoSummaryResponse> content = jpaQueryFactory
                .selectDistinct(Projections.constructor(
                        TodoSummaryResponse.class,
                        todo.title,
                        todo.managers.size(),
                        todo.comments.size(),
                        todo.createdAt
                        )
                )
                .from(todo)
                .where(builder)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(builder)
                .fetchOne();

        if (total == null) {
            total = 0L;
        }

        return new PageImpl<>(content, pageable, total);
    }
}
