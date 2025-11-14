package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*", maxAge = 3600)
@SuppressWarnings("null")
public class TaskController {

    private final TaskRepository repository;

    // 생성자 주입
    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // 모든 할 일 조회
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = repository.findAll();
        return ResponseEntity.ok(tasks);
    }

    // 새 할 일 추가
    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task saved = repository.save(task);
        return ResponseEntity.status(201).body(saved); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setCompleted(updatedTask.isCompleted());
                    Task saved = repository.save(task);
                    return ResponseEntity.ok(saved); // 200 OK
                })
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
