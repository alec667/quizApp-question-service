package com.example.questionsservice.controller;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.model.QuestionWrapper;
import com.example.questionsservice.model.QuizAnswers;
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
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.getQuestion(questionId), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> createQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.updateQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping("{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Integer questionId) {
        return new ResponseEntity<>(questionService.deleteQuestion(questionId), HttpStatus.OK);
    }

    //TODO generate quiz
    @GetMapping(path = "generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam int numOfQuestions) {
        return new ResponseEntity<>(questionService.getQuestionsForQuiz(category, numOfQuestions), HttpStatus.OK);

    }

    //TODO getQuestions (questionId)
    @PostMapping(path = "getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId) {
        return new ResponseEntity<>(questionService.getQuestionsFromId(questionsId), HttpStatus.OK);
    }

    //TODO calculate score
    @PostMapping(path = "getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizAnswers> answersList) {
        return new ResponseEntity<>(questionService.getScore(answersList), HttpStatus.OK);

    }
}
