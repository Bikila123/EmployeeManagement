package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedPositionRoleMappingModel {

    private int id;

    private String title;

    private int location;

    private int role;

    private int deleted_by;
    private Date deleted_date;
}
