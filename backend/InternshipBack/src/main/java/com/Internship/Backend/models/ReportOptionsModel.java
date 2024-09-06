package com.Internship.Backend.models;

import lombok.Data;

import java.util.Date;

@Data
public class ReportOptionsModel {
    private int guaranteeType;
    private int collateralType;
    private String gType;
    private String cType;
    private String reportOptions;
    private String typeOptions;
    private Date daily;
    private Date start_date;
    private Date end_date;
    private int user_id;
    private int region;
    private int branch;
    private String limit_id;
}
