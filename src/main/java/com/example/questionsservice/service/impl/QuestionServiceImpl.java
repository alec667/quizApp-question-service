package com.example.questionsservice.service.impl;

import com.example.questionsservice.exception.QuestionNotFoundException;
import com.example.questionsservice.model.Question;
import com.example.questionsservice.repository.QuestionRepository;
import com.example.questionsservice.service.QuestionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    final
    QuestionRepository repository;

    public QuestionServiceImpl(QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question getQuestion(Integer questionId) {
        Optional<Question> optional = repository.findById(questionId);
        Question question;
        if (optional.isPresent()) {
            question = optional.get();
            return question;
        } else {
            throw new QuestionNotFoundException("Question Not Found");
        }
    }

    @Override
    public List<Question> getAllQuestion() {
        List<Question> questions = new ArrayList<>();
        questions = repository.findAll();
        return questions;
    }

    @Override
    public String addQuestion(Question question) {
        repository.save(question);
        return "Question created";
    }

    @Override
    public Question updateQuestion(Question question) {
        Optional<Question> optional = repository.findById(question.getId());
        Question updated = new Question();
        if (optional.isPresent()){
            updated = optional.get();
            repository.save(updated);
            return updated;
        }else{
            return repository.save(question);
        }
    }

    @Override
    public String deleteQuestion(Integer questionId) {
        Optional<Question> optionalQuestion = repository.findById(questionId);
        if (optionalQuestion.isPresent()) {
            repository.deleteById(questionId);
            return "Question Id: " + questionId + " deleted";
        } else {
            return "Question Id: " + questionId + " doesn't exist";
        }
    }
}
