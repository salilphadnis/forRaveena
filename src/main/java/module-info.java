module dr.sparky.office.drsparkysoffice {
    requires javafx.controls;
    requires javafx.fxml;

    opens dr.sparky.office.drsparkysoffice;
    opens dr.sparky.office.drsparkysoffice.controller;
    opens dr.sparky.office.drsparkysoffice.model;
    opens dr.sparky.office.drsparkysoffice.view;
    opens dr.sparky.office.drsparkysoffice.util;
    opens dr.sparky.office.drsparkysoffice.images;
    opens dr.sparky.office.drsparkysoffice.data;
    opens dr.sparky.office.drsparkysoffice.test;

    exports dr.sparky.office.drsparkysoffice;
    exports dr.sparky.office.drsparkysoffice.controller;
    exports dr.sparky.office.drsparkysoffice.model;
    exports dr.sparky.office.drsparkysoffice.view;
    exports dr.sparky.office.drsparkysoffice.util;
    exports dr.sparky.office.drsparkysoffice.data;
    exports dr.sparky.office.drsparkysoffice.test;

}