package lk.ijse.dep10.ap.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class Mp3PlayerController {

    public AnchorPane root;
    public Label lblMediaFile;
    public ImageView imgPlayOrPause;
    public ImageView imgStop;
    public ImageView imgLoop;
    public ImageView imgMuteUnMute;
    public Slider sldVolume;
    public Slider sldTime;
    public Label lblTime;
    public Label lblTime1;
    @FXML
    private ImageView imgOpenFile;

    /*user defined*/
    private File mp3File=null;
    private Media media;
    private MediaPlayer audioPlayer;



    public void initialize() {
        lblTime.setText("--/--");
        Image imageOpen = new Image("image/open.png");       //open image
        imgOpenFile.setImage(imageOpen);

        Image imagePlayOrPause = new Image("image/play.png");       //play or pause
        imgPlayOrPause.setImage(imagePlayOrPause);

        Image imageStop = new Image("image/stop.png");       //stop
        imgStop.setImage(imageStop);

        Image imageLoop = new Image("image/loop.png");       //loop
        imgLoop.setImage(imageLoop);

        imgMuteUnMute.setImage(new Image("image/volume.png"));


        lblMediaFile.setText("Open a Mp3 File or Drag and Drop");       //open label





    }
    /*File open*/

    @FXML
    void imgOpenFileOnMouseEntered(MouseEvent event) {
        imgOpenFile.setOpacity(0.4);

    }

    @FXML
    void imgOpenFileOnMouseExited(MouseEvent event) {
        imgOpenFile.setOpacity(1);

    }

    @FXML
    void imgOpenFileOnMousePressed(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("Audio files", "*.mp3", "*.MP3"));

        fileChooser.setTitle("Select mp3 file to play");


        mp3File = fileChooser.showOpenDialog(imgOpenFile.getScene().getWindow());

        if (mp3File == null || !mp3File.exists()) return;

        media = new Media(mp3File.toURI().toString());
        lblMediaFile.setText(mp3File.toString());
        audioPlayer = new MediaPlayer(media);


    }


    /*Drag n drop*/

    public void rootOnDragOver(DragEvent dragEvent) {
        dragEvent.acceptTransferModes(TransferMode.MOVE);
    }


    public void rootOnDragDropped(DragEvent dragEvent) {
        String[] acceptedFileType = new String[]{"mp3", "MP3", "Audio file", "audio file"};
        dragEvent.setDropCompleted(true);
        mp3File = dragEvent.getDragboard().getFiles().get(0);

        if(mp3File==null)return;

        String fileName = mp3File.getName();
        String extension =
                fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());


        for (String s : acceptedFileType) {
            if(s.equals(extension)){
                lblMediaFile.setText(mp3File.toString());
                media = new Media(mp3File.toURI().toString());
                audioPlayer = new MediaPlayer(media);
                lblMediaFile.setText(mp3File.toString());

                return;
            }

        }
        new Alert(Alert.AlertType.WARNING, "Please select audio file formats only!").showAndWait();
        mp3File=null;



    }



    /*Play or pause*/
    public void imgPlayOrPauseOnMouseEntered(MouseEvent mouseEvent) {
        imgPlayOrPause.setOpacity(0.4);
    }

    public void imgPlayOrPauseOnMouseExited(MouseEvent mouseEvent) {
        imgPlayOrPause.setOpacity(1);
    }

    public void imgPlayOrPauseOnMousePressed(MouseEvent mouseEvent) {
        if (audioPlayer == null) {
            new Alert(Alert.AlertType.WARNING, "Please Select Audio File Before Play!").showAndWait();
            return;
        }

        /*Time Slider bar*/

        sldTime.setMin(0);      //slider min and max values
        sldTime.setMax(audioPlayer.getTotalDuration().toSeconds());

        audioPlayer.currentTimeProperty().addListener(time->{                   //get duration value and set for slider
            sldTime.setValue(audioPlayer.getCurrentTime().toSeconds());
            lblTime.setText(String.format("%s%s%s"
                    ,(int)audioPlayer.getCurrentTime().toSeconds(),"/",(int)audioPlayer.getTotalDuration().toSeconds()));

        });

        sldTime.valueProperty().addListener(slider->{
            if (sldTime.isValueChanging()) {
                audioPlayer.seek(new Duration(sldTime.getValue()));

            }
        });



        if (!(audioPlayer.getStatus() == MediaPlayer.Status.PLAYING)) {
            Image imagePause = new Image("image/pause.png");       //open image
            imgPlayOrPause.setImage(imagePause);

            audioPlayer.play();



            audioPlayer.volumeProperty().bind(sldVolume.valueProperty());

        }else {
            Image imagePlay = new Image("image/play.png");
            imgPlayOrPause.setImage(imagePlay);

            audioPlayer.pause();
        }



    }



    /*Stop*/
    public void imgStopOnMouseEntered(MouseEvent mouseEvent) {
        imgStop.setOpacity(0.4);
    }

    public void imgStopOnMouseExited(MouseEvent mouseEvent) {
        imgStop.setOpacity(1);
    }

    public void imgStopOnMousePressed(MouseEvent mouseEvent) {
        if(audioPlayer==null)return;
        audioPlayer.stop();

        if((audioPlayer.getStatus()== MediaPlayer.Status.PLAYING)) {
            imgPlayOrPause.setImage(new Image("image/play.png"));
        }

    }


    /*Loop*/

    public void imgLoopOnMouseEntered(MouseEvent mouseEvent) {


        imgLoop.setOpacity(0.4);
    }

    public void imgLoopOnMouseExited(MouseEvent mouseEvent) {
        imgLoop.setOpacity(1);
    }

    public void imgLoopOnMousePressed(MouseEvent mouseEvent) {
        if(audioPlayer==null)return;
        audioPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }

    public void imgMuteUnMuteOnMouseEntered(MouseEvent mouseEvent) {
        imgMuteUnMute.setOpacity(0.4);

    }

    public void imgMuteUnMuteOnMouseExited(MouseEvent mouseEvent) {
        imgMuteUnMute.setOpacity(1);
    }

    public void imgMuteUnMuteOnMousePressed(MouseEvent mouseEvent) {
        if(audioPlayer==null||!(audioPlayer.getStatus()==MediaPlayer.Status.PLAYING)) return;
        if(!audioPlayer.isMute()) {
            audioPlayer.setMute(true);
            imgMuteUnMute.setImage(new Image("image/mute.png"));
            return;
        }
        audioPlayer.setMute(false);
        imgMuteUnMute.setImage(new Image("image/volume.png"));

    }




}
