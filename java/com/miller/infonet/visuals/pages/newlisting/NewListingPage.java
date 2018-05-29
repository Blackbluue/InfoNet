/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals.pages.newlisting;

import com.miller.infonet.visuals.Page;

import com.miller.infonet.data.listing.Listing;
import com.miller.infonet.data.listing.Listings;
import com.miller.infonet.data.listing.Listing.ListingBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mille
 * @param <E>
 */
public abstract class NewListingPage<E extends Listing> extends Page {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final Map<String, NewListingPage> newListingPages = 
           new HashMap<>();
   
   private E content;
   private final ListingBuilder<E> builder;
   private Runnable onBuilt;
   
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
   public static NewListingPage makePage(String listingType) {
      return newListingPages.get(listingType);
   }
   
   /**
    * Sole constructor, to be called by subtypes.
    * 
    * @param listingType The name of the Listing type this page is used
    *                    with.
    */
   protected NewListingPage(String listingType) {
      super("Create a new enrty: " + listingType);
      builder = Listings.getBuilder(listingType);
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Retrieves the currently stored content. If no content has been 
    * built, then {@code null} will be returned.
    * @return 
    */
   public final E getContent() {
      return content;
   }
   
   /**
    * Retrieves the currently stored Listing object, or {@code null} if 
    * no object has been built yet. Calling this method will set the 
    * stored content to {@code null}, requiring it be be built again 
    * before this method should be called again. It will also clear the
    * page.
    * <p/>
    * Convenience method that combines the 
    * {@link #getContent() getContent()} and the {@link #clear() clear()}
    * operations.
    * 
    * @return The currently stored Listing object.
    */
   public final E retrieveContent() {
      E retContent = content;
      content = null;
      clear();
      return retContent;
   }
   
   /**
    * Builds and stores the Listing content. Should generally only be 
    * called by events initiated by the end user.
    * <p/>
    * Although the builder is supplied to subtypes through the
    * {@link #getBuilder() getBuilder()} method, subtypes must call this
    * method instead of the builder's {@code build()} method directly to 
    * ensure the constructed Listing object is stored properly so that it 
    * can be retrieved by other environments.
    */
   protected final void buildContent() {
      content = builder.build();
      if (onBuilt != null)
         onBuilt.run();
   }
   
   /**
    * Clears the user input on the page and discards any currently built
    * content. Typically called when the application switches away from
    * the page, so that it may be reused again later in a fresh state
    * without the need to generate a new page.
    */
   public abstract void clear();
   
   /**
    * Defines a handler to be used when the content has been built
    * successfully.
    * 
    * @param handler The handler.
    */
   public final void setOnBuilt(Runnable handler) {
      onBuilt = handler;
   }
   
   protected final ListingBuilder<E> getBuilder() {
      return builder;
   }
   
   static void registerNewListingPage(String listingType, NewListingPage page) {
      newListingPages.putIfAbsent(listingType, page);
   }
}
