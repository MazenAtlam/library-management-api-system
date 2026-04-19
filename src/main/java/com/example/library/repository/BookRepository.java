package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository layer for Book entity.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Overrides findAll to fetch the author eagerly and fix the N+1 problem.
     */
    @Override
    @EntityGraph(attributePaths = {"author"})
    Page<Book> findAll(Pageable pageable);

    /**
     * Searches for books allowing partial match on title and exact matches on genre and published year.
     * Uses JOIN FETCH to fetch the author eagerly to resolve the N+1 problem.
     * A countQuery is provided to correctly support pagination with JOIN FETCH.
     */
    @Query(value = "SELECT b FROM Book b JOIN FETCH b.author " +
           "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) " +
           "AND (:genre IS NULL OR b.genre = CAST(:genre AS string)) " +
           "AND (:publishedYear IS NULL OR b.publishedYear = CAST(:publishedYear AS int))",
           countQuery = "SELECT count(b) FROM Book b " +
           "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) " +
           "AND (:genre IS NULL OR b.genre = CAST(:genre AS string)) " +
           "AND (:publishedYear IS NULL OR b.publishedYear = CAST(:publishedYear AS int))")
    Page<Book> searchBooks(@Param("title") String title,
                           @Param("genre") String genre,
                           @Param("publishedYear") Integer publishedYear,
                           Pageable pageable);
}
