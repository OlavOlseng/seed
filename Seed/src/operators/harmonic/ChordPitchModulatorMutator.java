package operators.harmonic;

import genetics.HarmonyGenotype;
import genetics.MelodyGenotype;
import genetics.MusicGenotype;
import genetics.MusicalContainer;
import olseng.ea.genetics.GeneticMutationOperator;
import util.MusicalKey;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Olav on 24.02.2016.
 */
public class ChordPitchModulatorMutator extends GeneticMutationOperator<MusicGenotype> {


    public ChordPitchModulatorMutator(double weight) {
        super(weight);
    }

    @Override
    public MusicGenotype mutate(MusicGenotype parent, Random rand) {
        MusicalContainer mc = parent.getDeepCopy();
        HarmonyGenotype hc = mc.harmonyGenotype;

        int chordIndex = rand.nextInt(hc.bars);
        byte[] chord = hc.chords[chordIndex];
        int pitchIndex = rand.nextInt(chord.length);

        if (pitchIndex == chord.length - 1) {
            if (chord[pitchIndex] == HarmonyGenotype.NO_PITCH) {
                chord[pitchIndex] = (byte) rand.nextInt(12);
            }
            else if (rand.nextDouble() < 0.5){
                byte modulation = rand.nextDouble() < 0.5 ? (byte) -1 : (byte) 1;
                byte newValue = chord[pitchIndex];

                do {
                    newValue = (byte) ((newValue + modulation + 12) % 12);
                } while(hc.chordHasPitch(chordIndex, newValue));

                chord[pitchIndex] = newValue;
            }
            else{
                chord[pitchIndex] = HarmonyGenotype.NO_PITCH;
            }
        }
        else {
            byte modulation = rand.nextDouble() < 0.5 ? (byte) -1 : (byte) 1;
            byte newValue = chord[pitchIndex];

            do {
                newValue = (byte) ((newValue + modulation + 12) % 12);
            } while(hc.chordHasPitch(chordIndex, newValue));

            chord[pitchIndex] = newValue;
        }
        return new MusicGenotype(mc);
    }

    public static void main(String[] args) {
        MusicalContainer container = new MusicalContainer(1, new MusicalKey(0, MusicalKey.Mode.MAJOR));
        container.init();
        container.randomize(new Random());
        container.melodyGenotype.melody[2] = MelodyGenotype.MELODY_RANGE_MIN + 12;

        System.out.println(container.harmonyGenotype);

        MusicGenotype mg = new MusicGenotype();
        mg.setData(container);
        ChordPitchModulatorMutator omm = new ChordPitchModulatorMutator(1);

        mg = omm.mutate(mg, new Random());
        System.out.println(mg.getData().harmonyGenotype);
    }
}
