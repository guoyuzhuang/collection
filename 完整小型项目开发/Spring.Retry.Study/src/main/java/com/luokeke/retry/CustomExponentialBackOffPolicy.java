package com.luokeke.retry;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.backoff.BackOffContext;
import org.springframework.retry.backoff.BackOffInterruptedException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.Sleeper;
import org.springframework.retry.backoff.SleepingBackOffPolicy;
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.util.ClassUtils;

/**
 * Implementation of {@link BackOffPolicy} that increases the back off period for each
 * retry attempt in a given set using the {@link Math#exp(double) exponential} function.
 *
 * This implementation is thread-safe and suitable for concurrent access. Modifications to
 * the configuration do not affect any retry sets that are already in progress.
 *
 * The {@link #setInitialInterval(long)} property controls the initial value passed to
 * {@link Math#exp(double)} and the {@link #setMultiplier(double)} property controls by
 * how much this value is increased for each subsequent attempt.
 *
 * @author Rob Harrop
 * @author Dave Syer
 * @author Gary Russell
 * @author Artem Bilan
 */
@SuppressWarnings("serial")
public class CustomExponentialBackOffPolicy implements SleepingBackOffPolicy<CustomExponentialBackOffPolicy> {

	protected final Log logger = LogFactory.getLog(this.getClass());
	/**
	 * The default 'initialInterval' value - 100 millisecs. Coupled with the default
	 * 'multiplier' value this gives a useful initial spread of pauses for 1-5 retries.
	 */
	public static final long DEFAULT_INITIAL_INTERVAL = 100L;

	/**
	 * The default maximum backoff time (30 seconds).
	 */
	public static final long DEFAULT_MAX_INTERVAL = 30000L;

	/**
	 * The default 'multiplier' value - value 2 (100% increase per backoff).
	 */
	public static final double DEFAULT_MULTIPLIER = 2;

	/**
	 * The initial sleep interval.
	 */
	private volatile long initialInterval = DEFAULT_INITIAL_INTERVAL;

	/**
	 * The maximum value of the backoff period in milliseconds.
	 */
	private volatile long maxInterval = DEFAULT_MAX_INTERVAL;

	/**
	 * The value to increment the exp seed with for each retry attempt.
	 */
	private volatile List<Integer> multiplier = Arrays.asList();

	private Sleeper sleeper = new ThreadWaitSleeper();

	/**
	 * Public setter for the {@link Sleeper} strategy.
	 * @param sleeper the sleeper to set defaults to {@link ThreadWaitSleeper}.
	 */
	public void setSleeper(Sleeper sleeper) {
		this.sleeper = sleeper;
	}

	public CustomExponentialBackOffPolicy withSleeper(Sleeper sleeper) {
		CustomExponentialBackOffPolicy res = newInstance();
		cloneValues(res);
		res.setSleeper(sleeper);
		return res;
	}

	protected CustomExponentialBackOffPolicy newInstance() {
		return new CustomExponentialBackOffPolicy();
	}

	protected void cloneValues(CustomExponentialBackOffPolicy target) {
		target.setInitialInterval(getInitialInterval());
		target.setMaxInterval(getMaxInterval());
		target.setMultiplier(getMultiplier());
		target.setSleeper(sleeper);
	}

	/**
	 * Set the initial sleep interval value. Default is {@code 100} millisecond. Cannot be
	 * set to a value less than one.
	 *
	 * @param initialInterval the initial interval
	 */
	public void setInitialInterval(long initialInterval) {
		this.initialInterval = (initialInterval > 1 ? initialInterval : 1);
	}

	/**
	 * Set the multiplier value. Default is '<code>2.0</code>'. Hint: do not use values
	 * much in excess of 1.0 (or the backoff will get very long very fast).
	 * @param multiplier the multiplier
	 */
	public void setMultiplier(List<Integer> multiplier) {
		this.multiplier = multiplier;
	}

	/**
	 * Setter for maximum back off period. Default is 30000 (30 seconds). the value will
	 * be reset to 1 if this method is called with a value less than 1. Set this to avoid
	 * infinite waits if backing off a large number of times (or if the multiplier is set
	 * too high).
	 *
	 * @param maxInterval in milliseconds.
	 */
	public void setMaxInterval(long maxInterval) {
		this.maxInterval = maxInterval > 0 ? maxInterval : 1;
	}

	/**
	 * The initial period to sleep on the first backoff.
	 * @return the initial interval
	 */
	public long getInitialInterval() {
		return initialInterval;
	}

	/**
	 * The maximum interval to sleep for. Defaults to 30 seconds.
	 *
	 * @return the maximum interval.
	 */
	public long getMaxInterval() {
		return maxInterval;
	}

	/**
	 * The multiplier to use to generate the next backoff interval from the last.
	 *
	 * @return the multiplier in use
	 */
	public List<Integer> getMultiplier() {
		return multiplier;
	}

	/**
	 * Returns a new instance of {@link BackOffContext} configured with the 'expSeed' and
	 * 'increment' values.
	 */
	public BackOffContext start(RetryContext context) {
		return new CustomExponentialBackOffContext(this.multiplier);
	}

	/**
	 * Pause for a length of time equal to ' <code>exp(backOffContext.expSeed)</code>'.
	 */
	public void backOff(BackOffContext backOffContext) throws BackOffInterruptedException {
		CustomExponentialBackOffContext context = (CustomExponentialBackOffContext) backOffContext;
		try {
			long sleepTime = context.getSleepAndIncrement();
			if (logger.isDebugEnabled()) {
				logger.debug("Sleeping for " + sleepTime);
			}
			sleeper.sleep(sleepTime);
		} catch (InterruptedException e) {
			throw new BackOffInterruptedException("Thread interrupted while sleeping", e);
		}
	}

	static class CustomExponentialBackOffContext implements BackOffContext {
		private final List<Integer> multiplier;
		private int count = 0;

		public CustomExponentialBackOffContext(List<Integer> multiplier) {
			super();
			this.multiplier = multiplier;
		}

		public synchronized int getSleepAndIncrement() {
			int sleep = 0;
			if (multiplier.size() > count) {
				sleep = multiplier.get(count);
			} else {
				sleep = multiplier.get(multiplier.size() - 1);
			}
			count++;
			return sleep * 1000;
		}
	}

	public String toString() {
		return ClassUtils.getShortName(getClass()) + "[initialInterval=" + initialInterval + ", multiplier="
				+ multiplier + ", maxInterval=" + maxInterval + "]";
	}

}
