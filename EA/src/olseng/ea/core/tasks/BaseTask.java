package olseng.ea.core.tasks;

import olseng.ea.core.EA;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * Created by Olav on 12.01.2016.
 * Base task for handling threading in the algorithm. Also holds a Random object that is native to the task, which should be used in all random calculations to ensure threading is done optimally.
 */
public abstract class BaseTask<T> implements Callable<T> {

    protected final EA ea;
    protected Random rand;

    public BaseTask(EA ea) {
        this.ea = ea;
        this.rand = new Random();
    }

}
