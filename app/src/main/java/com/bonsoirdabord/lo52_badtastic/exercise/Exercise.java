package com.bonsoirdabord.lo52_badtastic.exercise;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class Exercise {

    /**
     * Name of the exercise
     */
    private String name;

    /**
     * Description of the exercise
     */
    private String description;

    /**
     * Physical difficulty of the exercise. Not be confused with {@link #publicCategory}
     * Must be a value between 1 and 5.
     */
    private int difficulty;

    /**
     * Words that will help to find and classify the exercise
     */
    private List<String> tags;

    /**
     * Category of people that can participate to the exercise.
     * Must be a value {@link #PUBLIC_BEGINNER} or {@link #PUBLIC_EXPERIENCED}
     * TODO: Modifier le type de la variable en fonction de la réponse à l'issue 2 github
     */
    private int publicCategory;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PUBLIC_BEGINNER, PUBLIC_EXPERIENCED})
    public @interface  PUBLIC_CATEGORY{}
    public static final int PUBLIC_BEGINNER = 1;
    public static final int PUBLIC_EXPERIENCED = 2;

    /**
     * Constructor of the Exercise class
     * @param name - Name of the exercise
     * @param description - Description of the exercise
     * @param difficulty - Difficulty of the exercise between 1 and 5
     * @param tags - List of words that describes the exercise
     * @param publicCategory - Type of public that can participate to the exercise
     */
    public Exercise(String name, String description, int difficulty, List<String> tags, @PUBLIC_CATEGORY int publicCategory) {

        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tags = tags;
        this.publicCategory = publicCategory;

    }

}
