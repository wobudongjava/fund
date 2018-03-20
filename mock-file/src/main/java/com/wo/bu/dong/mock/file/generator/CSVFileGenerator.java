package com.wo.bu.dong.mock.file.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import com.opencsv.CSVWriter;

//生成文件模板
public abstract class CSVFileGenerator {
    private File   destFile;
    private int    lineNums;
    private String dataDate;

    public CSVFileGenerator(File destFile, int lineNums, String dataDate) {
        super();
        this.destFile = destFile;
        this.lineNums = lineNums;
        this.dataDate = dataDate;
    }

    public void generateFile() {

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(destFile), "utf-8"); CSVWriter csvWriter = new CSVWriter(writer);) {
            //统计信息
            csvWriter.writeNext(getStatistics());
            //内容标题
            csvWriter.writeNext(getHead());
            //内容
            List<String[]> dataList = getDataList(lineNums);
            csvWriter.writeAll(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract String[] getStatistics();

    protected abstract String[] getHead();

    protected abstract List<String[]> getDataList(int lineNums);

    public String getDataDate() {
        return dataDate;
    }

}
