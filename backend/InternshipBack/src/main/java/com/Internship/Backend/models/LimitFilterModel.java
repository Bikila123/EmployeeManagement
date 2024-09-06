package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LimitFilterModel {
    private String limit;
    private int userId;
    private int regionId;
    private String toExpire;
    private String expired;
}
