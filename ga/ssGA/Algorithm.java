///////////////////////////////////////////////////////////////////////////////
///               Steady State Genetic Algorithm v1.0                       ///
///                 by Enrique Alba, September 1999                         ///
///                                                                         ///
///  2TOURNAMENT+SPX(rand_parent)+Bit_Mutation+Replacement(Worst_Always)    ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.util.Random;

public class Algorithm {
  private int chromosomeLength; // Alleles per chromosome
  private int geneNumber; // Number of genes in every chromosome
  private int geneLength; // Number of bits per gene
  private int populationSize; // Number of individuals in the population
  private double crossoverProb, mutationProb; // Probability of applying crossover and mutation
  private Problem problem; // The problem being solved
  private Population pop; // The population
  private static Random r; // Source for random values in this class
  private Individual auxIndiv; // Internal auxiliar individual being computed

  // CONSTRUCTOR
  public Algorithm(Problem p, int popsize, int gn, int gl, double pc, double pm) throws Exception {
    this.geneNumber = gn;
    this.geneLength = gl;
    this.chromosomeLength = gn * gl;
    this.populationSize = popsize;
    this.crossoverProb = pc;
    this.mutationProb = pm;
    this.problem = p;
    this.pop = new Population(popsize, chromosomeLength); // Create initial population
    this.r = new Random();
    this.auxIndiv = new Individual(chromosomeLength);

    for (int i = 0; i < popsize; i++)
      pop.setFitness(i, problem.evaluateStep(pop.getIth(i)));
    pop.computeStats();
  }

  public double getMutationProb() {
    return this.mutationProb;
  }

  // BINARY TOURNAMENT
  public Individual selectTournament() throws Exception {
    int p1, p2;

    p1 = (int) (r.nextDouble() * (double) populationSize + 0.5); // Round and then trunc to int

    if (p1 > populationSize - 1)
      p1 = populationSize - 1;
    do {
      p2 = (int) (r.nextDouble() * (double) populationSize + 0.5); // Round and then trunc to int
      if (p2 > populationSize - 1)
        p2 = populationSize - 1;
    } while (p1 == p2);
    if (pop.getIth(p1).getFitness() > pop.getIth(p2).getFitness())
      return pop.getIth(p1);
    else
      return pop.getIth(p2);
  }

  // SINGLE POINT CROSSOVER - ONLY ONE CHILD IS CREATED (RANDOMLY DISCARD
  // DE OTHER)
  public Individual SPX(Individual p1, Individual p2) {
    int rand;

    rand = (int) (r.nextDouble() * (double) chromosomeLength - 1 + 0.5); // From 0 to L-1 rounded
    if (rand > chromosomeLength - 1)
      rand = chromosomeLength - 1;

    if (r.nextDouble() > crossoverProb) // If no crossover then randomly returns one parent
      return r.nextDouble() > 0.5 ? p1 : p2;

    // Copy CHROMOSOME 1
    for (int i = 0; i < rand; i++) {
      auxIndiv.setAllele(i, p1.getAllele(i));
    }
    // Copy CHROMOSOME 2
    for (int i = rand; i < chromosomeLength; i++) {
      auxIndiv.setAllele(i, p2.getAllele(i));
    }

    return auxIndiv;
  }

  // MUTATE A BINARY CHROMOSOME
  public Individual mutate(Individual p1) {
    byte alelle = 0;
    Random r = new Random();

    auxIndiv.assign(p1);

    for (int i = 0; i < chromosomeLength; i++)
      if (r.nextDouble() <= mutationProb) // Check mutation bit by bit...
      {
        if (auxIndiv.getAllele(i) == 1)
          auxIndiv.setAllele(i, (byte) 0);
        else
          auxIndiv.setAllele(i, (byte) 1);
      }

    return auxIndiv;
  }

  // REPLACEMENT - THE WORST INDIVIDUAL IS ALWAYS DISCARDED
  public void replace(Individual new_indiv) throws Exception {
    pop.setIth(pop.getWorstIndividualIndex(), new_indiv);
    // pop.compute_stats(); // Recompute avg, best, worst, etc.
  }

  // EVALUATE THE FITNESS OF AN INDIVIDUAL
  private double evaluateStep(Individual indiv) {
    return problem.evaluateStep(indiv);
  }

  public void goOneStep() throws Exception {
    auxIndiv.assign(SPX(selectTournament(), selectTournament()));
    auxIndiv.setFitness(problem.evaluateStep(mutate(auxIndiv)));
    replace(auxIndiv);
  }

  public Individual getSolution() throws Exception {
    return pop.getIth(pop.getBestIndividualIndex()); // The better individual is the solution
  }

  public int getWorstIndividualIndex() {
    return pop.getWorstIndividualIndex();
  }

  public int getBestIndividualIndex() {
    return pop.getBestIndividualIndex();
  }

  public double getWorstFitness() {
    return pop.getWorstFitness();
  }

  public double getAverageFitness() {
    return pop.getAverageFitness();
  }

  public double getBestFitness() {
    return pop.getBestFitness();
  }

  public double getBestFitnessEver() {
    return pop.getBestFitnessEver();
  }

  public Individual getIth(int index) throws Exception {
    return pop.getIth(index);
  }

  public void setIth(int index, Individual indiv) throws Exception {
    pop.setIth(index, indiv);
  }
}
// END OF CLASS: Algorithm
