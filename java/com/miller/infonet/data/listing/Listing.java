/*
 * Project Name:
 * Author: Raymone Miller
 * Date:
 * Version #: 0.0.0
 */
package com.miller.infonet.data.listing;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.collections.FXCollections;

/**
 *
 * @author mille
 */
public abstract class Listing {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   public final String LISTING_TYPE;
   /**
    * List of property names that are common to all Listing types. 
    * Subtypes may use this list to initialize their own property lists 
    * and add upon them. The base list is immutable.
    */
   protected static final List<String> propertyNames = 
           Collections.unmodifiableList(Arrays.asList("title", "creator"));
   private final Map<String, Object> properties;

   /*+---------------------------------------------------------------+
     +                                                               +
     +                          Properties                           +
     +                                                               +
     +---------------------------------------------------------------+*/
   private ReadOnlyStringWrapper title;
   /**
    * Private access to the title property.
    *
    * @return The title property.
    */
   private ReadOnlyStringWrapper privateTitleProperty() {
      if (title == null)
         title = new ReadOnlyStringWrapper(this, "title", 
                 (String) properties.get("title"));
      return title;
   }
   /**
    * The title of this Listing.
    *
    * @return The title.
    *
    * @see #altTitlesProperty()
    */
   public ReadOnlyStringProperty titleProperty() {
      return privateTitleProperty().getReadOnlyProperty();
   }
   public String getTitle() {
      return privateTitleProperty().get();
   }
   /**
    * Sets the title property. Only affects the main title. If a
    * {@code null} value is detected, an empty string will be entered.
    *
    * @param title The title to set.
    */
   public void setTitle(String title) {
      privateTitleProperty().set((title == null) ? "" : title);
   }
   /**
    * Clears the Listing's title, setting it to an empty string.
    */
   public void clearTitle() {
      privateTitleProperty().set("");
   }

   /** Alternate titles for the listing. This field may be left empty. */
   private ReadOnlyListWrapper<String> altTitles;
   /**
    * Private access to the alt titles property.
    *
    * @return The alt titles property.
    */
   private ReadOnlyListWrapper<String> privateAltTitleProperty() {
      if (altTitles == null)
         altTitles = new ReadOnlyListWrapper<>(
                 this, "altTitles", FXCollections.observableArrayList());
      return altTitles;
   }
   /**
    * Alternate titles for the listing. It is possible for this list to be
    * empty.
    *
    * @return The alt titles property.
    *
    * @see #titleProperty()
    */
   public ReadOnlyListProperty<String> altTitlesProperty() {
      return privateAltTitleProperty().getReadOnlyProperty();
   }
   public List<String> getAltTitles() {
      return Collections.unmodifiableList(privateAltTitleProperty().get());
   }
   /**
    * Adds an alternate title to this Listing's list of alternate titles.
    * If the title is already present in the list, this method call will
    * be ignored. Only affects the list of alternate titles, not the main
    * title. {@code null} values will be treated as empty strings.
    *
    * @param altTitle The alternate title to add.
    */
   public void addAltTitle(String altTitle) {
      ReadOnlyListWrapper<String> titleList = privateAltTitleProperty();
      if (altTitle == null)
         altTitle = "";
      if (!titleList.contains(altTitle))
         titleList.add(altTitle);
   }
   /**
    * Removes an alternate title from this Listing's list of alternate
    * titles. If the title is not present in the list, the list remains
    * unchanged. Only affects the list of alternate titles, not the main
    * title. {@code null} values will be treated as empty strings.
    *
    * @param altTitle The alternate title to remove.
    */
   public void removeAltTitle(String altTitle) {
      ReadOnlyListWrapper<String> titleList = privateAltTitleProperty();
      if (altTitle == null)
         altTitle = "";
      titleList.remove(altTitle);
   }
   /**
    * Clears all alternate titles from this Listing's list of alternate
    * titles. The list is guaranteed to be empty after this call finishes.
    */
   public void clearAltTitleList() {
      privateAltTitleProperty().clear();
   }

   private ReadOnlyStringWrapper creator;
   /**
    * Private access to the creator property.
    *
    * @return The creator property.
    */
   private ReadOnlyStringWrapper privateCreatorProperty() {
      if (creator == null)
         creator = new ReadOnlyStringWrapper(this, "creator", 
                 (String) properties.get("creator"));
      return creator;
   }
   /**
    * The creator of this Listing. Can be a single person or a group.
    *
    * @return The creator.
    */
   public ReadOnlyStringProperty creatorProperty() { return privateCreatorProperty().getReadOnlyProperty(); }
   /**
    * Returns the value of the creator property.
    * 
    * @return The creator of this Listing.
    */
   public String getCreator() { return privateCreatorProperty().get(); }
   /**
    * Sets the creator of this Listing.
    *
    * @param creator The creator of this listing.
    */
   public void setCreator(String creator) { privateCreatorProperty().set((creator == null) ? "" : creator); }

