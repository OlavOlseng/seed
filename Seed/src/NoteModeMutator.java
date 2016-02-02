import olseng.ea.genetics.GeneticMutationOperator;

import java.util.Arrays;
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
                container.melody[index] = (byte)(rand.nextInt(MusicalContainer.MELODY_RANGE + 1) + MusicalContainer.MELODY_RANGE_MIN);
            }
        }
        else if (value == MusicalContainer.MELODY_REST) {
            if (rand.nextDouble() < 0.5 && startIndex > 0) {
                container.melody[index] = MusicalContainer.MELODY_HOLD;
            } else {
                container.melody[index] = (byte) (rand.nextInt(MusicalContainer.MELODY_RANGE + 1) + MusicalContainer.MELODY_RANGE_MIN);
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

    public static void main(String[] args) {
        MusicalContainer mc = new MusicalContainer(1);
        mc.init();
        MusicGenotype mg = new MusicGenotype();
        mg.setData(mc);
        NoteModeMutator nmm = new NoteModeMutator(1);
        Random rand = new Random();
        System.out.println(Arrays.toString(mc.melody));
        for (int i = 0; i < 50; i++) {
                mg = nmm.mutate(mg, rand);
                System.out.println(Arrays.toString(mg.getData().melody));
        }
    }
}
