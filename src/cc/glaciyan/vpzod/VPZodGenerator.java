package cc.glaciyan.vpzod;

import cc.glaciyan.uml.ClassModel;
import cc.glaciyan.uml.IZodGenerator;
import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Properties;

public final class VPZodGenerator implements IZodGenerator {
  private final File output;

  public VPZodGenerator(File output) {
    this.output = output;
  }

  public void generateZodSchema() throws Exception {
    try {
      ApplicationManager applicationManager = ApplicationManager.instance();
      ProjectManager projectManager = applicationManager.getProjectManager();

      IModelElement[] models = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_CLASS);

      for (IModelElement model : models) {
        IClass vbClass = (IClass)model;
        VPClassModel.process(vbClass);
      }

      Collection<ClassModel> sortedClasses = ClassModel.getSortedClasses();

      writeTemplate(applicationManager, sortedClasses, this.output);
    }
    finally {
      // clean up
      ClassModel.Classes = new HashMap<>();
    }
  }

  private void writeTemplate(ApplicationManager applicationManager, Collection<ClassModel> sortedClasses, File file)
    throws Exception {
    //try {
    String pluginPath = applicationManager.getPluginInfo("cc.glaciyan.vpzod").getPluginDir().getAbsolutePath();

    Properties velocityProperties = new Properties();
    velocityProperties.setProperty("runtime.log", pluginPath + "/velocity.log");

    Velocity.init(velocityProperties);

    VelocityContext context = new VelocityContext();

    context.put("classes", sortedClasses);

    FileWriter writer = new FileWriter(file);
    FileReader reader = new FileReader(pluginPath + "/src/cc/glaciyan/templates/zod.vm");

    Velocity.evaluate(context, writer, "zod", reader);
    reader.close();
    writer.close();
  }
}
