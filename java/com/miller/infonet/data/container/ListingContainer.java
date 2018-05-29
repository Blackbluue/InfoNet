/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.data.container;

import com.miller.infonet.data.listing.Listing;

import java.util.Collection;
import javafx.beans.Observable;

/**
 * Base interface for all Listing containers.
 * 
 * @author mille
 * @param <E> A type of Listing.
 */
public interface ListingContainer<E extends Listing> extends Collection<E>, Observable {
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
}
