package olseng.ea.bitvectest;

import olseng.ea.genetics.DevelopmentalMethod;

import java.util.ArrayList;

/**
 * Created by Olav on 03/03/2015.
 */
public class BitToIntVec implements DevelopmentalMethod<BinaryGenome,IntVec> {

    private int maxVal = 0;

    public BitToIntVec setMaxVal(int val) {
        this.maxVal = val;
        return this;
    }

    @Override
    public IntVec develop(BinaryGenome g) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < g.geneCount; i++) {
            int val = 0;
            long[] gene = g.getGene(i).toLongArray();
            if(gene.length > 0) {
                val += (int)gene[0];
            }
            if (maxVal != 0) {
                val %= maxVal;
            }
            list.add(val);
        }
        IntVec result = new IntVec(g);
        result.setRepresentation(list);
        return result;
    }

    public static void main(String[] args) {
        BinaryGenome g = new BinaryGenome(2,8);
        g.getData().flip(0, 14);
        DevelopmentalMethod dm = new BitToIntVec();
        ((BitToIntVec)dm).maxVal = 256;
        IntVec res = ((BitToIntVec) dm).develop(g);
        res.initFitnessValues(1);
        System.out.println(res);
    }
}


