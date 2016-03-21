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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.windows.Win32FullScreenStrategy;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

/**
 *
 * @author Aditya Rathi
 */

public class ServerSidePanelFrame extends JFrame implements ActionListener{

    private JPanel topPanel = new JPanel();
    private JButton playButton = new JButton("Stream All");
    private JButton btnStop = new JButton("Stop All");
    private JButton btnPause = new JButton("Pause All");
    private JButton btnAddMachine = new JButton("Add Machine");
    private ArrayList<MediaPlayerFactory> factoryList = new ArrayList<>();
    private ArrayList<EmbeddedMediaPlayer> mediaPlayerList = new ArrayList<>();
    private ArrayList<String> urlList = new ArrayList<>();
    public ArrayList<MachineObject> machineObjectsList = new ArrayList<>();
    private JPanel mainCenterPanel;
    
    
    int Video_Width = 470;
    int Video_Height = 300;
    
    
    public static void main(String[] args){
    
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new ServerSidePanelFrame().start();
                }
        });
       
    
    }


    public ServerSidePanelFrame() {
        
        this.setTitle("CATS Admin Dashboard");
        
        
        
        machineObjectsList.add(new MachineObject("Machine A", "http://127.0.0.1:5555"));
        machineObjectsList.add(new MachineObject("Machine B", "http://127.0.0.1:5555"));
        machineObjectsList.add(new MachineObject("Machine C", "http://127.0.0.1:5555"));
        

        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<machineObjectsList.size();i++)
                    mediaPlayerList.get(i).playMedia(machineObjectsList.get(i).getMachineAddress());
               
            }
        });

        btnStop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               for(int i=0;i<machineObjectsList.size();i++)
                    mediaPlayerList.get(i).stop();
            }
        });

        btnPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               for(int i=0;i<machineObjectsList.size();i++)
                    mediaPlayerList.get(i).pause();
            }
        });
        
        btnAddMachine.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                String machineName = JOptionPane.showInputDialog("Enter Machine Name: ");
                
                String machineAddress = JOptionPane.showInputDialog("Enter Address for "+machineName+" : ","http://");
                
                machineObjectsList.add(new MachineObject(machineName, machineAddress));
                
                refreshMachineView();
            }
        });

        topPanel.add(playButton);
        topPanel.add(btnStop);
        topPanel.add(btnPause);
        topPanel.add(btnAddMachine);

        this.setSize(1500, 800);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainCenterPanel = new JPanel(new FlowLayout());

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainCenterPanel, BorderLayout.CENTER);
        this.add(new SettingsPanel(this),BorderLayout.SOUTH);
        refreshMachineView();
        
        File file = new File("C:\\Users\\Aditya Rathi\\Desktop\\");
        long memory = (file.getUsableSpace())/(1024*1024);
        
        JOptionPane.showMessageDialog(
            this, "Memory available: "+memory+" MB");
        
    }

    public void actionPerformed(ActionEvent e) throws IllegalStateException {

        if (e.getActionCommand().equals("OK")) {
            this.dispose();
        }
    }

    private void start() {
        this.setVisible(true);
    }

    public void refreshMachineView(){
        
        mainCenterPanel.removeAll();
        mainCenterPanel.revalidate();
        mainCenterPanel.repaint();
        
        mediaPlayerList = new ArrayList<>();
        factoryList = new ArrayList<>();
        
        for(int i = 0; i < machineObjectsList.size(); i++) {
            
            JPanel vidPanel = machineObjectsList.get(i).getVideoPanel();
            Canvas vidCanvas = machineObjectsList.get(i).getVideoCanvas();
            
            vidPanel.setPreferredSize(new Dimension(Video_Width, Video_Height));
            vidPanel.setLayout(new BorderLayout());

            vidCanvas.setBackground(Color.black);
            
            vidPanel.add(vidCanvas, BorderLayout.CENTER);
            
            String s = "<html><font color='white'>"+machineObjectsList.get(i).getMachineName()+"</font></html>";
            
            JPanel belowPanel = new MachinePanel(this,i);
            ((JLabel)belowPanel.getComponent(0)).setText(s);
            
            vidPanel.add(belowPanel,BorderLayout.SOUTH);
            mainCenterPanel.add(vidPanel);
            
            MediaPlayerFactory factory = new MediaPlayerFactory();
            
            EmbeddedMediaPlayer mediaPlayer = factory.newEmbeddedMediaPlayer(new Win32FullScreenStrategy(this));
            
            
            mediaPlayer.setVideoSurface(factory.newVideoSurface(vidCanvas));
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
        
        
    
    }
    
}
