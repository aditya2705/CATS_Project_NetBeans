/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cats_project;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;

/**
 * Simple full-screen test.
 */
public class FullScreenTest extends VlcjTest {

    private JFrame frame;

    private EmbeddedMediaPlayerComponent mediaPlayerComponent;

    public static void main(String[] args) {

        new NativeDiscovery().discover();
        final String mrl = "C:\\Users\\Aditya Rathi\\Desktop\\CATS Test Folder\\output5.flv";

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FullScreenTest().start(mrl);
            }
        });
    }

    @SuppressWarnings("serial")
    public FullScreenTest() {
        frame = new JFrame("Win32 Full Screen Strategy");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(100, 100);
        frame.setSize(1200, 800);

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent() {
            @Override
            protected FullScreenStrategy onGetFullScreenStrategy() {
                return new Win32FullScreenStrategy(frame);
            }
        };

        frame.setContentPane(mediaPlayerComponent);

        frame.setVisible(true);
    }

    protected void start(String mrl) {
        mediaPlayerComponent.getMediaPlayer().playMedia(mrl);
        mediaPlayerComponent.getMediaPlayer().setFullScreen(true);
    }
}