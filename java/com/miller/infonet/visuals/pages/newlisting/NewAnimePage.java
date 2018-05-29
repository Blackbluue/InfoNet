/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals.pages.newlisting;

import com.miller.infonet.data.listing.Anime.AnimeBuilder;

import java.util.function.UnaryOperator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;

import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author mille
 */
class NewAnimePage extends NewListingPage {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private final AnimeBuilder builder;
   private final GridPane inputArea;
   private final TextField inputTitle;
   private final TextField inputCreator;
   private final TextField inputEpisodes;
   private final CheckBox inputCompleted;
   private final TextField inputDirector;
   private final Button submitButton;
   
   static {
      registerNewListingPage("Anime", new NewAnimePage());
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/   
   public NewAnimePage() {
      super("Anime");
      TextFormatter<Integer> episodesFormatter = 
              new TextFormatter<>(new IntegerStringConverter(), null, new IntegerFilter());
      
      builder = (AnimeBuilder) getBuilder();
      inputArea = new GridPane();
      
      inputTitle = new TextField();
      inputTitle.setPromptText("Title of the anime");
      
      inputCreator = new TextField();
      inputCreator.setPromptText("Creator of the anime");
      
      inputEpisodes = new TextField();
      inputEpisodes.setPromptText("Number of episodes for the anime");
      inputEpisodes.setTextFormatter(episodesFormatter);
      
      inputCompleted = new CheckBox("Completed?");
      
      inputDirector = new TextField();
      inputDirector.setPromptText("Director of the anime");
      
      submitButton = new Button("Submit");
      
      submitButton.setOnAction(new OnSubmitEventHandler());
      
      
      inputArea.add(inputTitle, 0, 0, 3, 1);
      inputArea.add(inputCreator, 1, 0, 3, 1);
      inputArea.add(inputEpisodes, 0, 1, 3, 1);
      inputArea.add(inputCompleted, 1, 1, 3, 1);
      inputArea.add(inputDirector, 0, 2, 3, 1);
      inputArea.add(submitButton, 0, 3);
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   @Override
   public void clear() {
      inputCompleted.setSelected(false);
      inputCreator.clear();
      inputDirector.clear();
      inputEpisodes.clear();
      inputTitle.clear();
      builder.clear();
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   private class OnSubmitEventHandler implements EventHandler<ActionEvent> {
      
      @Override
      public void handle(ActionEvent event) {
         builder.setTitle(inputTitle.getText());
         builder.setCreator(inputCreator.getText());
         builder.setEpisodes((Integer) inputEpisodes.getTextFormatter().getValue());
         builder.setCompleted(inputCompleted.isSelected());
         builder.setDirector(inputDirector.getText());
         NewAnimePage.this.buildContent();
      }
   }
   
   private static class IntegerFilter implements UnaryOperator<Change> {

      @Override
      public TextFormatter.Change apply(Change change) {
         try {
            Integer.parseInt(change.getText());
         } catch (NumberFormatException e) {
            change.setText("");
         }
         return change;
      }
      
   }
}
