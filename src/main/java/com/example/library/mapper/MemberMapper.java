package com.example.library.mapper;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberResponseDTO toResponseDTO(Member member);
    Member toEntity(MemberRequestDTO dto);
}