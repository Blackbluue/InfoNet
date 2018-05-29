/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.base;

import com.miller.infonet.visuals.Page;
import com.miller.infonet.visuals.pages.home.MainPage;
import com.miller.infonet.visuals.pages.newlisting.NewListingPage;

import java.util.Map;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import javafx.scene.control.MenuItem;


/**
 *
 * @author mille
 */
public final class PageManager {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final Options options = Options.getOptions();
   private static PageManager manager;
   
   private MainPage mainPage;
   private Map<String, NewListingPage> newListingPages;
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                          Properties                           +
     +                                                               +
     +---------------------------------------------------------------+*/
   private ReadOnlyObjectWrapper<Page> currentPageProperty;
   /**
    * Private access to the current page property.
    * 
    * @return The current page property.
    */
   private ReadOnlyObjectWrapper<Page> privateCurrentPageProperty() {
      if (currentPageProperty == null)
         currentPageProperty = new ReadOnlyObjectWrapper<>(this, "current page",
                 getMainPage());
      return currentPageProperty;
   }
   /**
    * The intended current page for the application. This property's value
    * will change as the user navigates through the application. As the 
    * PageManager does not directly work with a Scene object, it is the 
    * application's responsibility to set the current value of this 
    * property into the primary stage's scene and show it. The page is 
    * fully configured with visuals, event handlers etc., so the 
    * application needs only set the page as a scene's root node.
    * 
    * @return The current page property.
    */
   public ReadOnlyObjectProperty<Page> currentPageProperty() {
      return privateCurrentPageProperty().getReadOnlyProperty();
   }
   /**
    * Builds a Parent node to be used by the application to set in a
    * scene of the primary stage. This class caches various 
    * nodes that are frequently used as root nodes and will arbitrarily 
    * return one of them, based on the state of the application. In other 
    * words, this method is not guaranteed to return the same object every
    * time.
    * 
    * @return A SubScene object to set in the primary stage.
    */
   public Page getCurrentPage() {
      return privateCurrentPageProperty().get();
   }
   /**
    * Sets the current page property. This method should generally only be
    * called by event handlers when the user performs an action. Also
    * sets the previous page property to the current page, before it was 
    * changed.
    * 
    * @param page The Parent object to set as the current page.
    */
   private void setCurrentPage(Page page) {
      Page oldPage = getCurrentPage();
      privateCurrentPageProperty().set(page);
      privatePreviousPageProperty().set(oldPage);
   }
   
   private ReadOnlyObjectWrapper<Page> previousPageProperty;
   private ReadOnlyObjectWrapper<Page> privatePreviousPageProperty() {
      if (previousPageProperty == null)
         previousPageProperty = new ReadOnlyObjectWrapper<>(this, "previous page",
                 getCurrentPage());
      return previousPageProperty;
   }
   public ReadOnlyObjectProperty<Page> previousPageProperty() {
      return privatePreviousPageProperty().getReadOnlyProperty();
   }
   public Page getPreviousPage() {
      return privatePreviousPageProperty().get();
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Retrieves the PageManager. Only one manager can be created, so 
    * repeated calls to this method will always return the same object.
    * 
    * @return The PageManager.
    */
   public static PageManager getManager() {
      if (manager == null)
         manager = new PageManager();
      return manager;
   }
   
   /**
    * Sole constructor.
    */
   private PageManager() { }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Retrieves the application's main page. This page is cached after it
    * is first initialized, so repeated calls to this method will always
    * yield the same object reference.
    * 
    * @return The application's main page.
    */
   private MainPage getMainPage() {
      if (mainPage != null) 
         return mainPage;
      
      mainPage = new MainPage();
      mainPage.setOnNewListingRequest(event -> {
         String listingType = ((MenuItem) event.getSource()).getText();
         NewListingPage page = getNewListingPage(listingType);
         setCurrentPage(page);
      });
      return mainPage;
   }
   
   private NewListingPage getNewListingPage(String listingType) {
      NewListingPage page = newListingPages.get(listingType);
      if (page != null)
         return page;
      
      NewListingPage newPage = NewListingPage.makePage(listingType);
      newPage.setOnBuilt(() -> {
         options.getDatabase().add(newPage.retrieveContent());
         PageManager.this.setCurrentPage(getPreviousPage());
      });
      newListingPages.put(listingType, newPage);
      return newPage;
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
}
