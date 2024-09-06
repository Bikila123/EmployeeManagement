package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionModel {
    private Long id;
    private String name;
    private String code;
    private boolean status;
    private Long user_id;
}
