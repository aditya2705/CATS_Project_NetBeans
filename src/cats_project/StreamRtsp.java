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
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


/**
 * An example of how to stream a media file using RTSP.
 * <p>
 * The client specifies an MRL of <code>rtsp://@127.0.0.1:5555/demo</code>
 */
public class StreamRtsp extends VlcjTest {

    public static void main(String[] args) throws Exception {
       
        String media = "http://trailers.divx.com/divx_prod/profiles/Helicopter_DivXHT_ASP.divx";
        
        //System.out.println("Streaming '" + media + "' to '" + options + "'");
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(media);
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
                        null,
                        ":no-sout-rtp-sap",
                        ":no-sout-standard-sap",
                        ":sout-all",
                        ":sout-keep"
                    );

                }
        });

        
    }

}
