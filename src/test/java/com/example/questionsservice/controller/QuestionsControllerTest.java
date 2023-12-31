package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.model.QuestionWrapper;
import com.example.questionsservice.model.QuizAnswers;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    Question testQuestion1, testQuestion2;
    QuestionWrapper questionWrapper1, questionWrapper2;
    List<Integer> questionsIds;
    List<Question> questionList;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(questionsController).build();

        testQuestion1 = new Question(1, "What is the default value of float variable?", "Java", "0.0d", "0.0f", "0", "not defined", "0.0f");
        testQuestion2 = new Question(2, "Which of the following is NOT a keyword in java?", "Java", "static", "Boolean", "void", "private", "Boolean");
        questionList = Arrays.asList(testQuestion1, testQuestion2);
        questionsIds = Arrays.asList(testQuestion1.getId(), testQuestion2.getId());
        questionWrapper1 = new QuestionWrapper(1, "What is the default value of float variable?", "0.0d", "0.0f", "0", "not defined");
        questionWrapper2 = new QuestionWrapper(2, "Which of the following is NOT a keyword in java?", "static", "Boolean", "void", "private");

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
    void getAllQuestionsTest() throws Exception {
        when(questionService.getAllQuestion()).thenReturn(questionList);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/questions/all")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].rightAnswer", Matchers.is("Boolean")));
    }

    @Test
    void createQuestionTest() throws Exception {
        Question nQuestion = new Question(9, "Question Title", "category", "option1", "option2", "option3", "rightOption", "rightOption");
        String content = objectWriter.writeValueAsString(nQuestion);
        when(questionService.addQuestion(nQuestion)).thenReturn("Question created");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/questions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(result ->
                        assertThat(result.getResponse().getContentAsString())
                                .isEqualTo("Question created"));
    }

    @Test
    void updateQuestionTest() throws Exception {
        Question updatedQuestion = new Question(1, "Updated title", "Java", "updated_answer", "0.0f", "updated_answer", "updated_answer", "0.0f");

        String content = objectWriter.writeValueAsString(updatedQuestion);
        when(questionService.updateQuestion(updatedQuestion)).thenReturn(updatedQuestion);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/questions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$.questionTitle", Matchers.is("Updated title")));
    }

    @Test
    void deleteQuestionTest() throws Exception {
        when(questionService.deleteQuestion(1)).thenReturn("Question Id: 1 deleted");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .delete("/questions/1")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest).andExpect(status().isOk())
                .andExpect(result ->
                        assertThat(result.getResponse().getContentAsString())
                                .isEqualTo("Question Id: 1 deleted"));
    }

    @Test
    void getQuestionsForQuiz() throws Exception {

        when(questionService.getQuestionsForQuiz("Java", 2)).thenReturn(questionsIds);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/questions/generate")
                .param("category", "Java")
                .param("numOfQuestions", "2");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()));
    }

    @Test
    void getQuestionsFromId() throws Exception {
        List<QuestionWrapper> questions = Arrays.asList(questionWrapper1, questionWrapper2);
        String content = objectWriter.writeValueAsString(questionsIds);
        when(questionService.getQuestionsFromId(questionsIds)).thenReturn(questions);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/questions/getQuestions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].questionTitle", Matchers.is("What is the default value of float variable?")));
    }

    @Test
    void getScore() throws Exception {
        QuizAnswers answer1 = new QuizAnswers(1, "0.0f");
        QuizAnswers answer2 = new QuizAnswers(2, "Boolean");
        List<QuizAnswers> answersList = Arrays.asList(answer1, answer2);
        String content = objectWriter.writeValueAsString(answersList);

        when(questionService.getScore(answersList)).thenReturn(2);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/questions/getScore")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.notNullValue()));

    }
}