package com.sw.fd.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private int gno;
    private String gname;
    private LocalDateTime gdate;
}