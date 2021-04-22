package com.iluwatar.presentation;

import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;

import lombok.extern.slf4j.Slf4j;

/**
 * Generates the GUI of albums.
 */
@Slf4j
public class View {
  PresentationMod model;

  TextField txtTitle;
  TextField txtArtist;
  JCheckBox chkClassical;
  TextField txtComposer;
  JList<String> albumList;
  JButton apply;
  JButton cancel;

  boolean notLoadView;

  /**
   * constructor method.
   */
  public View() {
    model = new PresentationMod(PresentationMod.albumDataSet());
    notLoadView = true;
  }

  /**
   * save the data to PresentationModel.
   */
  public void saveToPMod() {
    LOGGER.info("save data to PresentationModel");
    model.setArtist(txtArtist.getText());
    model.setTitle(txtTitle.getText());
    model.setIsClassical(chkClassical.isSelected());
    model.setComposer(txtComposer.getText());
  }

  /**
   * load the data from PresentationModel.
   */
  public void loadFromPMod() {
    LOGGER.info("load data from PresentationModel");
    txtArtist.setText(model.getArtist());
    txtTitle.setText(model.getTitle());
    chkClassical.setSelected(model.getIsClassical());
    txtComposer.setEditable(model.getIsClassical());
    txtComposer.setText(model.getComposer());
  }

  /**
   * initialize the GUI.
   */
  public void createView() {
    var frame = new JFrame("Album");
    var b1 = Box.createHorizontalBox();

    frame.add(b1);
    albumList = new JList<>(model.getAlbumList());
    albumList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        model.setSelectedAlbumNumber(albumList.getSelectedIndex() + 1);
        loadFromPMod();
      }
    });
    b1.add(albumList);

    var b2 = Box.createVerticalBox();
    b1.add(b2);

    txtArtist = new TextField();
    txtTitle = new TextField();
    txtArtist.setSize(200, 50);
    txtTitle.setSize(200, 50);

    chkClassical = new JCheckBox();
    txtComposer = new TextField();
    chkClassical.addActionListener(itemEvent -> {
      txtComposer.setEditable(chkClassical.isSelected());
      if (!chkClassical.isSelected()) {
        txtComposer.setText("");
      }
    });
    txtComposer.setSize(200, 50);
    txtComposer.setEditable(model.getIsClassical());

    apply = new JButton("Apply");
    apply.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        saveToPMod();
        loadFromPMod();
      }
    });
    cancel = new JButton("Cancel");
    cancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        loadFromPMod();
      }
    });

    b2.add(txtArtist);
    b2.add(txtTitle);

    b2.add(chkClassical);
    b2.add(txtComposer);

    b2.add(apply);
    b2.add(cancel);

    frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    frame.setBounds(200, 200, 500, 300);
    frame.setVisible(true);
  }

}
