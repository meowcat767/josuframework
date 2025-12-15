package osu.framework.configuration;

import osu.framework.bindables.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract base class for configuration management with type-safe enum-based
 * lookups.
 * 
 * @param <TLookup> The enum type used for configuration keys.
 */
public abstract class ConfigManager<TLookup extends Enum<TLookup>> implements IConfigManager, AutoCloseable {
    /**
     * Whether user-specified configuration elements should be set even though a
     * default was never specified.
     */
    protected boolean addMissingEntries = true;

    private final Map<TLookup, Object> defaultOverrides;
    protected final Map<TLookup, IBindable<?>> configStore = new HashMap<>();

    private boolean hasLoaded = false;
    private final AtomicInteger lastSave = new AtomicInteger(0);
    private final Object saveLock = new Object();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * Creates a new ConfigManager.
     * 
     * @param defaultOverrides Optional overrides for default values.
     */
    protected ConfigManager(Map<TLookup, Object> defaultOverrides) {
        this.defaultOverrides = defaultOverrides;
    }

    /**
     * Creates a new ConfigManager without default overrides.
     */
    protected ConfigManager() {
        this(null);
    }

    @Override
    public void load() {
        performLoad();
        hasLoaded = true;
    }

    /**
     * Queue a background save operation with debounce.
     */
    protected void queueBackgroundSave() {
        int current = lastSave.incrementAndGet();

        scheduler.schedule(() -> {
            if (current == lastSave.get()) {
                save();
            }
        }, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean save() {
        if (!hasLoaded)
            return false;

        synchronized (saveLock) {
            lastSave.incrementAndGet();
            return performSave();
        }
    }

    /**
     * Set all required default values via setDefault() calls.
     * Note that defaults set here may be overridden by defaultOverrides provided in
     * the constructor.
     */
    protected abstract void initialiseDefaults();

    /**
     * Performs the actual load operation.
     */
    protected abstract void performLoad();

    /**
     * Performs the actual save operation.
     * 
     * @return True if save was successful.
     */
    protected abstract boolean performSave();

    /**
     * Sets a configuration's value.
     * 
     * @param lookup The lookup key.
     * @param value  The value. Will also become the default value if one has not
     *               already been initialised.
     */
    public <TValue> void setValue(TLookup lookup, TValue value) {
        Bindable<TValue> bindable = getOriginalBindable(lookup);

        if (bindable == null) {
            setDefault(lookup, value);
        } else {
            bindable.setValue(value);
        }
    }

    /**
     * Gets a configuration value.
     * 
     * @param lookup The lookup key.
     * @return The current value.
     */
    @SuppressWarnings("unchecked")
    public <TValue> TValue get(TLookup lookup) {
        Bindable<TValue> bindable = getOriginalBindable(lookup);
        return bindable != null ? bindable.getValue() : null;
    }

    /**
     * Sets a configuration's default value (double).
     */
    protected BindableDouble setDefault(TLookup lookup, double value, Double min, Double max, Double precision) {
        value = getDefault(lookup, value);

        BindableDouble bindable = (BindableDouble) getOriginalBindable(lookup);
        if (bindable == null) {
            bindable = new BindableDouble(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);
        if (min != null)
            bindable.setMinValue(min);
        if (max != null)
            bindable.setMaxValue(max);
        if (precision != null)
            bindable.setPrecision(precision);

        return bindable;
    }

    /**
     * Sets a configuration's default value (float).
     */
    protected BindableFloat setDefault(TLookup lookup, float value, Float min, Float max, Float precision) {
        value = getDefault(lookup, value);

        BindableFloat bindable = (BindableFloat) getOriginalBindable(lookup);
        if (bindable == null) {
            bindable = new BindableFloat(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);
        if (min != null)
            bindable.setMinValue(min);
        if (max != null)
            bindable.setMaxValue(max);
        if (precision != null)
            bindable.setPrecision(precision);

        return bindable;
    }

    /**
     * Sets a configuration's default value (int).
     */
    protected BindableInt setDefault(TLookup lookup, int value, Integer min, Integer max) {
        value = getDefault(lookup, value);

        BindableInt bindable = (BindableInt) getOriginalBindable(lookup);
        if (bindable == null) {
            bindable = new BindableInt(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);
        if (min != null)
            bindable.setMinValue(min);
        if (max != null)
            bindable.setMaxValue(max);

        return bindable;
    }

    /**
     * Sets a configuration's default value (long).
     */
    protected BindableLong setDefault(TLookup lookup, long value, Long min, Long max) {
        value = getDefault(lookup, value);

        BindableLong bindable = (BindableLong) getOriginalBindable(lookup);
        if (bindable == null) {
            bindable = new BindableLong(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);
        if (min != null)
            bindable.setMinValue(min);
        if (max != null)
            bindable.setMaxValue(max);

        return bindable;
    }

    /**
     * Sets a configuration's default value (boolean).
     */
    protected BindableBool setDefault(TLookup lookup, boolean value) {
        value = getDefault(lookup, value);

        BindableBool bindable = (BindableBool) getOriginalBindable(lookup);
        if (bindable == null) {
            bindable = new BindableBool(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);

        return bindable;
    }

    /**
     * Sets a configuration's default value (generic).
     */
    protected <TValue> Bindable<TValue> setDefault(TLookup lookup, TValue value) {
        value = getDefault(lookup, value);

        Bindable<TValue> bindable = getOriginalBindable(lookup);

        if (bindable == null) {
            bindable = new Bindable<>(value);
            addBindable(lookup, bindable);
        } else {
            bindable.setValue(value);
        }

        bindable.setDefaultValue(value);

        return bindable;
    }

    /**
     * Adds a bindable to the config store and sets up auto-save.
     */
    protected <TValue> void addBindable(TLookup lookup, Bindable<TValue> bindable) {
        configStore.put(lookup, bindable);
        bindable.bindValueChanged(e -> queueBackgroundSave(), false);
    }

    /**
     * Gets the default value, checking overrides first.
     */
    @SuppressWarnings("unchecked")
    private <TValue> TValue getDefault(TLookup lookup, TValue fallback) {
        if (defaultOverrides != null && defaultOverrides.containsKey(lookup)) {
            return (TValue) defaultOverrides.get(lookup);
        }
        return fallback;
    }

    /**
     * Gets the original bindable (not a bound copy).
     */
    @SuppressWarnings("unchecked")
    protected <TValue> Bindable<TValue> getOriginalBindable(TLookup lookup) {
        IBindable<?> obj = configStore.get(lookup);
        if (obj == null)
            return null;

        if (!(obj instanceof Bindable)) {
            throw new ClassCastException("Cannot convert bindable of type " + obj.getClass() +
                    " to Bindable");
        }

        return (Bindable<TValue>) obj;
    }

    /**
     * Retrieves a bindable. This will be a new instance weakly bound to the
     * configuration backing.
     * If you are further binding to events of a bindable retrieved using this
     * method, ensure to hold a local reference.
     * 
     * @return A weakly bound copy of the specified bindable.
     */
    @SuppressWarnings("unchecked")
    public <TValue> Bindable<TValue> getBindable(TLookup lookup) {
        Bindable<TValue> original = getOriginalBindable(lookup);
        return original != null ? (Bindable<TValue>) original.getBoundCopy() : null;
    }

    /**
     * Binds a local bindable with a configuration-backed bindable.
     */
    public <TValue> void bindWith(TLookup lookup, Bindable<TValue> bindable) {
        Bindable<TValue> original = getOriginalBindable(lookup);
        if (original != null) {
            bindable.bindTo(original);
        }
    }

    /**
     * Check whether a specific lookup may contain private user information.
     * 
     * @param lookup The lookup type to check.
     * @return Whether private information is present.
     */
    protected boolean checkLookupContainsPrivateInformation(TLookup lookup) {
        return false;
    }

    @Override
    public void close() {
        save();
        scheduler.shutdown();
    }
}
