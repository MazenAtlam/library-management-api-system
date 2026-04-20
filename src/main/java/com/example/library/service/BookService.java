package com.example.library.service;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.entity.Author;
import com.example.library.entity.Book;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for Book operations.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Retrieves all books with pagination.
     * Calls the optimized findAll method in the repository to prevent N+1 issues.
     */
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::toResponseDTO);
    }

    /**
     * Retrieves a book by its ID. Throws an exception if not found.
     * Marked as @Transactional to handle lazy loading of the Author entity.
     */
    @Transactional(readOnly = true)
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));
        return bookMapper.toResponseDTO(book);
    }

    /**
     * Creates a new book after verifying the author exists.
     */
    @Transactional
    public BookResponseDTO createBook(BookRequestDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author", dto.getAuthorId()));

        Book book = bookMapper.toEntity(dto);
        // Explicitly set the fully loaded author reference since MapStruct might just set the ID
        book.setAuthor(author);
        
        Book savedBook = bookRepository.save(book);
        return bookMapper.toResponseDTO(savedBook);
    }

    /**
     * Updates an existing book. Verifies the author if the authorId has changed.
     */
    @Transactional
    public BookResponseDTO updateBook(Long id, BookRequestDTO dto) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", id));

        existingBook.setTitle(dto.getTitle());
        existingBook.setIsbn(dto.getIsbn());
        existingBook.setGenre(dto.getGenre());
        existingBook.setPublishedYear(dto.getPublishedYear());

        // Update the author if the ID is different
        if (dto.getAuthorId() != null && !dto.getAuthorId().equals(existingBook.getAuthor().getId())) {
            Author newAuthor = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author", dto.getAuthorId()));
            existingBook.setAuthor(newAuthor);
        }

        Book updatedBook = bookRepository.save(existingBook);
        return bookMapper.toResponseDTO(updatedBook);
    }

    /**
     * Deletes a book by its ID. Throws an exception if not found.
     */
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book", id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Searches for books based on optional title, genre, and publishedYear.
     * Calls the optimized searchBooks method in the repository to prevent N+1 issues.
     */
    @Transactional(readOnly = true)
    public Page<BookResponseDTO> searchBooks(String title, String genre, Integer publishedYear, Pageable pageable) {
        return bookRepository.searchBooks(title, genre, publishedYear, pageable)
                .map(bookMapper::toResponseDTO);
    }
}
