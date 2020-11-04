package Task_3_2.RecordBook;

import java.util.Arrays;

public class Semester {
    int numberOfGrades;
    int[] grades;

    Semester(int numberOfGrades) {
        this.numberOfGrades = numberOfGrades;
        grades = new int[0];
    }

    Semester(int[] grades) {
        this.numberOfGrades = grades.length;
        this.grades = grades;
    }

    public int getExcellentGradesCount() {
        if (grades.length == 0) {
            return numberOfGrades;
        }

        return Arrays.stream(grades).map(x -> x == 5 ? 1 : 0).sum();
    }

    public int getBadGradesCount() {
        return Arrays.stream(grades).map(x -> x <= 3 ? 1 : 0).sum();
    }

    public double getAverageGrade() {
        if (grades.length == 0) {
            return 0;
        }
        return (double) Arrays.stream(grades).sum() / numberOfGrades;
    }

}
