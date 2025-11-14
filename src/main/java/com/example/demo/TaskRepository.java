package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

// Task 엔티티를 관리하는 Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
