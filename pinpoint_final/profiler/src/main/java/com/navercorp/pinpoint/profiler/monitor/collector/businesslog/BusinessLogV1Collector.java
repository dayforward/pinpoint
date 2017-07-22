package com.navercorp.pinpoint.profiler.monitor.collector.businesslog;

import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.profiler.monitor.collector.BusinessLogMetaCollector;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogV1;
import javafx.util.Pair;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created by Administrator on 2017/7/21.
 */
public class BusinessLogV1Collector extends BusinessLogVXMetaCollector<TBusinessLogV1> {

    private ProfilerConfig profilerConfig;
    private long nextLine;
    static final Pattern BUSINESS_LOG_PATTEN = compile("BUSSINESS_LOG*.log");
    static final String TIME_FIELD_PATTEN = "^[[1-9]\\\\d{3}\\\\-(0?[1-9]|1[0-2])\\\\-(0?[1-9]|[12]\\\\d|3[01])\\\\s*(0?[1-9]|1\\\\d|2[0-3])(\\\\:(0?[1-9]|[1-5]\\\\d)){2}]$";
    static final String THREAD_FIELD_PATTEN = "^[*]$";
    static final String LOG_LEVEL_FIELD_PATTEN = "^[a-zA-Z]{4,}";
    static final String CLASS_FIELD_PATTEN = "^[0-9a-zA-Z][.[0-9a-zA-Z]]*";
    static final String TXSPAN_BEGIN_FIELD_PATTEN = "^[*$";
    static final String TXSPAN_END_FIELD_PATTEN = "*]$";
    private List<String> businessLogList;
    private final static int linePerLogPerBatch = 1000;
    private HashMap<String, Pair<Date, Long>> dailyLogLineMap = new HashMap<String, Pair<Date, Long>>();

    private enum EnumField {
        TIME, THREAD, LOGLEVEL, CLASS, TXSPANBEGIN, TXSPANEND, MESSAGE
    }

    public BusinessLogV1Collector(ProfilerConfig profilerConfig) {
        this.profilerConfig = profilerConfig;
        this.nextLine = 0;
        businessLogList = new ArrayList<String>();
    }

    @Override
    public List<TBusinessLogV1> collect() {
        return getBussinessLogV1List();
    }

