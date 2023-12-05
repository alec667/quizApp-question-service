package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionsController.class)
class QuestionsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;
    @InjectMocks
    private QuestionsController questionsController;

    private AutoCloseable autoCloseable;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    Question testQuestion1;
    Question testQuestion2;
    List<Question> questionList;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(questionsController).build();

        testQuestion1 = new Question(1, "What is the default value of float variable?", "Java", "0.0d", "0.0f", "0", "not defined", "0.0f");
        testQuestion2 = new Question(2, "Which of the following is NOT a keyword in java?", "Java", "static", "Boolean", "void", "private", "Boolean");
        questionList = Arrays.asList(testQuestion1, testQuestion2);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getQuestionTest() throws Exception {
        when(questionService.getQuestion(1)).thenReturn(testQuestion1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/questions/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.rightAnswer", Matchers.is("0.0f")));
    }

    @Test
    void getAllQuestionsTest() {
    }

    @Test
    void createQuestionTest() {
    }

    @Test
    void updateQuestionTest() {
    }

    @Test
    void deleteQuestionTest() {
    }
}