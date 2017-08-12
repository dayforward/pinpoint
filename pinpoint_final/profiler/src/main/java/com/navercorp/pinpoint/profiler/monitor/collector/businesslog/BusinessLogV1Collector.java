package com.navercorp.pinpoint.profiler.monitor.collector.businesslog;

import static java.util.regex.Pattern.compile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



import com.google.inject.Inject;
import com.navercorp.pinpoint.bootstrap.AgentDirBaseClassPathResolver;
import com.navercorp.pinpoint.bootstrap.BootstrapJarFile;
import com.navercorp.pinpoint.bootstrap.ClassPathResolver;
import com.navercorp.pinpoint.bootstrap.config.ProfilerConfig;
import com.navercorp.pinpoint.common.util.Pair;
import com.navercorp.pinpoint.profiler.context.module.AgentId;
import com.navercorp.pinpoint.profiler.context.module.BootstrapJarPaths;
import com.navercorp.pinpoint.thrift.dto.TBusinessLogV1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * [XINGUANG]Created by Administrator on 2017/7/21.
 */
public class BusinessLogV1Collector implements BusinessLogVXMetaCollector<TBusinessLogV1> {

    private final Logger logger = LoggerFactory.getLogger(BusinessLogV1Collector.class);

    private ProfilerConfig profilerConfig;
    private long nextLine;
    static final Pattern BUSINESS_LOG_PATTERN = compile("^BUSINESS_LOG_[A-Za-z0-9]*.log$");
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
    String agentId;
    String jarPath;
    private HashMap<String, Pair<Date, Long>> dailyLogLineMap = new HashMap<String, Pair<Date, Long>>();

    private enum EnumField {
        TIME, THREAD, LOGLEVEL, CLASS, TXSPANBEGIN, TXSPANEND, MESSAGE
    }

    @Inject
    public BusinessLogV1Collector(ProfilerConfig profilerConfig, @AgentId String agentId) {
        this.profilerConfig = profilerConfig;
        this.nextLine = 0;
        businessLogList = new ArrayList<String>();
        this.agentId = agentId;
    }

    @Override
    public List<TBusinessLogV1> collect() {
        return getBusinessLogV1List();
    }

