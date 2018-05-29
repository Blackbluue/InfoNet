/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals.pages.home;

import com.miller.infonet.visuals.Page;

/**
 *
 * @author mille
 */
public class MainPage extends Page {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private final SpreadViewer viewer;
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   public MainPage() {
      super("InfoNet - Home");
      viewer = new SpreadViewer();
      
      setCenter(viewer);
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
}
