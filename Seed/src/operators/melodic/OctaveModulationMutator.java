package operators.melodic;

import genetics.MelodyContainer;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import sun.plugin.dom.exception.InvalidStateException;
import util.MusicalKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class OctaveModulationMutator extends GeneticMutationOperator<MusicGenotype> {


    public OctaveModulationMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer ms = parent.getDeepCopy();
        MelodyContainer mc = ms.melodyContainer;
        if(mc.melodyContainsPitch()) {

            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < mc.melody.length; i++) {
                if (mc.melody[i] >= MelodyContainer.MELODY_RANGE_MIN) {
                    indices.add(i);
                }
            }

            int noteIndex = indices.get(rand.nextInt(indices.size()));
            int pitchValue = mc.melody[noteIndex];

            byte toModulate = (byte) 12;

            if (rand.nextDouble() < 0.5) {
                toModulate *= -1;
            }

            pitchValue = ((pitchValue + toModulate) % MelodyContainer.MELODY_RANGE) + MelodyContainer.MELODY_RANGE_MIN;

            if (pitchValue > MelodyContainer.MELODY_RANGE_MAX ||pitchValue < MelodyContainer.MELODY_RANGE_MIN) {
                System.out.println("Created invalid pitch.");
            }
            mc.melody[noteIndex] = (byte) pitchValue;
        }
        else {
            System.out.println("PitchModulationMutator failed, as there are no pitches in the melody.");
        }
        MusicGenotype mg = new MusicGenotype();
        mg.setData(ms);

        return mg;
    }

    public static void main(String[] args) {
        MusicalContainer container = new MusicalContainer(1, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        container.init();
        container.randomize(new Random());
        container.melodyContainer.melody[2] = MelodyContainer.MELODY_RANGE_MIN + 12;

        System.out.println(Arrays.toString(container.melodyContainer.melody));

        MusicGenotype mg = new MusicGenotype();
        mg.setData(container);
        OctaveModulationMutator omm = new OctaveModulationMutator(1);

        mg = omm.mutate(mg, new Random());
        System.out.println(Arrays.toString(mg.getData().melodyContainer.melody));
    }
}
