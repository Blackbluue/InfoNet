/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.base;

import com.miller.infonet.data.listing.Anime;
import com.miller.infonet.data.container.Spread;

import java.util.Arrays;
import java.util.Calendar;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author mille
 */
public class InfoNet_3 extends Application {

   @Override
   public void start(Stage primaryStage) {
      Spread<Anime> spread1 = shounenAnimeSpread();
      Spread<Anime> spread2 = someShounenAnimeSpread();
      
      Options.getOptions().getSpreadList().
              addAll(spread1, spread2, copiedSpread(spread2), copiedSpread(spread1));

      Scene scene = new Scene(PageManager.getManager().getCurrentPage(), 1000, 1000);
      
      primaryStage.setTitle("InfoNet");
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   Spread<Anime> shounenAnimeSpread() {
      Spread spread = Spread.build("Anime");
      
      spread.add(buildBerserk());
      spread.addAll(Arrays.asList(buildBleach(), buildBokuNoHero(), 
              buildDGrayMan(), buildNaruto(), buildOnePiece(), 
              buildBerserk()));
      spread.setName("Shounen Anime");
      
      Options.getOptions().getDatabase().addAll(spread);
      Options.getOptions().getSpreadList().add(spread);
      return spread;
   }
   
   Spread<Anime> someShounenAnimeSpread() {
      Spread<Anime> spread = Spread.build("Anime");
      
      spread.addAll(Arrays.asList(buildBokuNoHero(), buildDGrayMan(), 
              buildOnePiece(), buildBerserk()));
      spread.setName("Some Shounen Anime");
      
      Options.getOptions().getDatabase().addAll(spread);
      Options.getOptions().getSpreadList().add(spread);
      return spread;
   }
   
   Spread<Anime> copiedSpread(Spread spread) {
      Spread<Anime> s = Spread.build(spread);
      s.add(buildBerserk());
      s.setName("Copied Spread");
      Options.getOptions().getDatabase().addAll(spread);
      return s;
   }
   
   Anime buildBleach() {
      Anime anime = Anime.build("Bleach");
      anime.setEpisodes(366);
      anime.setCompleted(true);
      anime.setCreator("Tite Kubo");
      anime.setDirector("Some guy");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2002);
      anime.setReleased(calendar);
      return anime;
   }
   
   Anime buildNaruto() {
      Anime anime = Anime.build("Naruto");
      anime.setEpisodes(220);
      anime.setCompleted(true);
      anime.setCreator("Masashi Kishimoto");
      anime.setDirector("Some other guy");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2000);
      anime.setReleased(calendar);
      return anime;
   }
   
   Anime buildOnePiece() {
      Anime anime = Anime.build("One Piece");
      anime.setEpisodes(837);
      anime.setCompleted(false);
      anime.setCreator("Eiichiro Oda");
      anime.setDirector("Another guy");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 1999);
      anime.setReleased(calendar);
      return anime;
   }
   
   Anime buildDGrayMan() {
      Anime anime = Anime.build("D.Gray-Man");
      anime.setEpisodes(72);
      anime.setCompleted(false);
      anime.setCreator("Katsura Hoshino");
      anime.setDirector("Maybe a girl");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2005);
      anime.setReleased(calendar);
      return anime;
   }
   
   Anime buildBokuNoHero() {
      Anime anime = Anime.build("Boku no Hero Academia");
      anime.setEpisodes(46);
      anime.setCompleted(false);
      anime.setCreator("Kohei Horikoshi");
      anime.setDirector("I don't care");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2002);
      anime.setReleased(calendar);
      return anime;
   }
   
   Anime buildBerserk() {
      Anime anime = Anime.build("Berserk");
      anime.setEpisodes(52);
      anime.setCompleted(true);
      anime.setCreator("Kentaro Miura");
      anime.setDirector("Cool guy");
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 1998);
      anime.setReleased(calendar);
      return anime;
   }

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      launch(args);
   }

}
