package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.model.QuestionWrapper;
import com.example.questionsservice.model.QuizAnswers;
import com.example.questionsservice.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionService questionService;

    @GetMapping(path = "{questionId}", produces = "application/json")
    @Operation(summary = "Returns a question by ID", description = "Provides Question details", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    @Operation(summary = "Returns a List of all questions", description = "Provides a list of questions as an ordered List", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @Operation(summary = "Creates a question", description = "Creates a question object from request body", responses = {@ApiResponse(responseCode = "201", description = "status CREATED")})
    public ResponseEntity<String> createQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Updates a question", description = "updates an existing question or, if it doesn't exist, creates it", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.updateQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping("{questionId}")
    @Operation(summary = "Deletes a question by ID", description = "Deletes a question from database", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.deleteQuestion(questionId), HttpStatus.OK);
    }

    // generate quiz
    @GetMapping(path = "generate", produces = "application/json")
    @Operation(summary = "Returns question's IDs for Quiz-Service", description = "Provides a list of questions IDs to be used by quiz-service API as GET method", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numOfQuestions) {
        return new ResponseEntity<>(questionService.getQuestionsForQuiz(category, numOfQuestions), HttpStatus.OK);

    }

    //getQuestions (questionId)
    @PostMapping(path = "getQuestions", consumes = "application/json")
    @Operation(summary = "Returns a list of question wrapper", description = "Provides (posts) a list of question wrapper objects to be used by quiz-service API", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId) {
        return new ResponseEntity<>(questionService.getQuestionsFromId(questionsId), HttpStatus.OK);
    }

    //calculate score
    @PostMapping(path = "getScore")
    @Operation(summary = "Calculates score", description = "Provides (posts) score for quiz-service API", responses = {@ApiResponse(responseCode = "200", description = "status OK")})
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizAnswers> answersList) {
        return new ResponseEntity<>(questionService.getScore(answersList), HttpStatus.OK);

    }
}