   private ReadOnlyObjectWrapper<Calendar> released;
   /**
    * Private access to the released property.
    *
    * @return The released property.
    */
   private ReadOnlyObjectWrapper<Calendar> privateReleasedProperty() {
      if (released == null)
         released = new ReadOnlyObjectWrapper<>(
                 this, "released", Calendar.getInstance());
      return released;
   }
   /**
    * The date the Listing was publicly released.
    *
    * @return The date the listing was publicly released.
    */
   public ReadOnlyObjectProperty<Calendar> releasedProperty() { return privateReleasedProperty().getReadOnlyProperty(); }
   public Calendar getReleased() { return privateReleasedProperty().get(); }
   /**
    * Sets the date the Listing was publicly released.
    *
    * @param released The date the listing was released.
    */
   public void setReleased(Calendar released) {
      if (released == null)
         throw new NullPointerException();
      privateReleasedProperty().set(released);
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Sole constructor, called by subtypes. Sets up the default property
    * values, which can be retrieved with 
    * {@link #getProperty(java.lang.String) getProperty(String)}.
    * 
    * @param listingType The name of this Listing type.
    */
   protected Listing(String listingType) {
      this.LISTING_TYPE = listingType;
      properties = new HashMap<>(Listings.defaultProperties(listingType));
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Retrieves the value of the desired property. {@code propertyName}
    * must not be {@code null}, and must be a valid property name. The
    * returned property value may be {@code null}.
    * 
    * @param propertyName The name of the property.
    * 
    * @return The property's value.
    */
   public final Object getProperty(String propertyName) {
      if (propertyName == null)
         throw new NullPointerException();
      if (!properties.containsKey(propertyName))
         throw new IllegalArgumentException("The is no property by the " +
                 "name " + propertyName);
      return properties.get(propertyName);
   }
   
   @Override
   public String toString() {
      StringBuilder s = new StringBuilder();

      s.append("Title: ").append(getTitle()).append("\n");
      s.append("Alternate Titles: ");
      if (getAltTitles().isEmpty())
         s.append("None").append("\n");
      else {
         s.append("\n");
         getAltTitles().stream().
                 forEach(t -> s.append("\t").append(t).append("\n"));
      }
      s.append("Creator: ").append(getCreator()).append("\n");
      s.append("Released: ").append(calendarString(getReleased())).append("\n");

      return s.toString();
   }

   private String calendarString(Calendar c) {
      StringBuilder s = new StringBuilder();
      s.append(c.get(Calendar.YEAR)).append("/");
      s.append(c.get(Calendar.MONTH)).append("/");
      s.append(c.get(Calendar.DAY_OF_MONTH));
      return s.toString();
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   public static abstract class ListingBuilder<E extends Listing> {
      private E content;
      private final Map<String, Object> properties;
      private final String type;

      protected ListingBuilder(String listingType) {
         this.type = listingType;
         properties =
                 new HashMap<>(Listings.defaultProperties(listingType));
      }

      /**
       * Builds the Listing object and returns it. The object is stored so
       * that it may be retrieved again at a later date using the
       * {@link #get()} method. Although not required to, a call to this
       * method generally will construct a new object each time, discarding
       * the old stored value.
       *
       * @return The built object.
       */
      public E build() { return (content = doBuild()); }

      /**
       * This method must be overridden by subtypes in order to build the
       * Listing object.
       *
       * @return The built Listing.
       */
      protected abstract E doBuild();

      /**
       * Retrieves the stored Listing object. Calling this method will not
       * build a new object, and if called before a call to {@link #build()}
       * then {@code null} will be returned.
       *
       * @return
       */
      public E get() { return content; }

      /**
       * Sets the specified property value for the Listing to be constructed.
       * If there is no such property name, and exception will be thrown. The
       * method returns a reference to this builder, so that the method may
       * be chained multiple times.
       * <p/>
       * It may be preferable to directly call the desired property's
       * {@code set} method, as this method is not type safe and the desired
       * property may have certain restrictions on its possible values, which
       * this method does not check.
       *
       * @param propertyName  The name of the property.
       * @param propertyValue The value of the property.
       *
       * @return This builder.
       */
      public final ListingBuilder<E> setProperty(String propertyName,
              Object propertyValue) {
         if (!properties.containsKey(propertyName))
            throw new IllegalArgumentException("There is no property " +
                    "by the name of " + propertyName);
         properties.put(propertyName, propertyValue);
         return this;
      }
      
      public final void setTitle(String title) {
         properties.put("title", (title == null) ? "" : title);
      }
      
      public final void setCreator(String creator) {
         properties.put("creator", (creator == null) ? "" : creator);
      }

      /**
       * Clears every property in the builder, setting them to their 
       * default values.
       */
      public void clear() {
         Map<String, Object> defaults = Listings.defaultProperties(type);
         
         properties.entrySet().
                 stream().
                 forEach(prop -> prop.setValue(defaults.get(prop.getKey())));
      }

      /**
       * Returns the stored properties, so that subtypes may use them to
       * construct the Listing. The returned Map is unmodifiable; the
       * {@link #setProperty(java.lang.String, java.lang.Object)
       * setProperty(String, Object)} method must be used to modify the
       * properties.
       *
       * @return The properties map.
       */
      protected final Map<String, Object> properties() { return Collections.unmodifiableMap(properties); }
   }
}
