package com.example.library.controller;

import com.example.library.dto.BorrowRecordRequestDTO;
import com.example.library.dto.BorrowRecordResponseDTO;
import com.example.library.service.BorrowRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-records")
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowRecordResponseDTO> borrowBook(@Valid @RequestBody BorrowRecordRequestDTO request) {
        return new ResponseEntity<>(borrowRecordService.borrowBook(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowRecordResponseDTO> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<BorrowRecordResponseDTO>> getRecordsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowRecordService.getRecordsByMember(memberId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<BorrowRecordResponseDTO>> getActiveBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getActiveBorrowRecords());
    }
}