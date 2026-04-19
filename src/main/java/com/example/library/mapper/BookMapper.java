package com.example.library.mapper;

import com.example.library.dto.BookRequestDTO;
import com.example.library.dto.BookResponseDTO;
import com.example.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BookMapper {
    BookResponseDTO toResponseDTO(Book book);

    // Maps the incoming authorId to the nested Author entity's ID
    @Mapping(target = "author.id", source = "authorId")
    Book toEntity(BookRequestDTO dto);
}