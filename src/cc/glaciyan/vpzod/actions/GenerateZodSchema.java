package cc.glaciyan.vpzod.actions;

import cc.glaciyan.vpzod.ClassModel;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class GenerateZodSchema implements VPActionController {
  @Override
  public void performAction(VPAction vpAction) {
    try {
      ProjectManager projectManager = ApplicationManager.instance().getProjectManager();
      IModelElement[] models = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_CLASS);

      Collection<ClassModel> classes = new ArrayList<>();

      for (IModelElement model : models) {
        IClass vbClass = (IClass)model;
        ClassModel classModel = ClassModel.valueOf(vbClass);
        classes.add(classModel);
      }
    }
    finally {
      // clean up
      ClassModel.Classes = new HashMap<>();
    }
  }

  @Override
  public void update(VPAction vpAction) {

  }
}
