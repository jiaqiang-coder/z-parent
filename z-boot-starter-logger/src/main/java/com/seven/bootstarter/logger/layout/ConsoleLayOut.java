package com.seven.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSONObject;
import com.seven.bootstarter.logger.filter.MDCFilter;

/**
 * @author zhangxianwen
 * 2020/1/12 10:41
 **/
public class ConsoleLayOut extends AbstractLayout {
    private final static String TAB = " ";
    private Boolean showThreadName = true;
    private Boolean showSeriesIp = true;

    public void setShowThreadName(Boolean showThreadName) {
        this.showThreadName = showThreadName;
    }

    public void setShowSeriesIp(Boolean showSeriesIp) {
        this.showSeriesIp = showSeriesIp;
    }

    @Override
    String buildLayout(ILoggingEvent iLoggingEvent) {
        JSONObject json = new JSONObject();
        writeMDC(json, iLoggingEvent);
        writeBasic(json, iLoggingEvent);
        writeThrowable(json, iLoggingEvent);
        StringBuilder out = new StringBuilder();
        out.append(json.get("time"));
        out.append(TAB);
        if (showThreadName) {
            out.append(json.get("threadName"));
            out.append(TAB);
        }
        out.append(json.get("level"));
        out.append(TAB);
        if (json.get(MDCFilter.HEADER_KEY_TRACE_ID) != null) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_KEY_TRACE_ID));
            out.append("]");
            out.append(TAB);
        }
        if (showSeriesIp && json.get(MDCFilter.HEADER_KEY_SERIES_IP) != null) {
            out.append("[");
            out.append(json.get(MDCFilter.HEADER_KEY_SERIES_IP));
            out.append("]");
            out.append(TAB);
        }
        out.append(json.get("logger"));
        out.append(TAB);
        out.append("-");
        out.append(TAB);
        out.append(json.get("message"));
        out.append("\n");
        return out.toString();
    }
}
