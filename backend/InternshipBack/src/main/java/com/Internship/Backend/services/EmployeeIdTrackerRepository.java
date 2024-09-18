package com.Internship.Backend.services;

import org.springframework.stereotype.Repository;

public interface EmployeeIdTrackerRepository extends JpaRepository<EmployeeIdTracker, Integer> {
    EmployeeIdTracker findByYear(int year);
}
