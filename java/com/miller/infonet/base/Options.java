/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.base;

import com.miller.infonet.data.container.Database;
import com.miller.infonet.visuals.pages.home.SpreadViewer;

import com.miller.infonet.data.container.Spread;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author mille
 */
public final class Options {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final Options OPTIONS = new Options();
   private Database database;
   private ObservableList<Spread> spreadList;
   private int initialSpread;
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   public static Options getOptions() {
      return OPTIONS;
   }
   
   /**
    * Sole constructor.
    */
   private Options() {
      initialSpread = SpreadViewer.NO_INITIAL;
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /* Not finished*/
   /**
    * Saves the stored Spread list to memory.
    * <p/>
    * Each call to this method involves a disk write operation.
    */
   public void saveData() {
      throw new UnsupportedOperationException("Not yet supported");
   }
   
   /* Not finished*/
   /**
    * Loads the saved Spread list from memory. The list is saved 
    * internally, and can be retrieved with 
    * {@link #getSpreadList() getSpreadList()}. This method disposes of 
    * the current Spread list to read in a fresh one.
    * <p/>
    * Each call to this method involves a disk read operation.
    */
   public void loadData() {
      spreadList = readDataFromMemory();
   }
   
   /* Not finished*/
   private ObservableList<Spread> readDataFromMemory() {
      ObservableList<Spread> list = FXCollections.observableArrayList();
      return list;
   }
   
   public Database getDatabase() {
      if (database == null)
         database = new Database();
      return database;
   }
   
   /**
    * Returns the list of Spread objects maintained by the application. 
    * It is possible for this list to be empty, but never {@code null}.
    * 
    * @return A list of Spread objects.
    */
   public ObservableList<Spread> getSpreadList() {
      if (spreadList == null)
         spreadList = FXCollections.observableArrayList();
      return spreadList;
   }
   
   /**
    * Indicates the user's requested initial spread to be shown on the
    * home page. This method is primarily used by the MainPage class.
    * 
    * @return The index value for the desired initial spread.
    */
   public int initialHomePageSpread() {
      return initialSpread;
   }
}
