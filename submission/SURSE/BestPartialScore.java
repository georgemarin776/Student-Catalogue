import java.util.ArrayList;

class BestPartialScore implements Strategy {
    @Override
    public Double getBestScore(ArrayList<Grade> grades) {
        Double bestScore = 0.0;
        for (Grade grade : grades) {
            if (grade.getPartialScore() > bestScore) {
                bestScore = grade.getPartialScore();
            }
        }
        return bestScore;
    }
    @Override
    public Double getScore(Grade grade) {
        return grade.getPartialScore();
    }
}