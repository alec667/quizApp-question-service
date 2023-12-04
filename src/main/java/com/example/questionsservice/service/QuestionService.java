package com.example.questionsservice.service;

import com.example.questionsservice.model.Question;

import java.util.List;

public interface QuestionService {

    Question getQuestion(Integer questionId);
    List<Question> getAllQuestion();
    String addQuestion(Question question);
    Question updateQuestion(Question question);
    String deleteQuestion(Integer questionId);
}