    @Override
    public void initDailyLogLineMap() {
        System.out.println("Init the dailyLogLineMap");
        //读文件，并初始化dailyLogLineMap
        String agentPath = getAgentPath();
        String dirPath = agentPath + File.separator + "businessLogPersistence";
        String filePath = dirPath  + File.separator +  agentId + ".txt";
        File dir = new File(dirPath);
        if(!dir.exists()) {
            dir.mkdir();
            logger.info("创建存放记录读取日志持久化行数文件的文件夹");
        }
        File file = new File(filePath);
        if (file.exists()) {
            String encoding = "UTF-8";
            try {
                InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath), encoding);
                BufferedReader reader = new BufferedReader(isReader);
                try {
                    String lineTxt = reader.readLine();
                    while (lineTxt != null) {
                        String[] mapStr = lineTxt.split(",");
                        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = sdf.parse(mapStr[1]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long line = Long.parseLong(mapStr[2]);
                        dailyLogLineMap.put(mapStr[0], new Pair<Date, Long>(date, line));
                        lineTxt = reader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveLogMark() {
        System.out.println("save log mark");
        //首先取到agent包的位置
        String agentPath = getAgentPath();
        String dirPath = agentPath + File.separator + "businessLogPersistence";
        String filePath = dirPath   + File.separator +  agentId + ".txt";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdir();
            logger.info("创建存放记录读取日志持久化行数文件的文件夹");
        }
        File file = new File(filePath);
        //写入内容
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        FileWriter fileWriter=null;
        try {
            fileWriter = new FileWriter(file);
        Iterator iter = dailyLogLineMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Pair<Date, Long>> map = (Map.Entry<String, Pair<Date, Long>>) iter.next();
            StringBuilder str = new StringBuilder();
            str.append(map.getKey()).append(",");
            Date date = map.getValue().getKey();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String line = map.getValue().getValue().toString();
            str.append(sdf.format(date)).append(",").append(line).append("\r\n");
            fileWriter.write(str.toString());
            fileWriter.flush();
        }
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }

    private String getAgentPath() {
        final ClassPathResolver classPathResolver = new AgentDirBaseClassPathResolver();
        classPathResolver.verify();
        return classPathResolver.getAgentDirPath();
    }

    private File[] listFiles(final Pattern pattern, String logDirPath) {
        File logDir = new File(logDirPath);
        return logDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String fileName) {
                Matcher matcher = pattern.matcher(fileName);
                if (matcher.matches()) {
                	System.out.println(fileName);
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
        boolean assemblyData = false;
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
	                if ((lineTxt = reader.readLine()) == null ) {
	                	lastLine = true;
	                	break;
	                }
                    nextLine++;
	                if("".equals(lineTxt)){
	                    continue;
                    }
	                String[] lineTxts = lineTxt.split(" ");

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
	                    	Long markLine = nextLine;
	                    	Date date = dailyLogLineMap.get(businessLogV1).getKey();
                            dailyLogLineMap.put(businessLogV1, new Pair<Date, Long>(date, (markLine - 1)));

                            assemblyData = true;
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
        TBusinessLogV1 tBusinessLogV1 = new TBusinessLogV1();
        if (linesOfTxts.size() != 0 && assemblyData == true) {
            tBusinessLogV1 = generateTBusinessLogV1FromStringList(linesOfTxts);
        }
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
        Date date = dateLinePair.getKey();
        
        //[XINGUANG] skip prelines
        boolean flag = skipPreLines(reader, businessLogV1, line);
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
        List<TBusinessLogV1> tBusinessLogV1List = new ArrayList<TBusinessLogV1>();
        if (!businessLogList.isEmpty()) {
            for (String businessLogV1 : businessLogList) {
                if (dailyLogLineMap.containsKey(businessLogV1)) {
                    List<TBusinessLogV1> tBusinessLogV1ListSub = readLogFromBusinessLog(businessLogV1);
                    //[XINGUANG] gurantee the order by first added list ,second added list ... when resolve data from tBusinessLogV1List?
                    tBusinessLogV1List.addAll(tBusinessLogV1ListSub);
                }
            }
        }
        return tBusinessLogV1List;
    }

    private void generateLogLineMap() {
        if (businessLogList != null && !businessLogList.isEmpty()) {
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
    }
    
    private void generateBusinessLogList(File[] files) {
        if (files != null) {
            for (File file : files) {
                businessLogList.add(file.toString());
            }
        }
    }

    private String getCorrespondLogDir(String tomcatLogDirs) {
        String[] tomcatLogDirList = tomcatLogDirs.split(";");
        HashMap<String, String> agentIdLogDirMap = new HashMap<String, String>();
        if (tomcatLogDirList != null && tomcatLogDirList.length != 0) {
            for (String tomcatLogDir : tomcatLogDirList) {
                String[] agentIdAndLog = tomcatLogDir.split("=");
                //agentId和logPath中不能带“~”，且两边都要存在
                if (agentIdAndLog.length == 2) {
                    agentIdLogDirMap.put(agentIdAndLog[0].trim(), agentIdAndLog[1].trim());
                } else {
                    logger.error("配置profiler.tomcatlog.dir参数不正确");
                    return null;
                }
            }
        } else {
            logger.error("未配置profiler.tomcatlog.dir参数");
            return null;
        }
        return agentIdLogDirMap.get(agentId);
    }

    private List<TBusinessLogV1> getBusinessLogV1List() {
        String tomcatLogDirs = profilerConfig.getTomcatLogDir();
        businessLogList.clear();
        String tomcatLogDir = getCorrespondLogDir(tomcatLogDirs);
        if (tomcatLogDir != null) {
            File[] files = listFiles(BUSINESS_LOG_PATTERN, tomcatLogDir.trim());
            generateBusinessLogList(files);

            generateLogLineMap();
            //[XINGUANG] read BusinessLogList
            return readLogFromBusinessLogList();
        } else {
            return new ArrayList<TBusinessLogV1>();
        }
    }
}