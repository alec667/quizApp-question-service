package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.service.QuestionService;
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
    ResponseEntity<Question> getQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    ResponseEntity<String> createQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.updateQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping("{questionId}")
    ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.deleteQuestion(questionId), HttpStatus.OK);
    }
}
