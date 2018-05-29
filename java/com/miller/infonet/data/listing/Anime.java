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
import java.util.NoSuchElementException;
import java.util.Objects;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyMapProperty;
import javafx.beans.property.ReadOnlyMapWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.collections.FXCollections;

/**
 * A listing class used to store information on Anime.
 *
 * @author mille
 */
public class Anime extends Listing {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final List<String> animePropertyNames;
   
   static {
      animePropertyNames = new ArrayList<>(propertyNames);
      animePropertyNames.addAll(Arrays.asList("episodes", "completed", "director"));
      Listings.registerListingType("Anime", 
              () -> new AnimeBuilder(),
              animePropertyNames.toArray(new String[0]));
      
      Listings.setDefaultPropertyValue("Anime", "episodes", 0);
      Listings.setDefaultPropertyValue("Anime", "completed", false);
      Listings.setDefaultPropertyValue("Anime", "director", "");
   }
   /*+---------------------------------------------------------------+
     +                                                               +
     +                          Properties                           +
     +                                                               +
     +---------------------------------------------------------------+*/

   private ReadOnlyIntegerWrapper episodes;
   /**
    * Private access to episodes property.
    *
    * @return The episodes property.
    */
   private ReadOnlyIntegerWrapper privateEpisodesProperty() {
      if (episodes == null)
         episodes = new ReadOnlyIntegerWrapper(this, "episodes", 
                 (int) getProperty("episodes"));
      return episodes;
   }
   /**
    * Current episode count. Does not include OVAs or special episodes.
    * The episode count will never be negative.
    *
    * @return Episodes property.
    */
   public ReadOnlyIntegerProperty episodesProperty() {
      return privateEpisodesProperty().getReadOnlyProperty();
   }
   /**
    * Sets the current episode count. If {@code episodes} is negative,
    * then the episode count will be set to 0.
    * <p/>
    * The episode count cannot be modified if the Anime is completed (if
    * the {@link #isCompletedProperty() } method returns {@code true}). If the
    * Anime is set to complete, then an exception will be thrown.
    *
    * @param episodes The current number of episodes to be set.
    *
    * @throws IllegalStateException If the Anime is currently set to
    *                               completed.
    */
   public void setEpisodes(int episodes) {
      checkCompleted();
      privateEpisodesProperty().set((episodes < 0) ? 0 : episodes);
   }
   /**
    * Increments the episode count by one, then returns the current count.
    * <p/>
    * The episode count cannot be modified if the Anime is completed (if
    * the {@link #isCompletedProperty() } method returns {@code true}). If the
    * Anime is set to complete, then an exception will be thrown.
    *
    * @return The current episode count, after it has been incremented.
    *
    * @throws IllegalStateException If the Anime is currently set to
    *                               completed.
    */
   public int incrementEpisodes() {
      checkCompleted();
      int epi = episodesProperty().get();
      privateEpisodesProperty().set(epi++);
      return epi;
   }
   /**
    * Decrements the episode count by one, then returns the current count.
    * The episode count can never fall below 0. If the current count is 0,
    * then this method call will be ignored and 0 is returned.
    * <p/>
    * The episode count cannot be modified if the Anime is completed (if
    * the {@link #isCompletedProperty() } method returns {@code true}). If the
    * Anime is set to complete, then an exception will be thrown.
    *
    * @return The current episode count, after is has been decremented.
    *
    * @throws IllegalStateException If the Anime is currently set to
    *                               completed.
    */
   public int decrementEpisodes() {
      checkCompleted();
      int epi = episodesProperty().get();
      if (epi > 0)
         privateEpisodesProperty().set(epi--);
      return epi;
   }

   private ReadOnlyBooleanWrapper completed;
   /**
    * Private access to completed property.
    * @return The completed property.
    */
   private ReadOnlyBooleanWrapper privateIsCompletedProperty() {
      if (completed == null)
         completed = new ReadOnlyBooleanWrapper(this, "completed", 
                 (boolean) getProperty("completed"));
      return completed;
   }
   /**
    * States whether the Anime is completed. An Anime is completed if no
    * new episodes will be released. OVAs and special episodes are not
    * considered.
    *
    * @return {@code true} if the anime is completed. Otherwise
    *         {@code false}.
    */
   public ReadOnlyBooleanProperty isCompletedProperty() {
      return privateIsCompletedProperty().getReadOnlyProperty();
   }
   /**
    * Sets the completion status of the Anime. An Anime is completed if no
    * new episodes will be released. OVAs and special episodes are not
    * considered.
    *
    * @param isCompleted The completion status of the Anime.
    */
   public void setCompleted(boolean isCompleted) {
      privateIsCompletedProperty().set(isCompleted);
   }
   
