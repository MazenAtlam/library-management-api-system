package com.example.library.controller;

import com.example.library.dto.BorrowRecordRequestDto;
import com.example.library.dto.BorrowRecordResponseDto;
import com.example.library.service.BorrowRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-records") // [cite: 52]
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @PostMapping // [cite: 53]
    public ResponseEntity<BorrowRecordResponseDto> borrowBook(@Valid @RequestBody BorrowRecordRequestDto request) {
        return new ResponseEntity<>(borrowRecordService.borrowBook(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return") // [cite: 53]
    public ResponseEntity<BorrowRecordResponseDto> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(borrowRecordService.returnBook(id));
    }

    @GetMapping("/member/{memberId}") // [cite: 53]
    public ResponseEntity<List<BorrowRecordResponseDto>> getRecordsByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(borrowRecordService.getRecordsByMember(memberId));
    }

    @GetMapping("/active") // [cite: 53]
    public ResponseEntity<List<BorrowRecordResponseDto>> getActiveBorrowRecords() {
        return ResponseEntity.ok(borrowRecordService.getActiveBorrowRecords());
    }
}