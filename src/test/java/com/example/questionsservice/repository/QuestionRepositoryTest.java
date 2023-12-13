package com.example.questionsservice.repository;

import com.example.questionsservice.model.Question;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;
    Question testQuestion1, testQuestion2;

    @BeforeEach
    void setUp() {
        testQuestion1 = new Question(1, "What is the default value of float variable?", "Java", "0.0d", "0.0f", "0", "not defined", "0.0f");
        testQuestion2 = new Question(2, "Which of the following is NOT a keyword in java?", "Java", "static", "Boolean", "void", "private", "Boolean");
        questionRepository.save(testQuestion1);
        questionRepository.save(testQuestion2);
    }

    @AfterEach
    void tearDown() {
        testQuestion1 = null;
        testQuestion2 = null;
        questionRepository.deleteAll();
    }

    @Test
    void findByCategory() {
        List<Question> questions = questionRepository.findByCategory("Java");

        assertThat(questions.get(0).getRightAnswer()).isEqualTo("0.0f");
        assertThat(questions.get(1).getRightAnswer()).isEqualTo("Boolean");
    }

    @Test
    void findRandomQuestionsByCategory() {
        List<Integer> questionsIds = questionRepository.findRandomQuestionsByCategory("Java", 2);

        assertThat(questionsIds.size()).isEqualTo(2);
    }
}