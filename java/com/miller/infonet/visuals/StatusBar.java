/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals;

import com.miller.infonet.base.Options;
import javafx.scene.control.Label;

/**
 *
 * @author mille
 */
class StatusBar extends Label {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   StatusBar() {
      setText(String.valueOf(Options.getOptions().getSpreadList().size()));
   }
   
}
