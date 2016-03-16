/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2009, 2010, 2011 Caprica Software Limited.
 */

package cats_project;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import javax.swing.SwingUtilities;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;


/**
 * An example of how to stream a media file over HTTP.
 * <p>
 * The client specifies an MRL of <code>http://127.0.0.1:5555</code>
 */
public class StreamHttp extends VlcjTest {
   
   public static final String[] options = {
      ":dshow-adev=none",
      ":dshow-vdev=",
      ":dshow-aspect-ratio=4\\:3",
      ":live-caching=200",
      ":sout-mp4-faststart"
    };
    
    public static void main(String[] args) throws Exception {
        
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(options);
        HeadlessMediaPlayer mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        mediaPlayer.playMedia("C:\\Users\\Aditya Rathi\\Videos\\Modern Family\\Modern family Season 3 Complete HDTV Bzingaz\\MF1.mp4",
                ":sout=#duplicate{dst=std{access=http,mux=ts,dst=127.0.0.1:5555}}");

        // Don't exit
        Thread.currentThread().join();
    }
    
}