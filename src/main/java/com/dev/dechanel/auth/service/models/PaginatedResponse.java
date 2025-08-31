package com.dev.dechanel.auth.service.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private List<T> items;
    private int totalPages;
    private long totalElements;
    private int currentPage;
    private int pageSize;
    private boolean first;
    private boolean last;
    private boolean hasNext;
    private boolean hasPrevious;
    private long numberOfElements;
}

