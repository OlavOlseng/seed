package operators;

import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import java.util.Random;

/**
 * Created by Olav on 02.02.2016.
 */
public class PitchSwapMutator extends GeneticMutationOperator<MusicGenotype> {


    public PitchSwapMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer mc = parent.getDeepCopy();
        int index = rand.nextInt(mc.melody.length);
        int selectedNoteIndex = mc.getNoteStartIndex(index);
        int swapNote = mc.getNextNoteIndex(selectedNoteIndex);
        int previousNote = mc.getPreviousNoteIndex(selectedNoteIndex);
        MusicGenotype child = new MusicGenotype();

        if (swapNote < 0 || (rand.nextDouble() < 0.5 && previousNote > -1)) {
            swapNote = previousNote;
        }
        if (swapNote < 0) {
            System.out.println("Failed to swap notes, as there is less than two notes in the genotype.");
            child.setData(mc);
            return child;
        }
        byte buffer = mc.melody[selectedNoteIndex];
        mc.melody[selectedNoteIndex] = mc.melody[swapNote];
        mc.melody[swapNote] = buffer;

        //Ensure no consecutive pauses.
        mc.concatenateRests();

        child.setData(mc);
        return child;
    }


}
