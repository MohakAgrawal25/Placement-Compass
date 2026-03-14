package com.placement.filterbackend.dto;

import lombok.Data;
import java.util.List;

@Data
public class BranchSelectionDTO {
    private List<Long> branchIds;
}