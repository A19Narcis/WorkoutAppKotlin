package com.example.kotlinworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel> {
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks = ExerciseModel(
            1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            false,
            false
        )
        exerciseList.add(jumpingJacks)

        val plank = ExerciseModel(
            2,
            "Plank",
            R.drawable.ic_plank,
            false,
            false
        )
        exerciseList.add(plank)

        val squat = ExerciseModel(
            3,
            "Squat",
            R.drawable.ic_squat,
            false,
            false
        )
        exerciseList.add(squat)

        val sidePlank = ExerciseModel(
            4,
            "Side Plank",
            R.drawable.ic_side_plank,
            false,
            false
        )
        exerciseList.add(sidePlank)

        return exerciseList
    }

}