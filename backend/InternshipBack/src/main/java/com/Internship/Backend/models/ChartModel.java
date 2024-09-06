package com.Internship.Backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartModel {
   private String[] labels;
   private Integer[] data;
   private int[] revolvingData;
   private int[] onetimeData;
}
