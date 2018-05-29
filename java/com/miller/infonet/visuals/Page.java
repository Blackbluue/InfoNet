/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.BorderPane;

/**
 * Base class for all pages. A NavigationBar is set in the top region and
 * a StatusBar is set in the bottom region; these areas should not be
 * modified directly by subclasses.
 * @author mille
 */
public abstract class Page extends BorderPane {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private String pageName;
   private final NavigationBar navBar;
   private final StatusBar statusBar;
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   protected Page(String pageName) {
      setPageName(pageName);
      navBar = new NavigationBar();
      statusBar = new StatusBar();
      
      setTop(navBar);
      setBottom(statusBar);
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Retrieves this Page's name. Generally a Page's name is used as the
    * title of a stage or as a header.
    * 
    * @return This Page's name.
    */
   public String getPageName() {
      return pageName;
   }
   
   /**
    * Sets the page name. {@code null} values will be treated as empty 
    * strings.
    * 
    * @param newName The new page name.
    */
   protected final void setPageName(String newName) {
      pageName = (newName == null) ?
              "" :
              newName;
   }
   
   /**
    * Sets an event handler to handle when the user requests to create a 
    * new Listing, through the drop down menu in the navigation bar. Note
    * that this handler will be applied to each MenuItem.
    * 
    * @param handler The event handler for adding new Listings.
    */
   public void setOnNewListingRequest(EventHandler<ActionEvent> handler) {
      navBar.setOnAddRequest(handler);
   }
}
