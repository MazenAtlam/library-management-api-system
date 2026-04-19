package com.example.library.dto;
import lombok.Data;

@Data
public class BookResponseDTO {
    private Long id;
    private String title;
    private String isbn;
    private String genre;
    private Integer publishedYear;
    private AuthorResponseDTO author; // Nested DTO!
}