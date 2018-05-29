/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.data.listing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mille
 */
public class Manga extends Listing {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final List<String> mangaPropertyNames;
   
   static {
      mangaPropertyNames = new ArrayList<>(propertyNames);
      mangaPropertyNames.addAll(Arrays.asList("chapters", "completed", "artist"));
      Listings.registerListingType("Manga", 
              () -> new MangaBuilder(),
              mangaPropertyNames.toArray(new String[0]));
      
      Listings.setDefaultPropertyValue("Manga", "chapters", 0);
      Listings.setDefaultPropertyValue("Manga", "completed", false);
      Listings.setDefaultPropertyValue("Manga", "artist", "");
   }
   /**
    * Creates a Manga object, initializing its {@code title},
    * {@code creator}, and {@code released} fields.
    *
    */
   public Manga() {
      super("Manga");
   }

   private void setChapters(int chaptesr) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   private void setCompleted(boolean isCompleted) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   private void setArtist(String artist) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   public static class MangaBuilder extends ListingBuilder<Manga> {

      public MangaBuilder() {
         super("Manga");
      }
      
      public void setChapters(int chapters) { setProperty("chapters", (chapters < 0) ? 0 : chapters); }
      
      public void setCompleted(boolean isCompleted) { setProperty("completed", isCompleted); }
      
      public void setArtist(String artistName) { setProperty("artist", (artistName == null) ? "" : artistName); }

      @Override
      protected Manga doBuild() {
         Manga manga = new Manga();
         Map<String, Object> properties = properties();
         
         manga.setTitle((String) properties.get("title"));
         manga.setCreator((String) properties.get("creator"));
         manga.setChapters((int) properties.get("chapters"));
         manga.setCompleted((boolean) properties.get("completed"));
         manga.setArtist((String) properties.get("artist"));
         
         return manga;
      }
   }
}
