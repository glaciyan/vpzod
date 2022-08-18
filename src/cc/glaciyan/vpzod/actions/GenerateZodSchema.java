package cc.glaciyan.vpzod.actions;

import cc.glaciyan.vpzod.Configurations;
import cc.glaciyan.vpzod.GenerateZodDialog;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.view.IDialog;
import com.vp.plugin.view.IDialogHandler;

import javax.swing.*;
import java.awt.*;

public class GenerateZodSchema implements VPActionController {
  private GenerateZodDialog myZodDialog;
  private Component _component;

  @Override
  public void performAction(VPAction vpAction) {
    ApplicationManager.instance().getViewManager().showDialog(new GenerateZodDialogHandle());
  }

  @Override
  public void update(VPAction vpAction) {

  }

  protected class GenerateZodDialogHandle implements IDialogHandler {
    @Override
    public Component getComponent() {
      String initialPath = Configurations.getInstance().getProjectConfigurations().getSchemaPath();
      myZodDialog = new GenerateZodDialog(initialPath == null ? "" : initialPath);
      _component = myZodDialog.getContentPanel();
      return _component;
    }

    @Override
    public void prepare(IDialog dialog) {
      dialog.setTitle("Generate Zod");
      dialog.setResizable(false);
      dialog.setSize(_component.getSize());
      myZodDialog.setDialog(dialog);
    }

    @Override
    public void shown() {
      myZodDialog.getOkButton().requestFocusInWindow();
    }

    @Override
    public boolean canClosed() {
      return true;
    }
  }
}
