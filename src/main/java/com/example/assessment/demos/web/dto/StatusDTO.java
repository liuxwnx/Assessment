package com.example.assessment.demos.web.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {

    private String status;
    // 驳回理由
    private String rejectReason;
}
