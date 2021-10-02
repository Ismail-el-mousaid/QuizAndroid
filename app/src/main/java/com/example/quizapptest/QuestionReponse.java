package com.example.quizapptest;

public class QuestionReponse {
    String question, rep1, rep2;

    public QuestionReponse(){

    }

    public QuestionReponse(String question, String rep1, String rep2) {
        this.question = question;
        this.rep1 = rep1;
        this.rep2 = rep2;
    }

    public String getQuestion() {
        return question;
    }

    public String getRep1() {
        return rep1;
    }

    public String getRep2() {
        return rep2;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setRep1(String rep1) {
        this.rep1 = rep1;
    }

    public void setRep2(String rep2) {
        this.rep2 = rep2;
    }
}
