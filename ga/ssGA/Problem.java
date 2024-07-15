///////////////////////////////////////////////////////////////////////////////
///                 Steady State Genetic Algorithm v1.0                     ///
///                      by Enrique Alba, July 2000                         ///
///                                                                         ///
///         Problem Function AND Representation (GL, GN, Ranges)            ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.Random;

public abstract class Problem // Maximization task
{
  protected int geneLength = 1; // Gene lenth in binary
  protected int geneNumber = 1; // Gene number in one string
  protected int chromosomeLength = geneNumber * geneLength; // Chromosome length
  protected long fitnessCounter; // Number of evaluations
  protected double targetFitness; // Target fitness value -MAXIMUM-
  protected boolean isTargetFitnessKnown; // Is the taret fitness known????
  protected static Random r = new Random(); // Random uniform variable

  public Problem() {
    chromosomeLength = geneNumber * geneLength;
    fitnessCounter = 0;
    isTargetFitnessKnown = false;
    targetFitness = -999999.9;
  }

  public int getGeneLength() {
    return geneLength;
  }

  public int getGeneNumber() {
    return geneNumber;
  }

  public void setGeneLength(int gl) {
    geneLength = gl;
    chromosomeLength = geneNumber * geneLength;
  }

  public void setGeneNumber(int gn) {
    geneNumber = gn;
    chromosomeLength = geneNumber * geneLength;
  }

  public long getFitnessCounter() {
    return fitnessCounter;
  }

  public double getTargetFitness() {
    return targetFitness;
  }

  public boolean isTargetFitnessKnown() {
    return isTargetFitnessKnown;
  }

  public void setTargetFitness(double tf) {
    targetFitness = tf;
    isTargetFitnessKnown = true;
  }

  public double evaluateStep(Individual Indiv) {
    fitnessCounter++;
    return Evaluate(Indiv);
  }

  public abstract double Evaluate(Individual Indiv);
}
// END OF CLASS: Problem
