package com.example.qili.metcall;

public class Patient {

    //Private variables
    private int patientUR;
    private int gender;
    private int smoking;
    private int alcohol;
    private int emnone;
    private int operations;
    private int anaesthetic;
    private int age;

    //Empty constructor
    public Patient(){ }

    //Constructor
    public Patient(int patientUR, int gender, int smoking, int alcohol, int emnone, int operations, int anaesthetic, int age){
        this.patientUR = patientUR;
        this.gender = gender;
        this.smoking = smoking;
        this.alcohol = alcohol;
        this.emnone = emnone;
        this.operations = operations;
        this.anaesthetic = anaesthetic;
        this.age = age;
    }

    public int getPatientUR() { return patientUR; }
    public void setPatientUR(int patientUR) {
        this.patientUR = patientUR;
    }
    public int getGender(){
        return gender;
    }
    public void setGender(int gender) {
        this.gender = gender;
    }
    public int getSmoking() {
        return smoking;
    }
    public void setSmoking() {
        this.smoking = smoking;
    }
    public int getAlcohol() {
        return alcohol;
    }
    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }
    public int getEmnone() {
        return emnone;
    }
    public void setEmnone(int emnone) {
        this.emnone = emnone;
    }
    public int getOperations() {
        return operations;
    }
    public void setOperations(int operations) {
        this.operations = operations;
    }
    public int getAnaesthetic() {
        return anaesthetic;
    }
    public void setAnaesthetic() {
        this.anaesthetic = anaesthetic;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    }