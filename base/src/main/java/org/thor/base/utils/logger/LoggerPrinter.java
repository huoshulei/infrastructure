package org.thor.base.utils.logger;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * 项目名称:  MSHB
 * 类描述:
 * 创建人:    ICOGN
 * 创建时间:  2016/9/23 15:49
 * 修改人:    ICOGN
 * 修改时间:  2016/9/23 15:49
 * 备注:
 * 版本:
 */
final class LoggerPrinter implements Printer {


    private static final int DEBUG = 3;

    private static final int CHUNK_SIZE = 4000;


    private static final int JSON_INDENT = 5;


    private static final int MIN_STACK_OFFSET = 3;

    private static final char   TOP_LEFT_CORNER        = '╔';
    private static final char   BOTTOM_LEFT_CORNER     = '╚';
    private static final char   MIDDLE_CORNER          = '╟';
    private static final char   HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER         = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER         = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    private static final String TOP_BORDER             = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER          = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER          = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;


    private String tag;


    private final ThreadLocal<String>  localTag         = new ThreadLocal<>();
    private final ThreadLocal<Integer> localMethodCount = new ThreadLocal<>();


    private final Settings settings = new Settings();

    LoggerPrinter() {
    }


    @Override
    public Settings init(String tag) {
        this.tag = tag;
        return settings;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Printer t(String tag, int methodCount) {
        if (tag != null) {
            localTag.set(tag);
        }
        localMethodCount.set(methodCount);
        return this;
    }


    @Override
    public void d(Object object) {
        String message;
        if (object.getClass().isArray()) {
            message = Arrays.deepToString((Object[]) object);
        } else {
            message = object.toString();
        }
        log(DEBUG, null, message);
    }


    @Override
    public void json(String json) {
        if (Helper.isEmpty(json)) {
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String     message    = jsonObject.toString(JSON_INDENT);
                d(message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String    message   = jsonArray.toString(JSON_INDENT);
                d(message);
                return;
            }
            d(json);
        } catch (JSONException e) {
            d(json);
        }
    }


    @Override
    public synchronized void log(int priority, String tag, String message, Throwable throwable) {
        if (settings.getLogLevel() == LogLevel.NONE) {
            return;
        }
        if (throwable != null && message != null) {
            message += " : " + Helper.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Helper.getStackTraceString(throwable);
        }
        if (message == null) {
            message = "没有设置日志信息";
        }
        int methodCount = getMethodCount();
        if (Helper.isEmpty(message)) {
            message = "空日志信息";
        }

        logTopBorder(tag);
        logHeaderContent(tag, methodCount);

        byte[] bytes  = message.getBytes();
        int    length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(tag);
            }
            logContent(tag, message);
            logBottomBorder(tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            logContent(tag, new String(bytes, i, count));
        }
        logBottomBorder(tag);
    }


    private synchronized void log(int priority, Throwable throwable, String msg, Object... args) {
        if (settings.getLogLevel() == LogLevel.NONE) {
            return;
        }
        String tag     = getTag();
        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }

    private void logTopBorder(String tag) {
        logChunk(tag, TOP_BORDER);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (settings.isShowThreadInfo()) {
            logChunk(tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(tag);
        }
        String level = "";

        int stackOffset = getStackOffset(trace) + settings.getMethodOffset();

        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("║ ")
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            logChunk(tag, builder.toString());
        }
    }

    private void logBottomBorder(String tag) {
        logChunk(tag, BOTTOM_BORDER);
    }

    private void logDivider(String tag) {
        logChunk(tag, MIDDLE_BORDER);
    }

    private void logContent(String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private void logChunk(String tag, String chunk) {
        settings.getLogAdapter().d(formatTag(tag), chunk);


    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        if (!Helper.isEmpty(tag) && !Helper.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }


    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return this.tag;
    }

    private String createMessage(String message, Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count  = localMethodCount.get();
        int     result = settings.getMethodCount();
        if (count != null) {
            localMethodCount.remove();
            result = count;
        }
        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        }
        return result;
    }

    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e    = trace[i];
            String            name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

}
