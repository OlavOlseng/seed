package operators.harmonic;

import genetics.ChordContainer;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import util.MusicalKey;

import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class ChordSwapMutator extends GeneticMutationOperator<MusicGenotype> {

    public ChordSwapMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {

        MusicalContainer mc = parent.getDeepCopy();
        ChordContainer hg = mc.chordContainer;

        int chord1Index = rand.nextInt(hg.chords.length);
        int chord2Index = rand.nextDouble() > 0.5 ? hg.getNextChord(chord1Index) : hg.getPreviousChord(chord1Index);

        byte[] buffer = hg.chords[chord1Index];
        hg.chords[chord1Index] = hg.chords[chord2Index];
        hg.chords[chord2Index] = buffer;

        return new MusicGenotype(mc);
    }

    public static void main(String[] args){
        MusicalContainer mc = new MusicalContainer(4, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        mc.init();
        mc.randomize(new Random());

        System.out.println(mc.chordContainer);

        MusicGenotype mg = new MusicGenotype(mc);
        ChordSwapMutator csm = new ChordSwapMutator(1);
        mg = csm.mutate(mg, new Random());

        System.out.println(mg.getData());

    }
}
