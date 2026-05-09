package osu.framework;

import osu.framework.development.*;
import osu.framework.platform.*;

public class FrameworkEnvironment {
    public static ExecutionNode startupExecutionNode = ExecutionNode.MULTI_THREADED; // in the main source code, it is { get; }, so I assume MULTI_THREADED.
    public static boolean NoTestTimeout;
    public static boolean ForceTestGC;
    public static boolean FailFlakyTests;
    public static boolean FrameStatisticViaTouch;
    // TODO: GraphicsSurfaceType. File cannot continue yet as I need to port that.
}
