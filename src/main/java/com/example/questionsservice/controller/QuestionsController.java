package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/questions")
public class QuestionsController {

    @Autowired
    QuestionService questionService;

    @GetMapping(path = "{questionId}")
    ResponseEntity<Question> createQuestion(@PathVariable("questionId") Integer questionId){
        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
    }
}
