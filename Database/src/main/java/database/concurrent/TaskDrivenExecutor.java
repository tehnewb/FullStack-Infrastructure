package database.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * TaskDrivenExecutor is a custom implementation of the ExecutorService interface that manages
 * tasks asynchronously while keeping track of the number of active tasks. It ensures that the
 * executor remains active until all submitted tasks are completed, providing a convenient way to
 * execute tasks concurrently in a multithreaded environment.
 *
 * @author Albert Beaupre
 * @version 1.0
 */
public class TaskDrivenExecutor implements ExecutorService {
    private final AtomicInteger activeTasks = new AtomicInteger(0);
    private ExecutorService executor;

    /**
     * Submits a Callable task for execution and returns a Future representing the pending result
     * of the task's execution.
     *
     * @param task the Callable task to be executed
     * @return a Future representing the pending result of the task's execution
     */
    @Override
    public <T> Future<T> submit(Callable<T> task) {
        incrementActiveTasks();
        Future<T> future = executor.submit(() -> {
            try {
                T result = task.call();
                return result;
            } finally {
                decrementActiveTasks();
            }
        });
        return future;
    }

    /**
     * Submits a Runnable task for execution and returns a Future representing the pending result
     * of the task's execution.
     *
     * @param task   the Runnable task to be executed
     * @param result the result to return upon successful completion of the task
     * @return a Future representing the pending result of the task's execution
     */
    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        incrementActiveTasks();
        Future<T> future = executor.submit(() -> {
            try {
                task.run();
                return result;
            } finally {
                decrementActiveTasks();
            }
        });
        return future;
    }

    /**
     * Submits a Runnable task for execution and returns a Future representing the pending completion
     * of the task.
     *
     * @param task the Runnable task to be executed
     * @return a Future representing the pending completion of the task
     */
    @Override
    public Future<?> submit(Runnable task) {
        incrementActiveTasks();
        Future<?> future = executor.submit(() -> {
            try {
                task.run();
            } finally {
                decrementActiveTasks();
            }
        });
        return future;
    }

    /**
     * Executes the given command in the executor.
     *
     * @param command the command to be executed
     */
    @Override
    public void execute(Runnable command) {
        incrementActiveTasks();
        executor.execute(() -> {
            try {
                command.run();
            } finally {
                decrementActiveTasks();
            }
        });
    }

    /**
     * Initiates an orderly shutdown of the executor, allowing previously submitted tasks to
     * complete while refusing new tasks.
     */
    @Override
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Attempts to stop all actively executing tasks, halts the processing of waiting tasks, and
     * returns a list of the tasks that were awaiting execution.
     *
     * @return a list of the tasks that were awaiting execution
     */
    @Override
    public List<Runnable> shutdownNow() {
        return executor.shutdownNow();
    }

    /**
     * Returns true if the executor has been shut down.
     *
     * @return true if the executor has been shut down
     */
    @Override
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    /**
     * Returns true if all tasks have completed after shutdown.
     *
     * @return true if all tasks have completed after shutdown
     */
    @Override
    public boolean isTerminated() {
        return executor.isTerminated();
    }

    /**
     * Blocks until all tasks have completed execution after a shutdown request, or the timeout
     * occurs, or the current thread is interrupted, whichever happens first.
     *
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return true if this executor terminated and false if the timeout elapsed before termination
     * @throws InterruptedException if interrupted while waiting
     */
    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * Invokes all the given tasks, waiting for each to complete before proceeding to the next one,
     * and returns a list of Futures representing the tasks.
     *
     * @param tasks the collection of tasks
     * @return a list of Futures representing the tasks
     * @throws InterruptedException if any thread interrupted the current thread while waiting
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executor.invokeAll(tasks);
    }

    /**
     * Invokes all the given tasks, waiting for each to complete before proceeding to the next one,
     * and returns a list of Futures representing the tasks. The operation times out after the
     * specified timeout.
     *
     * @param tasks   the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return a list of Futures representing the tasks
     * @throws InterruptedException if any thread interrupted the current thread while waiting
     */
    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return executor.invokeAll(tasks, timeout, unit);
    }

    /**
     * Invokes one of the given tasks and returns the result of that task.
     *
     * @param tasks the collection of tasks
     * @return the result of one of the tasks
     * @throws InterruptedException if any thread interrupted the current thread while waiting
     * @throws ExecutionException   if no task successfully completes
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return executor.invokeAny(tasks);
    }

    /**
     * Invokes one of the given tasks and returns the result of that task. The operation times out
     * after the specified timeout.
     *
     * @param tasks   the collection of tasks
     * @param timeout the maximum time to wait
     * @param unit    the time unit of the timeout argument
     * @return the result of one of the tasks
     * @throws InterruptedException if any thread interrupted the current thread while waiting
     * @throws ExecutionException   if no task successfully completes
     * @throws TimeoutException     if the wait timed out
     */
    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return executor.invokeAny(tasks, timeout, unit);
    }

    /**
     * Increments the count of active tasks and creates a new single-threaded executor if necessary.
     */
    private void incrementActiveTasks() {
        activeTasks.incrementAndGet();

        if (executor == null || isShutdown() || isTerminated()) {
            executor = Executors.newCachedThreadPool();
        }
    }

    /**
     * Decrements the count of active tasks and shuts down the executor if no tasks are active.
     */
    private void decrementActiveTasks() {
        if (activeTasks.decrementAndGet() == 0) {
            shutdown();
        }
    }
}