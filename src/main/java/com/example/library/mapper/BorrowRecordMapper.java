package com.example.library.mapper;

import com.example.library.dto.BorrowRecordRequestDTO;
import com.example.library.dto.BorrowRecordResponseDTO;
import com.example.library.entity.BorrowRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookMapper.class, MemberMapper.class}, unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface BorrowRecordMapper {
    BorrowRecordResponseDTO toResponseDTO(BorrowRecord record);

    // Maps the incoming IDs to the nested entity IDs
    @Mapping(target = "book.id", source = "bookId")
    @Mapping(target = "member.id", source = "memberId")
    BorrowRecord toEntity(BorrowRecordRequestDTO dto);
}