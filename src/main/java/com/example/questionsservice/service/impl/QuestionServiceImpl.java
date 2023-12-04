package com.example.questionsservice.service.impl;

import com.example.questionsservice.model.Question;
import com.example.questionsservice.repository.QuestionRepository;
import com.example.questionsservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    final
    QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question getQuestion(Integer questionId) {
        return null;
    }

    @Override
    public List<Question> getAllQuestion() {
        return null;
    }

    @Override
    public String addQuestion(Question question) {
        return null;
    }

    @Override
    public Question updateQuestion(Question question) {
        return null;
    }

    @Override
    public String deleteQuestion(Integer questionId) {
        return null;
    }
}
