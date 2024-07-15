////////////////////////////////////////////////////////////////////////////////
///                      Steady State Genetic Algorithm v1.0                 ///
///                       by Enrique Alba, September 1999                    ///
///                                                                          ///
///                      Individual = Chromosome + Fitness                   ///
////////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.io.*;

public class Individual implements Serializable {
  private Chromosome chrom;
  private int L;
  private double fitness;

  public Individual(int L) {
    chrom = new Chromosome(L);
    fitness = 0.0;
    this.L = L;
  }

  public void print() {
    chrom.print();
    System.out.print("   ");
    System.out.println(fitness);
  }

  public int getLength() {
    return L;
  }

  public void setFitness(double fit) {
    fitness = fit;
  }

  public double getFitness() {
    return fitness;
  }

  public void setAllele(int index, byte value) {
    chrom.setAllele(index, value);
  }

  public byte getAllele(int index) {
    return chrom.getAllele(index);
  }

  private void copy(Chromosome source, Chromosome destination) {
    for (int i = 0; i < L; i++) {
      destination.setAllele(i, source.getAllele(i));
    }
  }

  public void assign(Individual I) {
    copy(I.getChromosome(), chrom);
    fitness = I.getFitness();
    L = I.getLength();
  }

  public void setChrom(Chromosome ch) {
    copy(ch, chrom);
  }

  public Chromosome getChromosome() {
    return chrom;
  }
}
// END OF CLASS: Individual
