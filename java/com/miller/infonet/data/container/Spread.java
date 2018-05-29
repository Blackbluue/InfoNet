/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.data.container;

import com.miller.infonet.data.listing.Listing;

import java.util.AbstractList;
import java.util.Collection;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author mille
 * @param <E> A type of listing
 */
public class Spread<E extends Listing> extends AbstractList<E> implements ListingContainer<E>, ObservableList<E> {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private final ObservableList<E> data;
   /* Used to bind this spread's size property to the size of data*/
   public final String LISTING_TYPE;
   
   /** An empty spread (unmodifiable). */
   public static final Spread<Listing> EMPTY_SPREAD = new Spread<>();
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                          Properties                           +
     +                                                               +
     +---------------------------------------------------------------+*/
   private ReadOnlyIntegerWrapper sizeProperty;
   /**
    * Private access to the size property.
    * 
    * @return The size property.
    */
   private ReadOnlyIntegerWrapper privateSizeProperty() {
      if (sizeProperty == null) {
         sizeProperty = new ReadOnlyIntegerWrapper(this, "size");
         
         data.addListener(new WeakInvalidationListener(
                 dList -> sizeProperty.set(((Collection<E>) dList).size())));
      }
      return sizeProperty;
   }
   /**
    * The size of this Spread's list of data.
    * 
    * @return The size of this spread.
    */
   public ReadOnlyIntegerProperty sizeProperty() {
      return privateSizeProperty().getReadOnlyProperty();
   }
   @Override
   public int size() {
     return privateSizeProperty().get();
  }
   
   private StringProperty nameProperty;
   /**
    * The name of this Spread.
    * 
    * @return The name of this Spread.
    */
   public StringProperty nameProperty() {
      if (nameProperty == null)
         nameProperty = new SimpleStringProperty(this, "name", "");
      return nameProperty;
   }
   /**
    * The name of this Spread.
    * 
    * @return The name of this Spread.
    */
   public String getName() {
      return nameProperty().get();
   }
   /**
    * Sets the name of this Spread. If {@code null} is entered, then an
    * empty string will be used.
    * 
    * @param name The new name of this spread.
    */
   public void setName(String name) {
      nameProperty().set((name == null) ? "" : name);
   }

   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Creates a Spread object. {@code spread} is used to populate this 
    * Spread with an initial list of listings. A {@code null} value is 
    * not allowed.
    * <p/>
    * The Spread's listing type defines the type of listings that the 
    * spread will handle and cannot be changed once set. The listing type
    * is copied from the given Spread.
    * 
    * @param <E>         The type of Listing this Spread will handle.
    * @param spread      A Spread who's contents will be copied to this
    *                    Spread.
    * 
    * @return A newly constructed Spread object.
    */
   public static <E extends Listing> Spread<E> build(Spread<E> spread) {
      return new Spread<>(spread.LISTING_TYPE, spread);
   }
   
   public static <E extends Listing> Spread<E> build(String listingType) {
      return new Spread<>(listingType, null);
   }
   
   /**
    * Creates an empty, immutable spread.
    */
   private Spread() {
      LISTING_TYPE = "Listing";
      data = FXCollections.emptyObservableList();
   }
      
   private Spread(String listingType, Spread<E> spread) {
      LISTING_TYPE = listingType;
      this.data = FXCollections.observableArrayList();
      if (spread != null)
         data.addAll(spread.data);
   }
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   @Override
   public E get(int index) {
      return data.get(index);
   }
  
   @Override
   public void add(int index, E element) {
      if (index > data.size() || index < 0)
            throw new IndexOutOfBoundsException();
      
      if (data.contains(element)) {
         if (index == data.size())
            index--;
         data.remove(element);
      }
      data.add(index, element);
   }

   @Override
   public E set(int index, E element) {
      if (data.contains(element)) {
         if (index >= data.size() || index < 0)
            throw new IndexOutOfBoundsException();
         if (index == data.size() - 1)
            index--;
         data.remove(element);
         data.add(index, element);
         return null;
      }
      return data.set(index, element);
   }

   @Override
   public E remove(int index) {
      return data.remove(index);
   }
   
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      
      sb.append("Name: ").append(nameProperty().get()).append("\n");
      sb.append("Size: ").append(size()).append("\n");
      sb.append("Data Size: ").append(data.size()).append("\n");
      
      data.stream().forEach(l -> sb.append(l).append("\n"));
      return sb.toString();
   }

   @Override
   public void addListener(InvalidationListener listener) {
      data.addListener(listener);
   }

   @Override
   public void removeListener(InvalidationListener listener) {
      data.removeListener(listener);
   }

   @Override
   public void addListener(ListChangeListener<? super E> listener) {
      data.addListener(listener);
   }

   @Override
   public void removeListener(ListChangeListener<? super E> listener) {
      data.removeListener(listener);
   }

   @Override
   public boolean addAll(E... elements) {
      return data.addAll(elements);
   }

   @Override
   public boolean setAll(E... elements) {
      return data.setAll(elements);
   }

   @Override
   public boolean setAll(Collection<? extends E> col) {
      return data.setAll(col);
   }

   @Override
   public boolean removeAll(E... elements) {
      return data.removeAll(elements);
   }

   @Override
   public boolean retainAll(E... elements) {
      return data.retainAll(elements);
   }

   @Override
   public void remove(int from, int to) {
      data.remove(from, to);
   }
}
