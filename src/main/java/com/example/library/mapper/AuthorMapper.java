package com.example.library.mapper;

import com.example.library.dto.AuthorRequestDTO;
import com.example.library.dto.AuthorResponseDTO;
import com.example.library.entity.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorResponseDTO toResponseDTO(Author author);
    Author toEntity(AuthorRequestDTO dto);
}