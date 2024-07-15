///////////////////////////////////////////////////////////////////////////////
///            Steady State Genetic Algorithm v1.0                          ///
///                by Enrique Alba, July 2000                               ///
///                                                                         ///
///   Executable: set parameters, problem, and execution details here       ///
///////////////////////////////////////////////////////////////////////////////

package ga.ssGA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exe {

  private static void loadWeightsAndCapacity(ProblemSubsetSum problem, String problemInstancePath) {
    Integer C = -1;
    List<Integer> w = new ArrayList<>();
    try {
      File file = new File(problemInstancePath);
      Scanner scanner = new Scanner(file);

      C = Integer.parseInt(scanner.nextLine().trim());

      scanner.nextLine();

      String[] numbers = scanner.nextLine().split("\\s+");
      for (String num : numbers) {
        w.add(Integer.parseInt(num.trim()));
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      System.err.println("File not found at path: " + problemInstancePath);
    } catch (NumberFormatException e) {
      System.err.println("Error parsing integer from file.");
    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
    }
    problem.setC(C);
    problem.setW(w);
  }

  private static ProblemSubsetSum initializeProblem(String problemInstancePath, Boolean isTargetFitnessKnown) {
    ProblemSubsetSum problem = new ProblemSubsetSum();
    loadWeightsAndCapacity(problem, problemInstancePath);

    int geneNumber = problem.getNItems();
    int geneLength = 1;
    // double targetFitness = problem.getC();

    problem.setGeneNumber(geneNumber);
    problem.setGeneLength(geneLength);
    // problem.setTargetFitness(targetFitness);

    if (isTargetFitnessKnown) {
      problem.setTargetFitness(problem.getC());
    }

    return problem;
  }

  private static Algorithm initializeAlgorithm(ProblemSubsetSum problem, Experiment e) throws Exception {
    // double mutationProb = (double) e.mutatedGenesNumber /
    // problem.getGeneNumber();
    double mutationProb = e.mutatedGenesNumber
        / ((double) problem.getGeneNumber() * (double) problem.getGeneLength());
    return new Algorithm(problem, e.populationSize, problem.getGeneNumber(), problem.getGeneLength(), e.crossoverProb,
        mutationProb);
  }

  private static void runExperiment(Experiment e, StringBuilder results) throws Exception {
    System.out.print("Executing ");
    System.out.println(e);
    for (int i = 0; i < e.nExecutions; i++) {
      ProblemSubsetSum problem = initializeProblem(e.problemInstancePath, e.isTargetFitnessKnown);
      System.out.println(problem.getTargetFitness());
      Algorithm ga = initializeAlgorithm(problem, e);

      for (int step = 0; step < e.maxISteps; step++) {
        ga.goOneStep();
        // System.out.print(step);
        // System.out.print(" ");
        // System.out.println(ga.getBestFitness());

        // if ((problem.isTargetFitnessKnown())
        // && (ga.getSolution()).getFitness() >= problem.getTargetFitness()) {
        if ((problem.isTargetFitnessKnown())
            && (ga.getSolution()).getFitness() == problem.getTargetFitness()) {
          System.out.print("Solution Found! After ");
          System.out.print(problem.getFitnessCounter());
          System.out.println(" evaluations");
          break;
        }
      }

      // Print the solution
      // for (int j = 0; j < problem.getGeneNumber() * problem.getGeneLength(); j++)
      // System.out.print((ga.getSolution()).getAllele(j));
      // System.out.println();
      // System.out.println((ga.getSolution()).getFitness());
      String formattedString = String.format("%s, %f, %f, %d, %d, %d, %d, %d, %f\n",
          e.problemInstancePath,
          e.crossoverProb,
          ga.getMutationProb(),
          e.mutatedGenesNumber,
          e.maxISteps,
          i,
          -1,
          problem.getFitnessCounter(),
          ga.getSolution().getFitness());
      results.append(formattedString);
    }
  }

  public static void main(String args[]) throws Exception {
    int populationSize = 100;
    double[] crossoverProbs = { 0.6, 0.7, 0.8, 0.9, 1 };
    // int[] mutatedGenesNumbers = { 1, 2, 3, 4, 5 }; // average expected number of
    // mutated genes per individual
    int[] mutatedGenesNumbers = { 1, 5, 10, 15, 30 }; // average expected number of mutated genes per individual
    String problemInstancePath = "./problems/s300_simple";
    // long MAX_ISTEPS = 1500;
    long MAX_ISTEPS = 1000000;
    int nExecutions = 100;
    Boolean isTargetFitnessKnown = true;

    // s100 --> 500
    // s200 --> 1000
    // s300 --> 1500

    List<Experiment> experiments = new ArrayList<>();
    for (double crossoverProb : crossoverProbs) {
      for (int mutatedGenesNumber : mutatedGenesNumbers) {
        Experiment experiment = new Experiment(populationSize, crossoverProb, mutatedGenesNumber, problemInstancePath,
            MAX_ISTEPS, nExecutions, isTargetFitnessKnown);
        experiments.add(experiment);
      }
    }

    StringBuilder results = new StringBuilder(
        "problem,crossoverProb,mutationProb,nMutatedGenes,maxISteps,execution,nGenerations,nFitnessEvaluations,bestFitness\n");
    for (Experiment e : experiments) {
      runExperiment(e, results);
    }
    try {
      FileWriter writer = new FileWriter(String.format("%s_results_solution_found_AAAA.csv",
          problemInstancePath));
      writer.write(results.toString());
      writer.close();
    } catch (IOException e) {
      System.out.println("An error ocurrer while writing to the file");
    }
  }

}
// END OF CLASS: Exe
