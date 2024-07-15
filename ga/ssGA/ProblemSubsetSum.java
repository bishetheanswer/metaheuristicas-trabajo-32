package ga.ssGA;

import java.util.ArrayList;
import java.util.List;

public class ProblemSubsetSum extends Problem {
    private Integer C;
    private List<Integer> w = new ArrayList<>();

    public ProblemSubsetSum() {
        super();
    }

    public void setC(Integer capacity) {
        C = capacity;
    }

    public Integer getC() {
        return C;
    }

    public void setW(List<Integer> weights) {
        w = weights;
    }

    public Integer getNItems() {
        return w.size();

    }

    public double Evaluate(Individual indiv) {
        int i;
        double fitness = 0.0;

        if (chromosomeLength != w.size())
            System.out.println("Length mismatch error in Subset sum function.");

        for (i = 0; i < chromosomeLength; i++)
            fitness += indiv.getAllele(i) * w.get(i);

        if (fitness > C) {
            fitness = C - fitness * 0.1;
            if (fitness < 0.0)
                fitness = 0.0;
        }
        indiv.setFitness(fitness);
        return fitness;
    }
}
