package olseng.ea.core.tasks;

import olseng.ea.core.EA;

import java.util.concurrent.Callable;

/**
 * Created by Olav on 12.01.2016.
 */
public abstract class BaseTask<T> implements Callable<T> {

    protected final EA ea;

    public BaseTask(EA ea) {
        this.ea = ea;
    }

}
