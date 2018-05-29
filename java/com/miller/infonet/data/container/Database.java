/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.data.container;

import com.miller.infonet.data.listing.Listing;
import com.miller.infonet.data.listing.Listings;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import javafx.beans.InvalidationListener;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;


/**
 *
 * @author mille
 */
public class Database extends AbstractSet<Listing> implements 
        ListingContainer<Listing>, ObservableSet<Listing> {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private final Map<String, ObservableSet<Listing>> data;
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                          Properties                           +
     +                                                               +
     +---------------------------------------------------------------+*/
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   public Database() {
      data = new HashMap<>();
      Listings.listingTypeNames().stream().
              forEach(type -> data.put(type, FXCollections.observableSet()));
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/

   @Override
   public Iterator<Listing> iterator() {
      Stream<Listing> stream = null;
      return data.values().
              stream().
              map(set -> set.stream()).
              reduce(stream, Stream::concat).
              iterator();
   }

   @Override
   public int size() {
      return data.values().stream().
              map((set) -> set.size()).
              reduce(0, Integer::sum);
   }
   
   @Override
   public boolean add(Listing listing) {
      return data.get(listing.LISTING_TYPE).add(listing);
   }
   
   public ObservableList<? extends Listing> getByType(String typeName) {
      return FXCollections.observableArrayList(data.get(typeName));
   }

   @Override
   public void addListener(InvalidationListener listener) {
      data.values().forEach((ObservableSet set) -> set.addListener(
              listener));
   }

   @Override
   public void removeListener(InvalidationListener listener) {
      data.values().forEach((ObservableSet set) -> set.removeListener(
              listener));
   }

   @Override
   public void addListener(SetChangeListener<? super Listing> listener) {
      data.values().forEach((ObservableSet set) -> set.addListener(
              listener));
   }

   @Override
   public void removeListener(SetChangeListener<? super Listing> listener) {
      data.values().forEach((ObservableSet set) -> set.removeListener(
              listener));
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
}
