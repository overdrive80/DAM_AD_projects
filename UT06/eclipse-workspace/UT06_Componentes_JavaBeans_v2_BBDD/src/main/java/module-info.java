/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/module-info.java to edit this template
 */

module clasesbean {
    requires neodatis.odb;
    requires transitive java.desktop;
    requires java.base;
    requires java.sql;

    exports clasesbean;
    opens clasesbean;
    
    uses java.beans.PropertyChangeEvent;
    uses java.beans.PropertyChangeSupport;
    uses java.beans.PropertyChangeListener;

}
