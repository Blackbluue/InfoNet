/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals;


import com.miller.infonet.data.listing.Listings;

import java.util.stream.Collectors;

import javafx.event.EventHandler;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author mille
 */
class NavigationBar extends FlowPane {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private final MenuBar addMenuBar;
   private final Menu listingMenu;
   private final SearchBar searchBar;

   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   NavigationBar() {
      searchBar = new SearchBar();
      listingMenu = new Menu("Add New Listing");
      
      listingMenu.getItems().
              addAll(Listings.listingTypeNames().
                      stream().
                      map(MenuItem::new).
                      collect(Collectors.toList()));
      addMenuBar = new MenuBar(listingMenu);
      getChildren().addAll(searchBar, addMenuBar);
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   void setOnAddRequest(EventHandler handler) {
      listingMenu.getItems().
              stream().
              forEach(item -> item.setOnAction(handler));
   }
}
