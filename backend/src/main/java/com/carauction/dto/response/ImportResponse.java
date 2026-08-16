package com.carauction.dto.response;

import java.util.List;

public record ImportResponse(
        int read,
        int inserted,
        int updated,
        int skipped,
        List<String> errors
) {
}