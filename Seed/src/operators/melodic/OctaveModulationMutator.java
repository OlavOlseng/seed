package operators.melodic;

import genetics.MelodyGenotype;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;

import java.util.ArrayList;
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
        MelodyGenotype mc = ms.melodyGenotype;
        if(mc.melodyContainsPitch()) {

            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < mc.melody.length; i++) {
                if (mc.melody[i] >= MelodyGenotype.MELODY_RANGE_MIN) {
                    indices.add(i);
                }
            }

            int noteIndex = indices.get(rand.nextInt(indices.size()));
            int pitchValue = mc.melody[noteIndex];

            byte toModulate = (byte) 12;

            if (rand.nextDouble() < 0.5) {
                toModulate *= -1;
            }

            pitchValue = (pitchValue + toModulate) % MelodyGenotype.MELODY_RANGE + MelodyGenotype.MELODY_RANGE_MIN;

            mc.melody[noteIndex] = (byte)pitchValue;
        }
        else {
            System.out.println("PitchModulationMutator failed, as there are no pitches in the melody.");
        }
        MusicGenotype mg = new MusicGenotype();
        mg.setData(ms);
        return mg;
    }
}
