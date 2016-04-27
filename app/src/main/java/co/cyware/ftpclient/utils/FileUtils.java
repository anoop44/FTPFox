package co.cyware.ftpclient.utils;


import android.os.Environment;

import java.io.File;

/**
 * Created by Anoop S S on 27/4/16.
 */
public class FileUtils {

    private static final String FTP_DOWNLOADS = "FTPDownloads";

    public static File getDownloadPath() {
        File downloadFolder = new File(Environment.getExternalStorageDirectory(), FTP_DOWNLOADS);
        return downloadFolder;
    }
}
