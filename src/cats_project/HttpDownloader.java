package cats_project;


import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class HttpDownloader {

    private static EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private static ArrayList<String> fileURLList = new ArrayList<>();
    private static ArrayList<String> savedURLList = new ArrayList<>();
    
    public static void main(String[] args) {
        
        fileURLList.add("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        fileURLList.add("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        fileURLList.add("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        fileURLList.add("http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_1mb.mp4");
        
        final String saveDir;
        saveDir = "C:\\Users\\Aditya Rathi\\Desktop";
        for(int i=0;i<fileURLList.size();i++){
            try {
            
            HttpDownloadUtility.downloadFile(fileURLList.get(i), saveDir);
            
            savedURLList.add(saveDir+"\\"+HttpDownloadUtility.getSavedFileName());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MultiThreadedWindow();
                }
        });
        
    }
}
