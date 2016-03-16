/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cats_project;

/**
 *
 * @author Aditya Rathi
 */
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.Canvas;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import uk.co.caprica.vlcj.binding.LibVlc;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 * An example of how to stream a media file using RTP and use the "duplicate" output to also display
 * the video locally in an embedded media player.
 * <p>
 * Note that the duplicated output does not play it's own <em>stream</em>, so video displayed by
 * client applications will lag the local duplicated output.
 * <p>
 * The client specifies an MRL of <code>rtp://@230.0.0.1:5555</code>
 */
public class StreamRtpDuplicate extends VlcjTest {

    public static void main(String[] args) throws Exception {
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        
        String media = "screen://";
        String options = formatRtpStream("230.0.0.1", 5555);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
        EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();

        Canvas canvas = new Canvas();
        canvas.setBackground(Color.black);
        CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
        mediaPlayer.setVideoSurface(videoSurface);

        JFrame f = new JFrame("vlcj duplicate output test");
        f.add(canvas);
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        mediaPlayer.playMedia(media,
            options,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep"
        );

        // Don't exit
        Thread.currentThread().join();
    }

    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#duplicate{dst=display,dst=rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}}");
        return sb.toString();
    }
}