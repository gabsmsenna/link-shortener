package dev.senna.adapter.in.web.dto.response;

import java.util.List;

public record ApiResponse<T>(
        List<T> data,
        String nextToken
) {
}
