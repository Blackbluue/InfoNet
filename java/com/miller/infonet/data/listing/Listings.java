/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.data.listing;

import com.miller.infonet.data.listing.Listing.ListingBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author mille
 */
public final class Listings {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * A mapping of Listing type names to maps of their respective 
    * property names and default values.
    */
   private static final Map<String, Map<String, Object>> listingTypeProperties = new HashMap<>();
   private static final ListingBuilderFactory builderFactory = new ListingBuilderFactory();
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Sole constructor.
    */
   private Listings() { }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   /**
    * Registers a new Listing type for use in this application. The method
    * <strong>MUST</strong> be called by each Listing type in a static
    * context <strong>BEFORE</strong> any Listing of that type be created.
    * Otherwise, an exception will be thrown at creation time. This method
    * must be called only once for each Listing type.
    * 
    * @param listingType     The name of the Listing type.
    * @param builderSupplier A supplier to generate a builder for this 
    *                        type.
    * @param propertyNames   List of property names for this type.
    * 
    * @see #setDefaultPropertyValue(java.lang.String, java.lang.String, java.lang.Object) 
    *       setDefaultPropertyValue(String, String, Object)
    */
   static void registerListingType(String listingType, 
           Supplier<ListingBuilder<? extends Listing>> builderSupplier, 
           String... propertyNames) {
      if (listingType == null || builderSupplier == null || propertyNames == null)
         throw new NullPointerException();
      if (listingTypeProperties.containsKey(listingType))
         throw new IllegalArgumentException("Listing type " + 
                 listingType + " already registered.");
      
      Map<String,Object> properties = Stream.of(propertyNames).collect(
              Collectors.toMap(Function.identity(), p -> null));
      listingTypeProperties.put(listingType, properties);
      builderFactory.registerListingType(listingType, builderSupplier);
   }
   
   /**
    * Returns a list of property names for the given Listing type. If no 
    * such Listing type was found, {@code null} will be returned.
    * <p/>
    * The returned list does not write through to the underlying list.
    * 
    * @param listingType A class object that represents the desired 
    *                    Listing type.
    * 
    * @return A list of the desired Listing type's property names.
    */
   public static List<String> propertyNames(String listingType) {
      List<String> propertyNames = 
              new ArrayList<>(listingTypeProperties.get(listingType).keySet());
      return propertyNames;
   }
   
   /**
    * Allows for a default value to be set for a property of a specific
    * Listing type. If a default value is not set, it is set to 
    * {@code null} by default. This method does not check type safety on
    * the property value.
    * 
    * @param listingType   The name of the Listing type.
    * @param propertyName  The property name.
    * @param propertyValue The default value of the property.
    */
   static void setDefaultPropertyValue(String listingType, 
           String propertyName, Object propertyValue) {
      if (listingType == null || propertyName == null)
         throw new NullPointerException();
      if (!listingTypeProperties.containsKey(listingType))
         throw new IllegalArgumentException("There is no Listing of " +
                 "type " + listingType);
      
      Map<String, Object> properties = listingTypeProperties.get(listingType);
      if (!properties.containsKey(propertyName))
         throw new IllegalArgumentException("There is no property of " +
                 "name " + propertyName);
      
      listingTypeProperties.get(listingType).put(propertyName,
              propertyValue);
   }
   
   /**
    * Returns a map of property names to default values for the requested
    * Listing type. The returned map is immutable.
    * 
    * @param listingType The name of the Listing type.
    * 
    * @return A map of property names to default values.
    */
   static Map<String, Object> defaultProperties(String listingType) {
      if (!listingTypeProperties.containsKey(listingType))
         throw new IllegalArgumentException("There is no Listing of " +
                 "type " + listingType);
      return Collections.unmodifiableMap(listingTypeProperties.get(
              listingType));
   }
   
   /**
    * Returns a list of the names of all Listing types registered by this 
    * application. The list is immutable.
    * 
    * @return A list of all Listing type names.
    */
   public static List<String> listingTypeNames() {
      List<String> list = Collections.
              unmodifiableList(listingTypeProperties.keySet().
                      stream().
                      collect(Collectors.toList()));
      return list;
   }
   
   /**
    * Returns a builder object used to build a Listing of the desired 
    * type. Returns {@code null} if there is no associated builder for 
    * that type, or if the type is not registered.
    * 
    * @param listingType The type of the Listing.
    * 
    * @return A builder for the desired type.
    */
   public static ListingBuilder getBuilder(String listingType) {
      return builderFactory.getBuilder(listingType);
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static class ListingBuilderFactory {
      private final Map<String, Supplier<ListingBuilder<? extends Listing>>> suppliers;

      ListingBuilderFactory() {
         this.suppliers = new HashMap<>();
      }

      void registerListingType(String listingType, Supplier<ListingBuilder<? extends Listing>> supplier) {
         suppliers.putIfAbsent(listingType, supplier);
      }
      
      ListingBuilder<? extends Listing> getBuilder(String listingType) {
         return suppliers.get(listingType).get();
      }
      
   }
}
