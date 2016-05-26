package tests;

import fitness.*;
import genetics.*;
import olseng.ea.EAFactory;
import olseng.ea.adultselection.RankedTournamentSelector;
import olseng.ea.core.EA;
import olseng.ea.core.Population;
import olseng.ea.fitness.ranking.FastNonDominatedSort;
import olseng.ea.fitness.ranking.RankComparator;
import olseng.ea.genetics.OperatorPool;
import olseng.ea.genetics.Phenotype;
import operators.crossover.SingleBarCrossover;
import operators.crossover.SinglePointCrossover;
import operators.harmonic.ChordChangeMutator;
import operators.harmonic.ChordPitchModulatorMutator;
import operators.harmonic.ChordSwapMutator;
import operators.melodic.*;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import util.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Olav on 02.03.2016.
 */
public class AllObjectiveTest {

    private static final int POPULATION_SIZE = 600;
    private static final int GENERATIONS = 10000;
    private static final int BAR_COUNT = 16;
    private static final int BAR_BAKE_COUNT = 16;
    private static final int POPULATION_SIZE_RANDOMS = 100;
    private static final boolean BAKE = false;
    private static final boolean USE_SEED = true;
    private static final boolean MELODY_OPERATORS_ENABLED = true;
    private static final boolean HARMONY_OPERATORS_ENABLED = true;


