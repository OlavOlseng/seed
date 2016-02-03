package olseng.ea.bitvectest;


import olseng.ea.genetics.Genotype;

import java.util.BitSet;
import java.util.Random;

/**
 * Only supports fixed size genes atm.
 * Created by Olav on 03.03.2015.
 */
public class BinaryGenome extends Genotype<BitSet> {

    int geneCount;
    int geneSize;

    public BinaryGenome(int geneCount, int geneSize) {
        this(geneCount, geneSize, new BitSet());
    }

    public BinaryGenome(int geneCount, int geneSize, BitSet bitset) {
        this.geneCount = geneCount;
        this.geneSize = geneSize;
        this.setData(bitset);
    }

    public BitSet getGene(int geneIndex) {
        if (geneIndex >= geneCount) {
            throw new IndexOutOfBoundsException();
        }
        int max =  geneIndex * geneSize + geneSize;
        return getData().get(geneIndex * geneSize, max);
    }

    public void setGene(BitSet set, int geneIndex) {
        if (geneIndex >= geneCount) {
            throw new IndexOutOfBoundsException();
        }
        int index = geneIndex * geneSize;
        for (int i = 0; i < geneSize; i++) {
            getData().set(index + i, set.get(i));
        }
    }

    /**
     * Randomizes the entire genotype
     */
    public void randomize() {
        Random rand = new Random();
        for (int i = 0; i < geneCount * geneSize; i++) {
            getData().set(i, rand.nextDouble() < 0.5);
        }
    }

    /**
     * @return The amount of bits in the genome.
     */
    public int getSize() {
        return geneSize * geneCount;
    }

    public int calculateGeneCount(BitSet data) {
        return geneCount;
    }

    @Override
    public BitSet getDeepCopy() {
        return (BitSet) getData().clone();
    }

    @Override
    public void parseData(BitSet data) {

    }

    //============================TEST=================================
    public static void main(String[] args) {
        BinaryGenome g = new BinaryGenome(2, 2);
        g.getData().set(3, true);
        BitSet set = g.getData();
        System.out.println(set.length());
        for (int i = 0; i < g.geneCount * g.geneSize; i++) {
            System.out.println(g.getData().get(i));
        }
        System.out.println("LOL\n");
        BinaryGenome bsg = new BinaryGenome(1, 2);
        bsg.randomize();
        g.setGene(bsg.getGene(0), 0);
        for (int i = 0; i<g.geneCount * g.geneSize; i++) {
            System.out.println(g.getData().get(i));
        }
    }
}
