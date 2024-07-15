package ga.ssGA;

public class Experiment {
    int populationSize;
    double crossoverProb;
    int mutatedGenesNumber;
    String problemInstancePath;
    long maxISteps;
    int nExecutions;
    Boolean isTargetFitnessKnown;

    public Experiment(int populationSize, double crossoverProb, int mutatedGenesNumber, String problemInstancePath,
            long maxISteps, int nExecutions, Boolean isTargetFitnessKnown) {
        this.populationSize = populationSize;
        this.crossoverProb = crossoverProb;
        this.mutatedGenesNumber = mutatedGenesNumber;
        this.problemInstancePath = problemInstancePath;
        this.maxISteps = maxISteps;
        this.nExecutions = nExecutions;
        this.isTargetFitnessKnown = isTargetFitnessKnown;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "populationSize=" + populationSize +
                ", crossoverProb=" + crossoverProb +
                ", mutatedGenesNumber=" + mutatedGenesNumber +
                ", problemInstancePath='" + problemInstancePath + '\'' +
                ", maxISteps=" + maxISteps +
                '}';
    }
}