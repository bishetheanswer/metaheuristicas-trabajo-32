////////////////////////////////////////////////////////////////////////////////
///                          Steady State Genetic Algorithm v1.0             ///
///                            by Enrique Alba, September 1999               ///
///                                                                          ///
///   Population of "popsize" binary individuals(GN,GL) and their stats      ///
////////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

public class Population {
  // PRIVATE MEMORY
  private int populationSize; // The number of individuals
  private Individual population[]; // The vector of individuals

  private int chromosomeLength; // The length of the chromosomes

  // STATISTICS
  private int bestIndividualIndex; // The position of the best individual: [0..popsize-1]
  private int worstIndividualIndex; // The position of the worst individual: [0..popsize-1]
  private double bestFitness; // The best fitness of the present population
  private double averageFitness; // The average fitness of the present population
  private double worstFitness; // The worst fitness of the present population
  private double bestFitnessEver; // The best fitness ever found during the search

  public Population(int ps, int chroml) {
    populationSize = ps;
    population = new Individual[populationSize];
    chromosomeLength = chroml;

    for (int i = 0; i < populationSize; i++)
      population[i] = new Individual(chroml);

    // Initialize statistics
    bestIndividualIndex = 0;
    worstIndividualIndex = 0;
    bestFitness = 0.0;
    averageFitness = 0.0;
    worstFitness = 9999999999.0;
    bestFitnessEver = 0.0;
  }

  public int getPopulationSize() {
    return populationSize;
  }

  public int worstIndex() {
    return worstIndividualIndex;
  }

  public Individual getIth(int index) throws Exception {
    if ((index < populationSize) && (index >= 0))
      return population[index];
    else
      throw new Exception("Index out of range when getting a copy of an individual");
  }

  public void setIth(int index, Individual indiv) throws Exception {
    if ((index < populationSize) && (index >= 0))
      population[index].assign(indiv);
    else
      throw new Exception("Index out of range when inserting and individual");

    // ALWAYS RECOMPUTE STATS AFTER INSERTION
    computeStats();
  }

  public void setFitness(int index, double fitness) throws Exception {
    population[index].setFitness(fitness);
  }

  public void computeStats() {
    double f, total;

    // Initialize values (always needed!!!)
    total = 0.0;
    worstFitness = population[0].getFitness();
    worstIndividualIndex = 0;
    bestFitness = population[0].getFitness();
    bestIndividualIndex = 0;

    for (int i = 0; i < populationSize; i++) {
      f = population[i].getFitness();
      if (f <= worstFitness) {
        worstFitness = f;
        worstIndividualIndex = i;
      }
      if (f >= bestFitness) {
        bestFitness = f;
        bestIndividualIndex = i;
      }
      if (f >= bestFitnessEver) {
        bestFitnessEver = f;
      }
      total += f;
    }

    averageFitness = total / (double) populationSize;
  }

  public int getWorstIndividualIndex() {
    return worstIndividualIndex;
  }

  public int getBestIndividualIndex() {
    return bestIndividualIndex;
  }

  public double getWorstFitness() {
    return worstFitness;
  }

  public double getAverageFitness() {
    return averageFitness;
  }

  public double getBestFitness() {
    return bestFitness;
  }

  public double getBestFitnessEver() {
    return bestFitnessEver;
  }

  public void print() {
    for (int i = 0; i < populationSize; i++) {
      System.out.print(i);
      System.out.print("   ");
      for (int j = 0; j < chromosomeLength; j++)
        System.out.print(population[i].getAllele(j));
      System.out.print("   ");
      System.out.println(population[i].getFitness());
    }
  }

  public void printStats() {
    System.out.print(bestFitnessEver);
    System.out.print("   ");
    System.out.print(bestFitness);
    System.out.print("   ");
    System.out.print(averageFitness);
    System.out.print("   ");
    System.out.print(worstFitness);
    System.out.print("   ");
    System.out.print(bestIndividualIndex);
    System.out.print("   ");
    System.out.println(worstIndividualIndex);
  }
}
// END OF CLASS: Population
