package com.example.myapplication.GPACalc;

public class Course {
    private long id;
    private String subjectName;
    private int credit;
    private String grade;

    public Course(long id, String subjectName, int credit, String grade) {
        this.id = id;
        this.subjectName = subjectName;
        this.credit = credit;
        this.grade = grade;
    }

    public long getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}