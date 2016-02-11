/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cats_project;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Aditya Rathi
 */

public class MultiThreadedWindow extends JFrame implements ActionListener{

    private JPanel topPanel = new JPanel();
    private JButton playButton = new JButton("Stream All");
    private JButton btnStop = new JButton("Stop All");
    private JButton btnPause = new JButton("Pause All");
    private ArrayList<MediaPlayerFactory> factoryList = new ArrayList<>();
    private ArrayList<EmbeddedMediaPlayer> mediaPlayerList = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    int Video_Width = 470;
    int Video_Height = 300;
    int Num_Video = 6;
    
    public static void main(String[] args){
    
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new MultiThreadedWindow().start();
                }
        });
    
    }


    public MultiThreadedWindow() {
        
        this.setTitle("CATS Admin Dashboard");

        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<Num_Video;i++)
                    mediaPlayerList.get(i).playMedia("C:\\Users\\Aditya Rathi\\Desktop\\CATS Test Folder\\output"+i+".flv");
            }
        });

        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               for(int i=0;i<Num_Video;i++)
                    mediaPlayerList.get(i).stop();
            }
        });

        btnPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               for(int i=0;i<Num_Video;i++)
                    mediaPlayerList.get(i).pause();
            }
        });


        topPanel.add(playButton);
        topPanel.add(btnStop);
        topPanel.add(btnPause);

        this.setSize(1500, 800);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new FlowLayout());
        Canvas[] videoCanvas = new Canvas[Num_Video];
        JPanel[] vidPanel = new JPanel[Num_Video];

        for (int i = 0; i < Num_Video; i++) {
            vidPanel[i] = new JPanel();
            videoCanvas[i] = new Canvas();
            vidPanel[i].setPreferredSize(new Dimension(Video_Width, Video_Height));
            vidPanel[i].setLayout(new BorderLayout());

            videoCanvas[i].setBackground(Color.black);
            
            vidPanel[i].add(videoCanvas[i], BorderLayout.CENTER);
            
            String s = "<html><font color='white'>Machine "+(i+1)+"</font></html>";
            
            JPanel belowPanel = new MachinePanel(s);
            
            vidPanel[i].add(belowPanel,BorderLayout.SOUTH);
            mainPanel.add(vidPanel[i]);
            

        }
        
        for(int i=0;i<Num_Video;i++){
            MediaPlayerFactory factory = new MediaPlayerFactory();
            EmbeddedMediaPlayer mediaPlayer = factory.newEmbeddedMediaPlayer();
            mediaPlayer.setVideoSurface(factory.newVideoSurface(videoCanvas[i]));
            mediaPlayer.setPlaySubItems(true);
            mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {

                @Override
                public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
                    System.out.println(mediaPlayer.subItems());
                }
            });
            
            factoryList.add(factory);
            mediaPlayerList.add(mediaPlayer);
        }

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(new LogPanel(), BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) throws IllegalStateException {

        if (e.getActionCommand().equals("OK")) {
            this.dispose();
        }
    }

    private void start() {
        this.setVisible(true);
    }


    
}
