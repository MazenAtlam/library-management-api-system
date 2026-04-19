package com.example.library.dto;
import lombok.Data;

@Data
public class BorrowRecordRequestDTO {
    private Long bookId;
    private Long memberId;
}