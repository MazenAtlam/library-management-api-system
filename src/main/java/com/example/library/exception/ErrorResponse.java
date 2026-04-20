package com.example.library.exception;

import java.time.LocalDateTime;

/**
 * Standardized error response payload structure.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
