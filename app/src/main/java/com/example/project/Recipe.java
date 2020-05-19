package com.example.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Recipe {
    String userID;
    String key;
    String title;
    String author;
    String cookTime;
    String prepTime;
    List ingredients;
    List instructions;

    Recipe(){
        userID = "N/A";
        title = "N/A";
        author = "N/A";
        cookTime = "N/A";
        prepTime = "N/A";
        ingredients = new ArrayList();
        instructions = new ArrayList();

    }


    Recipe(String key, String titl, String auth, String cookT, String prepT){
        this.key = key;
        title = titl;
        author = auth;
        cookTime = cookT;
        prepTime = prepT;

    }


    Recipe(String usrID, String titl, String auth, String cookT, String prepT, ArrayList ingr, ArrayList inst){
        userID = usrID;
        title = titl;
        author = auth;
        cookTime = cookT;
        prepTime = prepT;
        ingredients = ingr;
        instructions = inst;

    }

    Recipe(String usrID, String titl, String auth, String cookT, String prepT, String ingr, String inst){
        userID = usrID;
        title = titl;
        author = auth;
        cookTime = cookT;
        prepTime = prepT;
        ingredients = Arrays.asList(ingr.split(", "));
        instructions = Arrays.asList(inst.split(", "));

    }


    public String getUserID() { return userID; }

    public String getTitle() {
        return author;
    }

    public String getAuthor() {
        return author;
    }

    public String getCookTime() {
        return cookTime;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public List getIngredients() {
        return ingredients;
    }

    public List getInstructions() {
        return instructions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = Arrays.asList(ingredients.split(", "));
    }

    public void setInstructions(String instructions) {
        this.instructions = Arrays.asList(instructions.split(", "));
    }

    public String ingredientsToString(){
        String ing = "";
        for(int i=0; i<ingredients.size(); i++){
            if(i==0){
                ing = ingredients.get(i).toString();
            } else {
                ing = ing + "\n" + ingredients.get(i).toString();
            }
        }

        return(ing);
    }

    public String instructionsToString(){
        String ins = "";
        for(int i=0; i<instructions.size(); i++){
            if(i==0){
                ins = "1. "+ instructions.get(i).toString();
            } else {
                ins = ins + "\n" + i+1 + ". " + instructions.get(i).toString();
            }
        }
        return(ins);
    }

    public String ingredientsToString(ArrayList ingr){
        String ing = "";
        for(int i=0; i<ingr.size(); i++){
            if(i==0){
                ing = ingr.get(i).toString();
            } else {
                ing = ing + "\n" + ingr.get(i).toString();
            }
        }

        return(ing);
    }

    public String instructionsToString(ArrayList inst){
        String ins = "";
        for(int i=0; i<inst.size(); i++){
            ins += "\n" + (i+1) + ". " + inst.get(i).toString();
        }
        return(ins);
    }

    @Override
    public String toString(){
        String recipe = "";
        recipe = "userID: " + userID;
        recipe += "\ttitle " + title;
        recipe += "\tauthor " + author;
        return recipe;
    }
}