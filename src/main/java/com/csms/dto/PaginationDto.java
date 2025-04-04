package com.csms.dto;

import com.csms.utils.enums.SortOrder;
import lombok.Getter;

@Getter
public class PaginationDto {
    int page=0;
    int limit=5;
    String sortBy="createdAt";
    SortOrder sortOrder=SortOrder.DESC;
}