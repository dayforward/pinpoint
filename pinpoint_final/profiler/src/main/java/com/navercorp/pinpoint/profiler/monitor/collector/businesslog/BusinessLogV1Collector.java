package com.navercorp.pinpoint.profiler.monitor.collector.businesslog;

import static java.util.regex.Pattern.compile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.google.inject.Inject;
import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.common.util.Pair;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogV1;



/**
 * Created by Administrator on 2017/7/21.
 */
public class BusinessLogV1Collector implements BusinessLogVXMetaCollector<TBusinessLogV1> {

    private ProfilerConfig profilerConfig;
    private long nextLine;
    static final Pattern BUSINESS_LOG_PATTERN = compile("^BUSINESS_LOG_[A-Za-z]*.log$");
    //static final String TIME_FIELD_PATTEN = "^[[1-9]\\\\d{3}\\\\-(0?[1-9]|1[0-2])\\\\-(0?[1-9]|[12]\\\\d|3[01])\\\\s*(0?[1-9]|1\\\\d|2[0-3])(\\\\:(0?[1-9]|[1-5]\\\\d)){2}]$";
    static final String TIME_FIELD_PATTEN = "^\\[([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))) ([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])\\]$";
    static final String THREAD_FIELD_PATTEN = "^[*]$";
    static final String LOG_LEVEL_FIELD_PATTEN = "^[a-zA-Z]{4,}";
    static final String CLASS_FIELD_PATTEN = "^[0-9a-zA-Z][.[0-9a-zA-Z]]*";
    static final String TXSPAN_BEGIN_FIELD_PATTEN = "^[*$";
    static final String TXSPAN_END_FIELD_PATTEN = "*]$";
    private List<String> businessLogList;
    private final static int linePerLogPerBatch = 1000;
    String[] nextLineContext = null;
    boolean lastLine = false;
    private HashMap<String, Pair<Date, Long>> dailyLogLineMap = new HashMap<String, Pair<Date, Long>>();

    private enum EnumField {
        TIME, THREAD, LOGLEVEL, CLASS, TXSPANBEGIN, TXSPANEND, MESSAGE
    }

    @Inject
    public BusinessLogV1Collector(ProfilerConfig profilerConfig) {
        this.profilerConfig = profilerConfig;
        this.nextLine = 0;
        businessLogList = new ArrayList<String>();
    }

    @Override
    public List<TBusinessLogV1> collect() {
    	/*TBusinessLogV1 tBusinessLogV1 = new TBusinessLogV1();
    	tBusinessLogV1.setMessage("this is a test message");
    	tBusinessLogV1.setSpanId("testSpanId");
    	tBusinessLogV1.setTransactionId("testTransactionId");
    	List<TBusinessLogV1> tBusinessLogV1s = new ArrayList<TBusinessLogV1>();
    	tBusinessLogV1s.add(tBusinessLogV1);
    	return tBusinessLogV1s;*/
        
        return getBusinessLogV1List();
    }

    private File[] listFiles(final Pattern pattern, String logDirPath) {
        File logDir = new File(logDirPath);
        return logDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches()) {
                	System.out.println(fileName);
                    //businessLogList.add(fileName);
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
    	List<String> linesList = new ArrayList<String>();
    	for (String[] strArray : linesOfTxts) {
    		for (String str : strArray) {
    			linesList.add(str);
    		}
    	}
    	//删除多余的字符串
    	linesList.remove(1);  	
        linesList.remove(4);
        linesList.remove(4);
        linesList.remove(5);
        linesList.remove(5);
       /* EnumField state = EnumField.TIME;
        for (String string : linesList) {
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
        if (state == EnumField.MESSAGE) {
            stringList.add(txSpanBuilder.toString());
            stringList.add(messageBuilder.toString());
        } else
            return null;*/
        return linesList;
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
        return new Pair<String, String>(txId,spanId);
    }

    String retriveMessageFormField(List<String> fieldList, int index) {
        StringBuilder sb = new StringBuilder();
        for(int i = index; i < fieldList.size(); i++)
            sb.append(fieldList.get(i)).append(" ");
        return sb.toString();
    }

