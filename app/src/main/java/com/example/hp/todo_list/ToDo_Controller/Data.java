package com.example.hp.todo_list.ToDo_Controller;



public class Data { //Creating class
    // Declaring variables
    private String status;
    private int id;
    private String title;
    private String description;
    private String date;


    public String getStatus() { // Method will get the status of task
        return status;
    }

    public void setStatus(String status) { // Method will set the status of task
        this.status = status;
    }

    public String getDescription() { // Method will get the description of the task
        return description;
    }

    public void setDescription(String description) { // Method will set the description of the task
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


