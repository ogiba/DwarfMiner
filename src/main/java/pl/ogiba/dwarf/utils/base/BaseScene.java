/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.ogiba.dwarf.utils.base;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author robertogiba
 */
public abstract class BaseScene {
    protected void runOnUiThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
