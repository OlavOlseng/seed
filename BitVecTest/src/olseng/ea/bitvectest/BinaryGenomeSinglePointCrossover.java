package olseng.ea.bitvectest;

import olseng.ea.genetics.GeneticCrossoverOperator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

/**
 * Created by Olav on 22.01.2016.
 */
public class BinaryGenomeSinglePointCrossover extends GeneticCrossoverOperator<BinaryGenome> {

    public BinaryGenomeSinglePointCrossover(double weight) {
        super(weight);
    }

    @Override
    public BinaryGenome crossover(BinaryGenome parent1, BinaryGenome parent2) {
        BinaryGenome child = new BinaryGenome(parent1.geneCount, parent1.geneSize, parent1.getDeepCopy());
        int crossoverIndex;

        if(child.geneCount == 2) {
            crossoverIndex = 1;
        }
        else {
            crossoverIndex = (int)(Math.random() * parent1.geneCount - 1) + 1;
        }

        //System.out.println("Crossover index: " + crossoverIndex);

        for (int geneIndex = crossoverIndex; geneIndex < child.geneCount; geneIndex++) {
            child.setGene(parent2.getGene(geneIndex), geneIndex);
        }
        return child;
    }
    //#################TEST##################
    public static void main(String[] args) {
        BinaryGenomeSinglePointCrossover op = new BinaryGenomeSinglePointCrossover(1);

        System.out.println("Parent 1:");
        BinaryGenome j = new BinaryGenome(20,1);
        j.randomize();
        System.out.println(j.getData() + "\n");

        System.out.println("Parent 2:");
        BinaryGenome k = new BinaryGenome(20, 1);
        k.randomize();
        System.out.println(k.getData() + "\n");

        BinaryGenome mutated = op.crossover(j, k);
        System.out.println("Child:");
        System.out.println(mutated.getData());
    }
}
