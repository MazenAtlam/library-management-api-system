package com.example.library.dto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AuthorRequestDTO {
    private String firstName;
    private String lastName;
    private String nationality;
    private LocalDate birthDate;
}