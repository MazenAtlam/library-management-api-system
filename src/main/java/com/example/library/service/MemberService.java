package com.example.library.service;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.entity.Member;
import com.example.library.exception.DuplicateResourceException;
import com.example.library.exception.ResourceNotFoundException;
import com.example.library.mapper.MemberMapper;
import com.example.library.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service layer for Member operations.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    /**
     * Retrieves all members with pagination.
     */
    @Transactional(readOnly = true)
    public Page<MemberResponseDTO> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberMapper::toResponseDTO);
    }

    /**
     * Retrieves a single member by ID.
     * Throws ResourceNotFoundException if no member exists with the given ID.
     */
    @Transactional(readOnly = true)
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));
        return memberMapper.toResponseDTO(member);
    }

    /**
     * Registers a new member.
     * membershipDate is automatically set via @PrePersist on the entity.
     * Throws DuplicateResourceException if the email is already taken.
     */
    @Transactional
    public MemberResponseDTO createMember(MemberRequestDTO dto) {
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Member", "email", dto.getEmail());
        }

        Member member = memberMapper.toEntity(dto);
        return memberMapper.toResponseDTO(memberRepository.save(member));
    }

    /**
     * Updates an existing member's information.
     * Re-validates email uniqueness only when the email has actually changed.
     * Throws ResourceNotFoundException if no member exists with the given ID.
     * Throws DuplicateResourceException if the new email is already taken.
     */
    @Transactional
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO dto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", id));

        // Only check email uniqueness if the email has changed
        if (!existingMember.getEmail().equalsIgnoreCase(dto.getEmail()) &&
                memberRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Member", "email", dto.getEmail());
        }

        existingMember.setFirstName(dto.getFirstName());
        existingMember.setLastName(dto.getLastName());
        existingMember.setEmail(dto.getEmail());
        existingMember.setPhoneNumber(dto.getPhoneNumber());

        return memberMapper.toResponseDTO(memberRepository.save(existingMember));
    }

    /**
     * Deletes a member by ID.
     * Throws ResourceNotFoundException if no member exists with the given ID.
     */
    @Transactional
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member", id);
        }
        memberRepository.deleteById(id);
    }

    /**
     * Searches for members whose first or last name contains the given string (case-insensitive).
     */
    @Transactional(readOnly = true)
    public Page<MemberResponseDTO> searchByName(String name, Pageable pageable) {
        return memberRepository.searchByName(name, pageable)
                .map(memberMapper::toResponseDTO);
    }
}
