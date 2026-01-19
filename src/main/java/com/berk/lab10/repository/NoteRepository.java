package com.berk.lab10.repository;

import com.berk.lab10.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    Optional<Note> findByIdAndUserId(Integer id, Integer userId);
}
