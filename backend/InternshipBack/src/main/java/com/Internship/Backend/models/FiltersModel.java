package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltersModel {

    private Date start_date;
    private Date end_date;
    private int segment;
    private int region;
    private int branch;
    private int guarantee_type;
    private String gtype;
    private int status;
    private String stype;
    private String limit_id;
    private Boolean commission;
    private Boolean exception;
    private int userId;
}
