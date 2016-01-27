package olseng.ea.core;

/**
 * Created by Olav on 27.01.2016.
 */
public class FitnessSumTermination implements TerminationCondition {

    private final float cap;

    public FitnessSumTermination(float cap) {
        this.cap = cap;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean shouldTerminate(EA ea) {
        float sum = 0;
        for (int i = 0; i < ea.population.getIndividual(0).getFitnessCount(); i++) {
            sum += ea.population.getIndividual(0).getFitnessValue(i);
        }

        return sum >= cap;
    }
}
