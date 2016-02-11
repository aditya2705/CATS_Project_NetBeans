/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cats_project;

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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 *
 * @author Aditya Rathi
 */

public class MultiThreadedWindow extends JFrame{

    private JPanel topPanel = new JPanel();
    private JPanel statusPanel = new JPanel();
    private JButton playButton = new JButton("Play");private JButton btnStop = new JButton("Stop");
    private JButton btnPause = new JButton("Pause");
    private ArrayList<MediaPlayerFactory> factoryList = new ArrayList<>();
    private ArrayList<EmbeddedMediaPlayer> mediaPlayerList = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    int Video_Width = 230;
    int Video_Height = 190;
    int Num_Video;


    public MultiThreadedWindow(ArrayList<String> urlList) {
        
        this.urlList = urlList;
        Num_Video = urlList.size();

        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               stop();
            }
        });

        btnPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               pause();
            }
        });


        topPanel.add(playButton);
        topPanel.add(btnStop);
        topPanel.add(btnPause);

        this.setSize(1000, 400);
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
            vidPanel[i].setBackground(Color.black);
            vidPanel[i].setLayout(new BorderLayout());

            videoCanvas[i].setBackground(Color.black);
            vidPanel[i].add(videoCanvas[i], BorderLayout.CENTER);

            mainPanel.add(vidPanel[i]);

        }
        
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            }
        });
        
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
        this.add(statusPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) throws IllegalStateException {

        if (e.getActionCommand().equals("OK")) {
            this.dispose();
        }
    }

    private void start() {
        this.setVisible(true);
    }

    private void play() {
        for(int i=0;i<Num_Video;i++)
            mediaPlayerList.get(i).playMedia(urlList.get(i));
       
    }

    private void stop() {
        for(int i=0;i<Num_Video;i++)
            mediaPlayerList.get(i).stop();
    }

    private void pause() {
        for(int i=0;i<Num_Video;i++)
            mediaPlayerList.get(i).pause();
    }

    
}