    TBusinessLogV1 assembleTBusinessLogV1(List<String> fieldList) {
        TBusinessLogV1 tBusinessLogV1 = new TBusinessLogV1();
        tBusinessLogV1.setTime(fieldList.get(0));
        tBusinessLogV1.setThreadName(fieldList.get(1));
        tBusinessLogV1.setLogLevel(fieldList.get(2));
        tBusinessLogV1.setClassName(fieldList.get(3));
        //Pair<String, String> txSpanPair = retriveTractionIdFromField(fieldList.get(4));
        tBusinessLogV1.setTransactionId(fieldList.get(4));
        tBusinessLogV1.setSpanId(fieldList.get(5).split("]")[0]);
        tBusinessLogV1.setMessage(retriveMessageFormField(fieldList, 6));
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
        	if (nextLineContext != null) {
        		linesOfTxts.add(nextLineContext);
        		nextLineContext = null;
        		firstLineInOneMessage = false;
        	} else {        	
	            try {
	                if ((lineTxt = reader.readLine()) == null) {
	                    //return new Pair<Long, TBusinessLogV1>(line, null);
	                	lastLine = true;
	                	break;
	                }
	                String[] lineTxts = lineTxt.split(" ");
	                nextLine++;
	                String time  = lineTxts[0] + " " + lineTxts[1];
	                lineTxts[0] = time;
	                if (firstLineInOneMessage) {                 	
	                    if (!checkTimePatternMeet(time)) {
	                        //corrupt log format
	                        return new Pair(line, null);
	                    }
	                    firstLineInOneMessage = false;
	                } else {
	                    if (checkTimePatternMeet(time)) {
	                    	nextLineContext = lineTxts;
	                    	break;
	                    }
	                        
	                }
	                linesOfTxts.add(lineTxts);   
	            } catch (IOException e1) {
	                e1.printStackTrace();
	                return new Pair<Long, TBusinessLogV1>(line, null);
	            }
        	}
            
        }

        TBusinessLogV1 tBusinessLogV1 = generateTBusinessLogV1FromStringList(linesOfTxts);
        if (tBusinessLogV1 == null)
            return new Pair<Long, TBusinessLogV1>(line, null);
        return new Pair<Long, TBusinessLogV1>(nextLine, tBusinessLogV1);
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
        
        //[XINGUANG] skip prelines
        boolean flag = skipPreLines(reader, businessLogV1, line);
        /*if (!flag) {
            return new Pair<Long, TBusinessLogV1>(line, null);
        }*/        
        for (int i = 0; i < linePerLogPerBatch; i++) {
        	if(lastLine) {
        		break;
        	}
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
        List<TBusinessLogV1> tBusinessLogV1List = new ArrayList<TBusinessLogV1>();
        for (String businessLogV1 : businessLogList) {
            if (dailyLogLineMap.containsKey(businessLogV1)) {
                List<TBusinessLogV1> tBusinessLogV1ListSub = readLogFromBusinessLog(businessLogV1);
                //[XINGUANG] gurantee the order by first added list ,second added list ... when resolve data from tBusinessLogV1List?
                tBusinessLogV1List.addAll(tBusinessLogV1ListSub);
            }
        }
        return tBusinessLogV1List;
    }

    private void generateLogLineMap() {
        for (String businessLogV1 : businessLogList) {
            Pair<Date, Long> dailyLine = dailyLogLineMap.get(businessLogV1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            if (dailyLine == null) {               
                    Date date;
					try {
						date = df.parse(df.format(new Date()));
						dailyLogLineMap.put(businessLogV1, new Pair<Date, Long>(date, 0l));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}               
            } else {
                Date originDate = dailyLine.getKey();
                    Date date;
					try {
						date = df.parse(df.format(new Date()));
						if (originDate.before(date)) {
	                        dailyLogLineMap.put(businessLogV1, new Pair<Date, Long>(date, 0l));
	                    }
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}                   
            }
        }
    }
    
    private void generateBusinessLogList(File[] files, String tomcatLogDir) {
    	for (File file : files) {
    		businessLogList.add(file.toString());
    	}
    }

    private List<TBusinessLogV1> getBusinessLogV1List() {
        //[XINGUANG] retrives logs from tomcat log dir
    	//String tomcatLogDir = "D:/logs";
        String tomcatLogDir = profilerConfig.getTomcatLogDir();
        businessLogList.clear();
        File[] files = listFiles(BUSINESS_LOG_PATTERN, tomcatLogDir);
        generateBusinessLogList(files, tomcatLogDir);
        generateLogLineMap();
        //[XINGUANG] read BusinessLogList
        return readLogFromBusinessLogList();
    }
    
   /* public static void main(String[] args) {
    	BusinessLogV1Collector b = new BusinessLogV1Collector(null);
    	List<TBusinessLogV1> li = b.getBusinessLogV1List();
    	System.out.println(li.toString());
    }*/
    
}