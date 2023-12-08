package com.example.questionsservice.service.impl;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.repository.QuestionRepository;
import com.example.questionsservice.service.QuestionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    private QuestionService questionService;
    private AutoCloseable autoCloseable;
    Question testQuestion;
    Question testQuestion2;
    List<Question> questionList;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.questionService = new QuestionServiceImpl(questionRepository);
        testQuestion = new Question(1, "What is the default value of float variable?", "Java", "0.0d", "0.0f", "0", "not defined", "0.0f");
        testQuestion2 = new Question(2, "Which of the following is NOT a keyword in java?", "Java", "static", "Boolean", "void", "private", "Boolean");
        questionList = Arrays.asList(testQuestion, testQuestion2);
        questionRepository.save(testQuestion);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
        questionRepository.deleteAll();
    }

    @Test
    void getQuestion() {
        mock(Question.class);
        mock(QuestionRepository.class);

        when(questionRepository.findById(1)).thenReturn(Optional.ofNullable(testQuestion));
        assertThat(questionService.getQuestion(1)).isEqualTo(testQuestion);
    }

    @Test
    void getAllQuestion() {
        mock(Question.class);
        mock(QuestionRepository.class);

        when(questionRepository.findAll()).thenReturn(questionList);
        assertThat(questionService.getAllQuestion()).isEqualTo(questionList);
    }

    @Test
    void addQuestion() {
        mock(Question.class);
        mock(QuestionRepository.class);
        Question nQuestion = new Question(9, "Question Title", "category", "option1", "option2", "option3", "rightOption", "rightOption");

        when(questionRepository.save(nQuestion)).thenReturn(nQuestion);
        assertThat(questionService.addQuestion(nQuestion)).isEqualTo("Question created");
    }

    @Test
    void updateQuestion() {
        mock(Question.class);
        mock(QuestionRepository.class);
        Question updatedQuestion = new Question(1, "Updated title", "Java", "updated_answer", "0.0f", "updated_answer", "updated_answer", "0.0f");

        when(questionRepository.save(updatedQuestion)).thenReturn(updatedQuestion);
        assertThat(questionService.updateQuestion(updatedQuestion)).isEqualTo(updatedQuestion);
    }

    @Test
    void deleteQuestion() {
        mock(Question.class);
        mock(QuestionRepository.class, CALLS_REAL_METHODS);
        String response = "Question Id: 1 doesn't exist";

        doAnswer(Answers.CALLS_REAL_METHODS).when(questionRepository).deleteById(any());
        assertThat(questionService.deleteQuestion(1)).isEqualTo(response);
    }
}