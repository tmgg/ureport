/*******************************************************************************
 * Copyright 2017 Bstek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.ureport.provider.report.file;

import com.bstek.ureport.UReportProperties;
import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Jacky.gao
 * @since 2017年2月11日
 */
@Component
public class FileReportProvider implements ReportProvider {
    private String prefix = "file:";

    @Resource
    private UReportProperties props;

    private String storeDir;

    @PostConstruct
    public void init() throws IOException {
        storeDir = props.getFileStoreDir();
            FileUtils.forceMkdir(new File(storeDir));
    }



    @Override
    public String loadReport(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length());
        }
        String fullPath = storeDir + "/" + file;
        try {
            return FileUtils.readFileToString(new File(fullPath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ReportException(e);
        }
    }

    @Override
    public void deleteReport(String file) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length());
        }
        String fullPath = storeDir + "/" + file;
        File f = new File(fullPath);
        if (f.exists()) {
            f.delete();
        }
    }

    @Override
    public List<ReportFile> getReportFiles() {
        File file = new File(storeDir);
        List<ReportFile> list = new ArrayList<>();
        for (File f : file.listFiles()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(f.lastModified());
            list.add(new ReportFile(f.getName(), calendar.getTime()));
        }
        Collections.sort(list, (f1, f2) -> f2.getUpdateDate().compareTo(f1.getUpdateDate()));
        return list;
    }

    @Override
    public String getName() {
        return "服务器文件系统" + storeDir;
    }

    @Override
    public void saveReport(String file, String content) {
        if (file.startsWith(prefix)) {
            file = file.substring(prefix.length(), file.length());
        }
        String fullPath = storeDir + "/" + file;
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(new File(fullPath));
            IOUtils.write(content, outStream, "utf-8");
        } catch (Exception ex) {
            throw new ReportException(ex);
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public boolean disabled() {
        return !props.isFileStoreEnable();
    }




    @Override
    public String getPrefix() {
        return prefix;
    }
}
