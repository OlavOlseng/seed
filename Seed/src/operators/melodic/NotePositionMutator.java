package operators.melodic;

import genetics.MusicGenotype;
import genetics.MelodyContainer;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.Random;

/**
 * Created by Olav on 02.02.2016.
 */
public class NotePositionMutator extends GeneticMutationOperator<MusicGenotype> {

    /**
     * This operator moves the position of a note, and swaps in the value of the overwritten position.
     * @param weight
     */
    public NotePositionMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer ms = parent.getDeepCopy();
        MelodyContainer mc = ms.melodyContainer;
        int index = rand.nextInt(mc.melody.length);
        int selectedNoteIndex = mc.getNoteStartIndex(index);
        int movement = rand.nextInt(15) + 1;
        movement = rand.nextDouble() < 0.5 ? movement : -movement;
        int swapIndex = (MelodyContainer.MELODY_BAR_SUBDIVISION * MelodyContainer.MELODY_FOURTH_SUBDIVISION * ms.bars + selectedNoteIndex + movement) % (MelodyContainer.MELODY_BAR_SUBDIVISION * MelodyContainer.MELODY_FOURTH_SUBDIVISION * ms.bars);
        MusicGenotype child = new MusicGenotype();

        byte buffer = mc.melody[selectedNoteIndex];
        mc.melody[selectedNoteIndex] = mc.melody[swapIndex];
        mc.melody[swapIndex] = buffer;

        //Ensure no consecutive pauses.
        mc.concatenateRests();
        if (mc.melody[0] == MelodyContainer.MELODY_HOLD) {
            mc.melody[0] = MelodyContainer.MELODY_REST;
        }
        child.setData(ms);

        if (mc.containsInvalidPitches()) {
            System.out.println("Invalid pitch detected!");
        }
        return child;
    }

    @Override
    public boolean isApplicable(MusicGenotype g) {
        int pitches = 0;
        for (byte pitch : g.getData().melodyContainer.melody) {
            if (pitch >= MelodyContainer.MELODY_RANGE_MIN && pitch <= MelodyContainer.MELODY_RANGE_MAX) {
                pitches++;
            }
        }
        return pitches >= 2;
    }
}
