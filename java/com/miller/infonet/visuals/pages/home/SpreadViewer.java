/*
 * Project Name: 
 * Author: Raymone Miller
 * Date: 
 * Version #: 0.0.0
 */
package com.miller.infonet.visuals.pages.home;

import com.miller.infonet.base.Options;

import com.miller.infonet.data.listing.Listing;
import com.miller.infonet.data.listing.Listings;
import com.miller.infonet.data.container.Spread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.ReadOnlyObjectProperty;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author mille
 */
public class SpreadViewer extends BorderPane {
   /*+---------------------------------------------------------------+
     +                                                               +
     +                            Fields                             +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static final Options opt = Options.getOptions();
   private final ViewerHeaderPane headerPane;
   private final SortedList<Spread> sortedSpreadList;
   private SortedList sortedData;
   private final TableView<Spread> spreadTable;
   private final TableView<Listing> dataTable;
   private final InvalidationListener spreadChangeInvalidationListener = 
           selectedItem -> {
              Spread spread = ((ReadOnlyObjectProperty<Spread>) selectedItem).get();
              updateDataTable(spread.LISTING_TYPE, spread.getName(), spread);
           };
   
   private static final List<TableColumn<Spread, ?>> SPREAD_TABLE_COLUMNS_LIST;
   public static final int NO_INITIAL = -1;
   public static final int DATABASE_INITIAL = -2;
   
   static {
      SPREAD_TABLE_COLUMNS_LIST = new ArrayList<>();
      
      TableColumn<Spread, String> nameCol = new TableColumn<>("Name");
      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
      
      TableColumn<Spread, String> sizeCol = new TableColumn<>("Number of Entries");
      sizeCol.setCellValueFactory(new PropertyValueFactory<>("size"));
      
      SPREAD_TABLE_COLUMNS_LIST.addAll(Arrays.asList(nameCol, sizeCol));
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                  Constructors/Builders                        +
     +                                                               +
     +---------------------------------------------------------------+*/
   SpreadViewer() {
      Options opt = Options.getOptions();
      int initialSpreadIndex = opt.initialHomePageSpread();
      
      sortedSpreadList = new SortedList<>(opt.getSpreadList());
      spreadTable = new TableView<>(sortedSpreadList);
      dataTable = new TableView<>();
      headerPane = new ViewerHeaderPane();
      
      sortedSpreadList.comparatorProperty().bind(spreadTable.comparatorProperty());
      
      spreadTable.getColumns().setAll(SPREAD_TABLE_COLUMNS_LIST);
      spreadTable.getSelectionModel().selectedItemProperty().addListener(
              new WeakInvalidationListener(spreadChangeInvalidationListener));
      
      if (initialSpreadIndex != NO_INITIAL) {
         Spread spread = sortedSpreadList.get(initialSpreadIndex);
         updateDataTable(spread.LISTING_TYPE, spread.getName(), spread);
      }
      
      setCenter(dataTable);
      setTop(headerPane);
      setLeft(spreadTable);
   }
      
   /*+---------------------------------------------------------------+
     +                                                               +
     +                         Methods                               +
     +                                                               +
     +---------------------------------------------------------------+*/
   private static <E extends Listing> List<TableColumn<E, ?>> getDataTableColumnsList(String listingType) {
      List<String> propertyNames = Listings.propertyNames(listingType);
      
      List<TableColumn<E, ?>> colList = propertyNames.stream().
              map(pName -> {
                 TableColumn<E, ?> col = new TableColumn(capitalize(pName));
                 
                 col.setCellValueFactory(new PropertyValueFactory<>(pName));
                 return col;
              }).
              collect(Collectors.toList());
      
      return colList;
   }
   
   private void updateDataTable(String listingType, String listName, ObservableList dataList) {
      if (sortedData != null)
         sortedData.comparatorProperty().unbind();
      sortedData = new SortedList(dataList);
      
      dataTable.setItems(sortedData);
      dataTable.getColumns().setAll(getDataTableColumnsList(listingType));
      sortedData.comparatorProperty().bind(dataTable.comparatorProperty());
      headerPane.setName(listName);
   }
   
   private static String capitalize(String string) {
      StringBuilder sBuilder = new StringBuilder(string);
      sBuilder.setCharAt(0, Character.toUpperCase(string.charAt(0)));
      return sBuilder.toString();
   }
   
   /*+---------------------------------------------------------------+
     +                                                               +
     +                        Inner Classes                          +
     +                                                               +
     +---------------------------------------------------------------+*/
   private class ViewerHeaderPane extends FlowPane {
      private final Label dataListNameLabel;
      private final Set<Button> listingTypeButtons;

      public ViewerHeaderPane() {
         dataListNameLabel = new Label();
         listingTypeButtons = new HashSet<>();
         List<String> types = Listings.listingTypeNames();
         types.forEach(type -> {
            Button button = new Button(type);
            button.setOnAction(e -> {
               ObservableList<? extends Listing> dataList = opt.getDatabase().getByType(
                       type);
               SpreadViewer.this.updateDataTable(type, type, dataList);
            listingTypeButtons.add(button);
            });
            listingTypeButtons.add(button);
         });
      }
      
      void setName(String dataListName) {
         dataListNameLabel.setText(dataListName);
      }
   }
}