package operators.harmonic;

import genetics.ChordContainer;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import util.ChordBuilder;
import util.MusicalKey;

import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class ChordChangeMutator extends GeneticMutationOperator<MusicGenotype> {

    public ChordChangeMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer mc = parent.getDeepCopy();
        ChordContainer cc = mc.chordContainer;

        int chordIndex = rand.nextInt(cc.bars);
        boolean triad = rand.nextDouble() < 0.5;
        int pitches = triad ? 3 : 4;
        int padding = triad ? 1 : 0;

        cc.chords[chordIndex] = ChordBuilder.getChord(rand.nextInt(mc.key.scale.length), pitches, padding, mc.key);

        return new MusicGenotype(mc);
    }

    @Override
    public boolean isApplicable(MusicGenotype genotype) {
        return true;
    }

    public static void main(String[] args) {
        MusicalContainer mc = new MusicalContainer(4, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        mc.init();
        mc.randomize(new Random());

        System.out.println(mc.chordContainer);

        MusicGenotype mg = new MusicGenotype(mc);
        ChordChangeMutator csm = new ChordChangeMutator(1);
        mg = csm.mutate(mg, new Random());

        System.out.println(mg.getData());
    }
}
