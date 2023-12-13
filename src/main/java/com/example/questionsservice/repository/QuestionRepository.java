package com.example.questionsservice.repository;

import com.example.questionsservice.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);


    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RAND() LiMIT :numOfQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numOfQuestions);
}