    public static void main(String[] args) {
        OperatorPool<MusicGenotype> op = new OperatorPool<>();
        if (MELODY_OPERATORS_ENABLED) {
            op.addOperator(new NoteModeMutator(2));
            op.addOperator(new NotePositionMutator(1));
            op.addOperator(new RandomPitchMutator(2));
            op.addOperator(new PitchModulationMutator(2));
            op.addOperator(new HalfMeasureDuplicatorMutator(0.5));
        }

        if (HARMONY_OPERATORS_ENABLED) {
            op.addOperator(new ChordChangeMutator(2));
            op.addOperator(new ChordPitchModulatorMutator(2));
            op.addOperator(new ChordSwapMutator(1));
        }

        op.addOperator(new SingleBarCrossover(1));
        op.addOperator(new SinglePointCrossover(2));
        op.setCrossoverProbability(0.5);

        EAFactory<MusicGenotype, MusicPhenotype> factory = new EAFactory<>();

        StatiscalMelodyObjective tObjective = new StatiscalMelodyObjective();

        factory.addFitnessObjective(new MelodicVoiceObjective());
        factory.addFitnessObjective(tObjective);
        //factory.addFitnessObjective(new PatternObjective());
        factory.addFitnessObjective(new HarmonizationObjective());
        factory.addFitnessObjective(new HarmonicProgressionObjective());

        factory.developmentalMethod = new MusicDevelopmentalMethod();
        factory.operatorPool = op;
        factory.adultSelector = new RankedTournamentSelector(2, 0.1);
        factory.rankingModule = new FastNonDominatedSort();
        factory.sortingModule = new RankComparator();

        EA<MusicGenotype, MusicPhenotype> ea = factory.build();
        ea.setThreadCount(32);
        ea.populationMaxSize = POPULATION_SIZE;
        ea.populationElitism = 1;
        ea.allowMutationAndCrossover = true;


        MusicalKey key = new MusicalKey(0, MusicalKey.Mode.MINOR);


        MusicalContainer music = new MusicalContainer(BAR_BAKE_COUNT, key);
        music.init();
        ChordContainer hg = music.chordContainer;
        hg.init();

        /*
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(2, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(4, 3, 1, key, true);
        hg.chords[3] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[4] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[5] = ChordBuilder.getChord(2, 3, 1, key);
        hg.chords[6] = ChordBuilder.getChord(4, 4, 1, key, true);
        hg.chords[7] = ChordBuilder.getChord(0, 3, 1, key);

        /*
        hg.chords[0] = ChordBuilder.getChord(0, 3, 1, key);
        hg.chords[1] = ChordBuilder.getChord(1, 3, 1, key);
        hg.chords[2] = ChordBuilder.getChord(2, 3, 1, key);
        hg.chords[3] = ChordBuilder.getChord(3, 3, 1, key);
        hg.chords[4] = ChordBuilder.getChord(4, 3, 1, key);
        hg.chords[5] = ChordBuilder.getChord(5, 3, 1, key);
        hg.chords[6] = ChordBuilder.getChord(6, 3, 1, key);
        hg.chords[7] = ChordBuilder.getChord(7, 3, 1, key);
        */


        MelodyContainer mc = music.melodyContainer;

        mc.init();
        //MusicBank.putLisaMelody(mc);
        MusicBank.putHitMe(mc);


        MusicGenotype initialSeed = new MusicGenotype(music);
        List<Phenotype> initialPop = new ArrayList<>();
        MusicPhenotype p = ea.developmentalMethod.develop(initialSeed);
        ea.fitnessEvaluator.evaluate(p);
        if (BAKE) {
            tObjective.bake(p);
        }

        MusicParser parser = new MusicParser();
        String melody = parser.parseMelody(music.melodyContainer);
        System.out.println(melody);
        melody = " Rw | " + melody;
        String chords = "Rw | " + parser.parseChords(music.chordContainer);
        System.out.println(chords);
        System.out.println(tObjective.getEvaluationString(p));
        Pattern pMelody = new Pattern(melody).setVoice(0).setInstrument(4);
        Pattern pHarmony = new Pattern(chords).setVoice(1).setInstrument(0);
        Player player = new Player();
       // player.play(pMelody, pHarmony);


        /*
        System.out.println(p.getFitnessValue(0));
        music = new MusicalContainer(BAR_COUNT, key);
        music.init();
        MusicBank.putHitMe(music.melodyContainer);
        */
        p = ea.developmentalMethod.develop(new MusicGenotype(music));
        ea.fitnessEvaluator.evaluate(p);

        if (USE_SEED) {
            initialPop.add(p);
        }

        for (int i = 0; i < POPULATION_SIZE_RANDOMS; i++) {
            music = new MusicalContainer(BAR_COUNT, key);
            music.init();
            music.randomize(new Random());
            initialSeed = new MusicGenotype(music);
            p = ea.developmentalMethod.develop(initialSeed);
            ea.fitnessEvaluator.evaluate(p);
            initialPop.add(p);
        }

        Population pop = new Population(10);
        pop.setPopulation(initialPop);

        ea.initialize(pop);

        double startTime = System.currentTimeMillis();

        for (int i = 0; i < GENERATIONS; i++) {
            System.out.println("Running generation: " + i);
            System.out.println("Pop size: " + pop.getPopulationSize());
            ea.step();
        }

        double runTime = (double) System.currentTimeMillis() - startTime;
        System.out.println("Elapsed runtime: " + runTime / 60000. + ":" + (runTime / 1000.0) % 60);

        ea.terminateThreads();

        while(true) {
            int index = 0;
            if (true) {
                for (int i = 0; i < 25; i++) {
                    System.out.println(i + ":" + ea.population.getIndividual(i));
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Select and index to play...");
                try {
                    String input = br.readLine();
                    if (input.contains("-w")) {
                        System.out.println("Writing files... ");

                        String[] splitInput = input.split(" ");
                        index = Integer.parseInt(String.valueOf(splitInput[2]));
                        music = (MusicalContainer) ea.population.getIndividual(index).getRepresentation();
                        MidiWriter.WritePatternToMidi(music, splitInput[1]);
                        continue;
                    }

                    index = Integer.parseInt(String.valueOf(input));
                    if (index == -1) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                System.out.println(tObjective.getEvaluationString((MusicPhenotype) ea.population.getIndividual(index)));
                System.out.println(ea.population.getIndividual(index).getRepresentation());
                System.out.println(ea.population.getIndividual(index));

                parser = new MusicParser();
                music = (MusicalContainer) ea.population.getIndividual(index).getRepresentation();
                melody = parser.parseMelody(music.melodyContainer);
                System.out.println(melody);
                melody = " Rw | " + melody;
                chords = "Rw | " + parser.parseChords(music.chordContainer);
                System.out.println(chords);
                System.out.println("Half measure counts: " + ((MusicPhenotype)(ea.population.getIndividual(index))).halfMeasureRhythmicPatterns.values());
                System.out.println("Whole measure counts: " + ((MusicPhenotype)(ea.population.getIndividual(index))).wholeMeasureRhythmicPatterns.values());
                System.out.println("Measure patterns: " + Arrays.toString(((MusicPhenotype) ea.population.getIndividual(index)).sequentialMeasurePatterns));
                System.out.println("Rest patterns: " + Arrays.toString(((MusicPhenotype) ea.population.getIndividual(index)).sequentialMeasureRestPatterns));
                pMelody = new Pattern(melody).setVoice(0).setInstrument(4);
                pHarmony = new Pattern(chords).setVoice(1).setInstrument(1);
                player = new Player();
                player.play(pMelody, pHarmony);
            }
        }
    }
}
