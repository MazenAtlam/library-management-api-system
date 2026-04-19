package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Searches for books allowing partial match on title and exact matches on genre and published year.
     * Optional parameters are supported by checking if they are NULL.
     */
    @Query("SELECT b FROM Book b " +
           "WHERE (:title IS NULL OR LOWER(b.title) LIKE LOWER(CONCAT('%', CAST(:title AS string), '%'))) " +
           "AND (:genre IS NULL OR b.genre = CAST(:genre AS string)) " +
           "AND (:publishedYear IS NULL OR b.publishedYear = CAST(:publishedYear AS int))")
    Page<Book> searchBooks(@Param("title") String title,
                           @Param("genre") String genre,
                           @Param("publishedYear") Integer publishedYear,
                           Pageable pageable);
}
