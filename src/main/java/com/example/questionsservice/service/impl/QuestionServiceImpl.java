package com.example.questionsservice.service.impl;

import com.example.questionsservice.exception.QuestionNotFoundException;
import com.example.questionsservice.model.Question;
import com.example.questionsservice.model.QuestionWrapper;
import com.example.questionsservice.model.QuizAnswers;
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
    public List<Integer> getQuestionsForQuiz(String category, int numOfQuestions) {
        try {
            return repository.findRandomQuestionsByCategory(category, numOfQuestions);
        } catch (Exception e) {
            throw new QuestionNotFoundException("No questions");
        }
    }

    @Override
    public List<QuestionWrapper> getQuestionsFromId(List<Integer> questionsId) {
        List<QuestionWrapper> questionList = new ArrayList<>();
        Optional<Question> optional;
        for (Integer qId : questionsId) {
            optional = repository.findById(qId);
            if (optional.isPresent()) {
                QuestionWrapper question = new QuestionWrapper(optional.get().getId(),
                        optional.get().getQuestionTitle(),
                        optional.get().getOption1(),
                        optional.get().getOption2(),
                        optional.get().getOption3(),
                        optional.get().getOption4());
                questionList.add(question);
            }

        }
        return questionList;
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
        if (optional.isPresent()) {
            updated = optional.get();
            repository.save(updated);
            return updated;
        } else {
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

    @Override
    public Integer getScore(List<QuizAnswers> answersList) {
        Integer score = 0;
        for (QuizAnswers a : answersList) {
            Optional<Question> question = repository.findById(a.getId());
            if (question.isPresent()) {
                if (a.getAnswer().equalsIgnoreCase(question.get().getRightAnswer())) {
                    score++;
                }
            }

        }

        return score;
    }

}