   private ReadOnlyMapWrapper<String, String> characters;
   /**
    * Private access to the character list property.
    * 
    * @return The character list property.
    */
   private ReadOnlyMapWrapper<String, 
        String> privateCharacterListProperty() {
      if (characters == null)
         characters = new ReadOnlyMapWrapper<>(
                 this, "characters", FXCollections.observableHashMap());
      return characters;
   }
   /**
    * List of characters in this Anime, and their voice actors. Keys are 
    * for character names, values are for voice actor names.
    *
    * @return The anime's character list.
    */
   public ReadOnlyMapProperty<String, String> characterListProperty() {
      return privateCharacterListProperty().getReadOnlyProperty();
   }
   /**
    * Adds the character to this Anime's character list, setting their
    * voice actor to {@code null}. If a character by the specified name is
    * already present in the list, this method call is ignored.
    *
    * @param characterName The name of the character to add to the list.
    */
   public void addCharacter(String characterName) {
      privateCharacterListProperty().putIfAbsent(characterName, null);
   }
   /**
    * Adds a character to the list of characters for this Anime, pairing
    * them with a specific voice actor. If a character of the specified
    * name already has a voice actor registered to them, the previous
    * voice actor will be replaced.
    * <p/>
    * The {@code characterName} parameter cannot be {@code null}.
    * {@code voiceActor} is allowed to be {@code null}.
    *
    * @param characterName The name of the character.
    * @param voiceActor    The name of the voice actor.
    * 
    * @return The previous voice actor, or {@code null} if the character
    *         was not present in the list. Note that a {@code null} value
    *         could also mean that the character was in the list but had
    *         no voice actor mapped to them.
    */
   public String addCharacter(String characterName, String voiceActor) {
      return privateCharacterListProperty().
              put(Objects.requireNonNull(characterName), voiceActor);
   }
   /**
    * Removes the specified character and their associated voice actor
    * from this Anime's character list. The specified character will not
    * be present in the character list upon returning from this method.
    *
    * @param characterName The name of the character to remove.
    */
   public void removeCharacter(String characterName) {
      privateCharacterListProperty().remove(characterName);
   }
   /**
    * Returns the voice actor of the specified character. If this
    * character does not have a voice actor registered to them, then a
    * {@code null} will be returned.
    *
    * @param characterName The name of the character who's voice actor is
    *                      to be returned.
    *
    * @return The requested Voice actor.
    *
    * @throws NoSuchElementException If the specified character is not
    *                                listed with this Anime.
    */
   public String getVoiceActor(String characterName) {
      ReadOnlyMapWrapper<String, String> charList = 
              privateCharacterListProperty();
      if (!charList.containsKey(characterName))
         throw new NoSuchElementException("There is no character by" +
                 " the name of " + characterName + " in this Anime.");

      return charList.get(characterName);
   }
   /**
    * Sets the voice actor of a character in this Anime's character list.
    * If the character already had a voice actor associated with it, the
    * old voice actor will be replaced and returned.
    * <p/>
    * The {@code characterName} parameter cannot be {@code null}.
    * {@code voiceActor} is allowed to be {@code null}.
    *
    * @param characterName The name of the desired character.
    * @param voiceActor    The name of the voice actor to set.
    * @return The previous voice actor, or {@code null} if none existed.
    */
   public String setVoiceActor(String characterName, String voiceActor) {
      ReadOnlyMapWrapper<String, String> charList = 
              privateCharacterListProperty();
      if (!charList.containsKey(characterName))
         throw new NoSuchElementException("There is no character by" +
                 " the name of " + characterName + " in this Anime.");

      return charList.put(
              Objects.requireNonNull(characterName), voiceActor);
   }
   /**
    * Removes the association between the specified character and their
    * voice actor. If the specified character is not in the character
    * list, this method call will be ignored. Upon returning from this
    * method, subsequent calls to {@link #getVoiceActor(java.lang.String)}
    * for this character name will return {@code null} (assuming that
    * character is in the character list and its voice actor has not been
    * modified since). This method does NOT remove the character from the 
    * list.
    *
    * @param characterName The name of the character whose voice actor is
    *                      to be removed.
    */
   public void removeVoiceActor(String characterName) {
      ReadOnlyMapWrapper<String, String> charList = 
              privateCharacterListProperty();
      if (charList.containsKey(characterName))
         charList.put(characterName, null);
   }
   
   private ReadOnlyStringWrapper director;
   /**
    * Private access to the director property.
    * 
    * @return The director property.
    */
   private ReadOnlyStringWrapper privateDirectorProperty() {
      if (director == null)
         director = new ReadOnlyStringWrapper(this, "director", 
                 (String) getProperty("director"));
      return director;
   }
   /**
    * The lead director of the Anime. This method will never
    * return {@code null}, but it may return an empty string.
    *
    * @return The director of this anime.
    */
   public ReadOnlyStringProperty directorProperty() {
      return privateDirectorProperty().getReadOnlyProperty();
   }
   /**
    * Sets the lead director for this Anime. The {@code director}
    * parameter cannot be {@code null}, but it can be a empty string.
    *
    * @param director The director of the anime.
    */
   public void setDirector(String director) {
      if (director == null)
            throw new NullPointerException();
      
      privateDirectorProperty().set(director);
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Sole constructor.
    */
   protected Anime() {
      super("Anime");
   }
   
   public static Anime build(String title) {
      Anime anime = new Anime();
      anime.setTitle(title);
      return anime;
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   private void checkCompleted() {
      if (privateIsCompletedProperty().get())
         throw new IllegalStateException("The anime is completed; " +
                 "the episode count cannot be modified.");
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   public static class AnimeBuilder extends ListingBuilder<Anime> {

      public AnimeBuilder() {
         super("Anime");
      }
      
      public void setEpisodes(int episodes) { setProperty("episodes", (episodes < 0) ? 0 : episodes); }
      
      public void setCompleted(boolean isCompleted) { setProperty("completed", isCompleted); }
      
      public void setDirector(String directorName) { setProperty("director", (directorName == null) ? "" : directorName); }

      @Override
      protected Anime doBuild() {
         Anime anime = new Anime();
         Map<String, Object> properties = properties();
         
         anime.setTitle((String) properties.get("title"));
         anime.setCreator((String) properties.get("creator"));
         anime.setEpisodes((int) properties.get("episodes"));
         anime.setCompleted((boolean) properties.get("completed"));
         anime.setDirector((String) properties.get("director"));
         
         return anime;
      }
   }
}
