package olseng.ea.core;

/**
 * Created by Olav on 27.01.2016.
 */
public class MaxGenerationCondition implements TerminationCondition {

    private final int cap;
    private int counter = 0;

    public MaxGenerationCondition(int cap) {
        this.cap = cap;
    }

    @Override
    public void reset() {
        counter = 0;
    }

    @Override
    public boolean shouldTerminate(EA ea) {
        counter++;
        if (counter >= cap) {
            return true;
        }
        return false;
    }

    public int getGenerationsPassed() {
        return this.counter;
    }
}
