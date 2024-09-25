package com.Internship.Backend.services;

import org.springframework.stereotype.Repository;

import com.Internship.Backend.models.EmployeeIdTracker;

public interface EmployeeIdTrackerRepository extends JpaRepository<EmployeeIdTracker, Integer> {
    EmployeeIdTracker findByYear(int year);
}
