package Task_3_2.RecordBook;

public class Semester {
    int numberOfGrades;
    boolean isFilled;
    Subject[] grades;

    /**
     * Initializer of semester with
     * @param grades - grades
     */
    Semester(Subject[] grades, boolean isFilled) {
        this.numberOfGrades = grades.length;
        this.grades = grades;
        this.isFilled = isFilled;
    }

    public int getNumberOfGrades() {
        if (grades.length == 0 || !isFilled) {
            return 0;
        }
        return numberOfGrades;
    }
    /**
     * @return count of excellent grades
     */
    public int getExcellentFinalGradesCount() {
        if (grades.length == 0) {
            return 0;
        } else if (!isFilled){
            return numberOfGrades;
        }

        int gradesCount = 0;
        for (int i = 0; i < numberOfGrades; i++) {
            if (grades[i].isFinalGrade && grades[i].grade.ordinal() == 5) {
                gradesCount++;
            }
        }
        return gradesCount;
    }

    /**
     * @return count of grades, that less than 4
     */
    public int getBadFinalGradesCount() {
        if (grades.length == 0 || !isFilled) {
            return 0;
        }

        int gradesCount = 0;
        for (int i = 0; i < numberOfGrades; i++) {
            if (grades[i].isFinalGrade && grades[i].grade.ordinal() <= 3) {
                gradesCount++;
            }
        }
        return gradesCount;
    }

    /**
     * @return the sum of the final grades in this semester
     */
    public int getFinalGradesCount() {
        if (grades.length == 0) {
            return 0;
        } else if (!isFilled){
            return numberOfGrades;
        }

        int gradesCount = 0;
        for (int i = 0; i < numberOfGrades; i++) {
            if (grades[i].isFinalGrade) {
                gradesCount++;
            }
        }
        return gradesCount;
    }

    /**
     * @return sum of grades in current semester
     */
    public int getGradesSum() {
        if (grades.length == 0 || !isFilled) {
            return 0;
        }

        int gradesSum = 0;
        for (int i = 0; i < numberOfGrades; i++) {
            gradesSum += grades[i].grade.ordinal();
        }
        return gradesSum;
    }

    public void setGrades(Grade[] grades) {
        for (int i = 0; i < numberOfGrades; i++) {
            this.grades[i].grade = grades[i];
        }
        isFilled = true;
    }

}
