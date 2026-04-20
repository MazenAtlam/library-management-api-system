package com.example.library.controller;

import com.example.library.dto.MemberRequestDTO;
import com.example.library.dto.MemberResponseDTO;
import com.example.library.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller layer for Member endpoints.
 * Base path: /api/members
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * GET /api/members
     * Returns a paginated list of all members.
     */
    @GetMapping
    public ResponseEntity<Page<MemberResponseDTO>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    /**
     * GET /api/members/{id}
     * Returns a single member by ID.
     * Responds with 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.getMemberById(id));
    }

    /**
     * GET /api/members/search?name=...
     * Returns a paginated list of members whose name matches the query.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<MemberResponseDTO>> searchMembers(
            @RequestParam String name,
            Pageable pageable) {
        return ResponseEntity.ok(memberService.searchByName(name, pageable));
    }

    /**
     * POST /api/members
     * Registers a new member. membershipDate is auto-set on creation.
     * Responds with 409 if the email is already in use.
     */
    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO dto) {
        MemberResponseDTO created = memberService.createMember(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT /api/members/{id}
     * Updates an existing member's information.
     * Responds with 404 if not found, 409 if the new email is already taken.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> updateMember(
            @PathVariable Long id,
            @RequestBody MemberRequestDTO dto) {
        return ResponseEntity.ok(memberService.updateMember(id, dto));
    }

    /**
     * DELETE /api/members/{id}
     * Deletes a member by ID.
     * Responds with 204 No Content on success, 404 if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
