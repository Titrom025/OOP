package Task_3_2.RecordBook;

import java.util.Arrays;

public class Semester {
    int numberOfGrades;
    int[] grades;

    /**
     * Initializer of semester without grades, using grades count
     * @param numberOfGrades - grades count
     */
    Semester(int numberOfGrades) {
        this.numberOfGrades = numberOfGrades;
        grades = new int[0];
    }

    /**
     * Initializer of semester with
     * @param grades - grades
     */
    Semester(int[] grades) {
        this.numberOfGrades = grades.length;
        this.grades = grades;
    }

    /**
     * @return count of excellent grades
     */
    public int getExcellentGradesCount() {
        if (grades.length == 0) {
            return numberOfGrades;
        }

        return Arrays.stream(grades).map(x -> x == 5 ? 1 : 0).sum();
    }

    /**
     * @return count of grades, that less than 4
     */
    public int getBadGradesCount() {
        return Arrays.stream(grades).map(x -> x <= 3 ? 1 : 0).sum();
    }

    /**
     * @return an average grade in current semester
     */
    public double getAverageGrade() {
        if (grades.length == 0) {
            return 0;
        }
        return (double) Arrays.stream(grades).sum() / numberOfGrades;
    }

}
