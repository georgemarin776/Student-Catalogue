import java.util.ArrayList;

class BestExamScore implements Strategy {
    @Override
    public Double getBestScore(ArrayList<Grade> grades) {
        Double bestScore = 0.0;
        for (Grade grade : grades) {
            if (grade.getExamScore() > bestScore) {
                bestScore = grade.getExamScore();
            }
        }
        return bestScore;
    }
    @Override
    public Double getScore(Grade grade) {
        return grade.getExamScore();
    }
}