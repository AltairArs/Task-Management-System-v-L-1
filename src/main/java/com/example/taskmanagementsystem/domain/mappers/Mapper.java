package com.example.taskmanagementsystem.domain.mappers;

/**
 * Entity, Dto
 * @param <A>
 * @param <B>
 */
public interface Mapper<A, B> {
    B mapToDto(A a);
    A mapFromDto(B b);
}
