package com.example.questionsservice.service;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.model.QuestionWrapper;
import com.example.questionsservice.model.QuizAnswers;

import java.util.List;

public interface QuestionService {

    Question getQuestion(Integer questionId);
    List<Integer> getQuestionsForQuiz(String category, int numOfQuestions);
    List<QuestionWrapper> getQuestionsFromId(List<Integer> questionsId);
    List<Question> getAllQuestion();
    String addQuestion(Question question);
    Question updateQuestion(Question question);
    String deleteQuestion(Integer questionId);
    Integer getScore(List<QuizAnswers> answersList);


}
