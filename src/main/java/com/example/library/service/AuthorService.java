package com.example.library.service;

import com.example.library.dto.AuthorRequestDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.entity.Author;
import com.example.library.mapper.AuthorMapper;
import com.example.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<AuthorResponseDTO> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponseDTO)
                .toList();
    }

    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author not found"));
        return authorMapper.toResponseDTO(author);
    }

    public AuthorResponseDTO addNewAuthor(AuthorRequestDTO dto) {
        validateAuthor(dto);

        if (authorRepository.existsByFirstNameAndLastName(dto.getFirstName(), dto.getLastName())) {
            throw new IllegalStateException("Author already exists");
        }

        Author author = authorMapper.toEntity(dto);
        return authorMapper.toResponseDTO(authorRepository.save(author));
    }

    @Transactional
    public AuthorResponseDTO updateAuthorById(Long id, AuthorRequestDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author not found"));

        updateIfChanged(dto.getFirstName(), author.getFirstName(), author::setFirstName);
        updateIfChanged(dto.getLastName(), author.getLastName(), author::setLastName);
        updateIfChanged(dto.getNationality(), author.getNationality(), author::setNationality);

        if (dto.getBirthDate() != null && !Objects.equals(dto.getBirthDate(), author.getBirthDate())) {
            author.setBirthDate(dto.getBirthDate());
        }

        return authorMapper.toResponseDTO(author);
    }

    public void deleteAuthorById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new IllegalStateException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    private void validateAuthor(AuthorRequestDTO dto) {
        if (dto.getFirstName() == null || dto.getFirstName().isBlank() ||
                dto.getLastName() == null || dto.getLastName().isBlank()) {
            throw new IllegalArgumentException("First name and last name are required");
        }
    }

    private void updateIfChanged(String newValue, String currentValue, Consumer<String> updater) {
        if (!Objects.equals(newValue, currentValue) && newValue != null && !newValue.isBlank()) {
            updater.accept(newValue);
        }
    }
}
