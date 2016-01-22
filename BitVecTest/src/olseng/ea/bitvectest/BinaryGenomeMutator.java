package olseng.ea.bitvectest;

import olseng.ea.genetics.GeneticMutationOperator;

import java.util.Random;

/**
 * Created by Olav on 22.01.2016.
 */
public class BinaryGenomeMutator extends GeneticMutationOperator<BinaryGenome> {

    public BinaryGenomeMutator(double weight) {
        super(weight);
    }

    @Override
    public BinaryGenome mutate(BinaryGenome parent, Random rand) {
        BinaryGenome child = new BinaryGenome(parent.geneCount, parent.geneSize, parent.getDeepCopy());
        int geneIndex = rand.nextInt(child.geneCount);
        int bitIndex = rand.nextInt(child.geneSize);
        child.getData().flip(geneIndex * child.geneSize + bitIndex);
        return child;
    }

    public static void main(String[] args) {
        //Mutation test
        BinaryGenomeMutator go = new BinaryGenomeMutator(1);
        BinaryGenome j = new BinaryGenome(20,1);
        j.randomize();
        System.out.println(j.getData());
        BinaryGenome mutated = go.mutate(j, new Random());
        System.out.println(mutated.getData());

    }
}