    private File[] listFiles(final Pattern pattern, String logDirPath) {
        File logDir = new File(logDirPath);
        return logDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches()) {
                    businessLogList.add(fileName);
                    return true;
                }
                return false;
            }
        });
    }

    boolean checkTimePatternMeet(String timeField) {
        if (timeField.matches(TIME_FIELD_PATTEN))
            return true;
        return false;
    }

    boolean checkThreadPatternMeet(String threadField) {
        if (threadField.matches(THREAD_FIELD_PATTEN))
            return true;
        return false;
    }

    boolean checkLogLevelMeet(String logLevelField) {
        if (logLevelField.matches(LOG_LEVEL_FIELD_PATTEN))
            return true;
        return false;
    }

    boolean checkClassFieldMeet(String classField) {
        if (classField.matches(CLASS_FIELD_PATTEN))
            return true;
        return false;
    }

    boolean checkTxSpanBeginMeet(String txSpanField) {
        if (txSpanField.matches(TXSPAN_BEGIN_FIELD_PATTEN))
            return true;
        return false;
    }

    boolean checkTxSpanEndMeet(String txSpanField) {
        if (txSpanField.matches(TXSPAN_END_FIELD_PATTEN))
            return false;
        return true;
    }

    List<String> generateFieldList(List<String[]> linesOfTxts) {
        List<String> stringList = new ArrayList<String>();
        StringBuilder messageBuilder = new StringBuilder();
        StringBuilder txSpanBuilder = new StringBuilder();
        EnumField state = EnumField.TIME;
        for (String[] stringArray : linesOfTxts) {
            for (String string : stringArray) {
                switch (state) {
                    case TIME:
                        if (checkTimePatternMeet(string) == false)
                            return null;
                        stringList.add(string);
                        state = EnumField.THREAD;
                        break;
                    case THREAD:
                        if (checkThreadPatternMeet(string) == false)
                            return null;
                        stringList.add(string);
                        state = EnumField.LOGLEVEL;
                        break;
                    case LOGLEVEL:
                        if (checkLogLevelMeet(string) == false)
                            return null;
                        stringList.add(string);
                        state = EnumField.CLASS;
                        break;
                    case CLASS:
                        if (checkClassFieldMeet(string) == false)
                            return null;
                        stringList.add(string);
                        state = EnumField.TXSPANBEGIN;
                        break;
                    case TXSPANBEGIN:
                        if (checkTxSpanBeginMeet(string) == false)
                            return null;
                        txSpanBuilder.append(string);
                        state = EnumField.TXSPANEND;
                        break;
                    case TXSPANEND:
                        txSpanBuilder.append(string);
                        if (checkTxSpanEndMeet(string) == true)
                            state = EnumField.MESSAGE;
                        break;
                    case MESSAGE:
                        messageBuilder.append(string);
                }
            }
        }
        if (state == EnumField.MESSAGE) {
            stringList.add(txSpanBuilder.toString());
            stringList.add(messageBuilder.toString());
        } else
            return null;
        return stringList;
    }

    Pair<String, String> retriveTractionIdFromField(String string) {
        String[] stringList = string.split(",");
        if (stringList.length != 2)
            return null;

        String[] subStringList1 = stringList[0].split(":");
        if (subStringList1.length != 2)
            return null;
        String txId = subStringList1[1].trim();

        String[] subStringList2 = stringList[1].split(":");
        if (subStringList2.length != 2)
            return null;
        String spanId = subStringList1[2].trim();
        return make_pair(txId,spanId);
    }

    String retriveMessageFormField(List<String> fieldList, int index) {
        StringBuilder sb = new StringBuilder();
        for(i = index; i < fieldList.size(); i++)
            sb.append(fieldList.get(i));
        return sb.toString();
    }

    TBusinessLogV1 assembleTBusinessLogV1(List<String> fieldList) {
        TBusinessLogV1 tBusinessLogV1 = new TBusinessLogV1();
        tBusinessLogV1.setTime(fieldList.get(0));
        tBusinessLogV1.setThreadName(fieldList.get(1));
        tBusinessLogV1.setLogLevel(fieldList.get(2));
        tBusinessLogV1.setClassName(fieldList.get(3));
        Pair<String, String> txSpanPair = retriveTractionIdFromField(fieldList.get(4));
        tBusinessLogV1.setTransactionId(txSpanPair.getKey());
        tBusinessLogV1.setSpanId(txSpanPair.getValue());
        tBusinessLogV1.setMessage(retriveMessageFormField(fieldList, 4));
        return tBusinessLogV1;
    }

    TBusinessLogV1 generateTBusinessLogV1FromStringList(List<String[]> linesOfTxts) {
        List<String> fieldList = generateFieldList(linesOfTxts);
        if (fieldList == null)
            return null;
        return assembleTBusinessLogV1(fieldList);
    }

    private Pair<Long, TBusinessLogV1> readOneLogFromLineInner(BufferedReader reader, String businessLogV1, Long line) {
        Long nextLine = line;
        String lineTxt;
        boolean firstLineInOneMessage = true;
        List<String[]> linesOfTxts = new ArrayList<String[]>();
        while (true) {
            try {
                if ((lineTxt = reader.readLine()) == null) {
                    return make_pair(line, null);
                }
                String[] lineTxts = lineTxt.split(" ");
                nextLine++;
                if (firstLineInOneMessage) {
                    if (!checkTimePatternMeet(lineTxts[0])) {
                        //corrupt log format
                        return make_pair(line, null);
                    }
                    firstLineInOneMessage = false;
                } else {
                    if (checkTimePatternMeet(lineTxts[0]))
                        break;
                }
                linesOfTxts.add(lineTxts);
            } catch (IOException e1) {
                e1.printStackTrace();
                return make_pair(line, null);
            }
        }

        TBusinessLogV1 tBusinessLogV1 = generateTBusinessLogV1FromStringList(linesOfTxts);
        if (tBusinessLogV1 == null)
            return make_pair(line, null);
        return make_pair(nextLine, tBusinessLogV1);
    }

    boolean skipPreLines(BufferedReader reader, String businessLogV1, Long line) {
        Long initValue = 0L;
        String lineTxt;
        while (line.compareTo(initValue) > 0) {
            try {
                if ((lineTxt = reader.readLine()) == null) {
                    return false;
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                return false;
            }
            initValue++;
        }
        return true;
    }

    private Pair<Long, TBusinessLogV1> readOneLogFromLine(BufferedReader reader, String businessLogV1, Long line) {

        //[XINGUANG] skip prelines
        boolean flag = skipPreLines(reader, businessLogV1, line);
        if (!flag) {
            return make_pair(line, null);
        }
        return readOneLogFromLineInner(reader, businessLogV1, line);
    }

    private BufferedReader generateBufferedReader(String businessLogV1) {
        String encoding = "UTF-8";
        try {
            InputStreamReader isReader = new InputStreamReader(new FileInputStream(businessLogV1), encoding);
            BufferedReader reader = new BufferedReader(isReader);
            return reader;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<TBusinessLogV1> readLogFromBusinessLog(String businessLogV1) {
        List<TBusinessLogV1> tBusinessLogV1List = new ArrayList<TBusinessLogV1>();
        //[XINGUANG] generate reader for businessLogV1 file
        BufferedReader reader = generateBufferedReader(businessLogV1);
        if (reader == null)
            return tBusinessLogV1List;
        //[XINGUANG] get line number
        Pair<Date, Long> dateLinePair = dailyLogLineMap.get(businessLogV1);
        Long line = dateLinePair.getValue();
        for (int i = 0; i < linePerLogPerBatch; i++) {
            Pair<Long, TBusinessLogV1> tBusinessLogV1LinePair = readOneLogFromLine(reader, businessLogV1, line);
            if (tBusinessLogV1LinePair.getKey() != line) {
                line = tBusinessLogV1LinePair.getKey();
                tBusinessLogV1List.add(tBusinessLogV1LinePair.getValue());
            } else {
                break;
            }
        }
        return tBusinessLogV1List;
    }

    private List<TBusinessLogV1> readLogFromBusinessLogList() {
        List<TBusinessLogV1> tBussinessLogV1List = new ArrayList<TBusinessLogV1>();
        for (String businessLogV1 : businessLogList) {
            if (dailyLogLineMap.containsKey(businessLogV1)) {
                List<TBusinessLogV1> tBusinessLogV1ListSub = readLogFromBusinessLog(businessLogV1);
                //[XINGUANG] gurantee the order by first added list ,second added list ... when resolve data from tBusinessLogV1List?
                tBussinessLogV1List.addAll(tBusinessLogV1ListSub);
            }
        }
        return tBussinessLogV1List;
    }

    private void generateLogLineMap() {
        for (String businessLogV1 : businessLogList) {
            Pair<Date, Long> dailyLine = dailyLogLineMap.get(businessLogV1);
            if (dailyLine == null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = df.parse(df.format(new Date()));
                    dailyLogLineMap.put(businessLogV1, new Pair<Date, Long>(date, 0l));
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            } else {
                Date originDate = dailyLine.getKey();
                try {
                    Date date = df.parse(df.format(new Date()));
                    if (originDate.before(date)) {
                        dailyLogLineMap.put(businessLogV1, new Pair<Date, Long>(date, 0l));
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }

    private List<TBusinessLogV1> getBussinessLogV1List() {
        List<TBusinessLogV1> tBussinessLogV1s = new ArrayList<TBusinessLogV1>();
        //[XINGUANG] retrives logs from tomcat log dir
        String tomcatLogDir = profilerConfig.getTomcatLogDir();
        businessLogList.clear();
        listFiles(BUSINESS_LOG_PATTEN, tomcatLogDir);
        generateLogLineMap();
        //[XINGUANG] read BusinessLogList
        return readLogFromBusinessLogList();
    }
}