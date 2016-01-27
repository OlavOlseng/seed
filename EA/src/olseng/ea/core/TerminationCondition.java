package olseng.ea.core;

/**
 * Created by Olav on 27.01.2016.
 */
public interface TerminationCondition {

    /**
     * This function should reset any internal state of the termination condition.
     */
    public void reset();

    public boolean shouldTerminate(EA ea);

}
