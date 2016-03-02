package operators.melodic;

import genetics.MusicGenotype;
import genetics.MelodyContainer;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Olav on 01.02.2016.
 */
public class NoteModeMutator extends GeneticMutationOperator<MusicGenotype> {

    public NoteModeMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer ms = parent.getDeepCopy();
        MelodyContainer container = ms.melodyContainer;

        int index = rand.nextInt(container.melody.length);
        byte value = container.melody[index];
        int startIndex = container.getNoteStartIndex(index);
        byte startValue = container.melody[startIndex];

        if (value == MelodyContainer.MELODY_HOLD) {
            if (startValue != MelodyContainer.MELODY_REST && rand.nextDouble() < 0.5) {
                container.melody[index] = MelodyContainer.MELODY_REST;
            }
            else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MelodyContainer.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MelodyContainer.MELODY_RANGE + 1) + MelodyContainer.MELODY_RANGE_MIN);
                }
            }
        }
        else if (value == MelodyContainer.MELODY_REST) {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MelodyContainer.MELODY_HOLD;
            } else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MelodyContainer.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MelodyContainer.MELODY_RANGE + 1) + MelodyContainer.MELODY_RANGE_MIN);
                }
            }
        }
        else {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MelodyContainer.MELODY_HOLD;
            } else {
                container.melody[index] = MelodyContainer.MELODY_REST;
            }
        }
        MusicGenotype child = new MusicGenotype();
        child.setData(ms);
        if (container.containsInvalidPitches()) {
            throw new NullPointerException("LOL");
        }
        return child;
    }
}
