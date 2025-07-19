package com.example.testtasknews.service;

import com.example.testtasknews.dto.response.PageResponseDto;
import org.springframework.data.domain.PageRequest;

/**
 * Generic service interface for CRUD operations.
 *
 * @param <C> DTO for create operations
 * @param <U> DTO for update operations
 * @param <V> DTO for view/response operations
 */
public interface Service<C, U, V> {

    /**
     * Saves a new entity.
     *
     * @param createRequestDto DTO containing data for creation
     */
    void save(C createRequestDto);

    /**
     * Retrieves all entities with pagination.
     *
     * @param pageRequest pagination and sorting information
     * @return paginated response DTO
     */
    PageResponseDto<V> findAll(PageRequest pageRequest);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id entity identifier
     * @return response DTO for the entity
     */
    V findById(Long id);

    /**
     * Deletes an entity by its ID.
     *
     * @param id entity identifier
     */
    void deleteById(Long id);

    /**
     * Updates an existing entity.
     *
     * @param updateRequestDto DTO containing updated data
     */
    void update(U updateRequestDto);
}
