package operators;

import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
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
        MusicalContainer container = parent.getDeepCopy();

        int index = rand.nextInt(container.melody.length);
        byte value = container.melody[index];
        int startIndex = container.getNoteStartIndex(index);
        byte startValue = container.melody[startIndex];

        if (value == MusicalContainer.MELODY_HOLD) {
            if (startValue != MusicalContainer.MELODY_REST && rand.nextDouble() < 0.5) {
                container.melody[index] = MusicalContainer.MELODY_REST;
            }
            else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MusicalContainer.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MusicalContainer.MELODY_RANGE + 1) + MusicalContainer.MELODY_RANGE_MIN);
                }
            }
        }
        else if (value == MusicalContainer.MELODY_REST) {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MusicalContainer.MELODY_HOLD;
            } else {
                int previousNoteIndex = container.getPreviousNoteIndex(startIndex);
                if (previousNoteIndex > -1 && container.melody[previousNoteIndex] >= MusicalContainer.MELODY_RANGE_MIN) {
                    container.melody[index] = container.melody[previousNoteIndex];
                }
                else {
                    container.melody[index] = (byte) (rand.nextInt(MusicalContainer.MELODY_RANGE + 1) + MusicalContainer.MELODY_RANGE_MIN);
                }
            }
        }
        else {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MusicalContainer.MELODY_HOLD;
            } else {
                container.melody[index] = MusicalContainer.MELODY_REST;
            }
        }
        MusicGenotype child = new MusicGenotype();
        child.setData(container);
        return child;
    }
}
