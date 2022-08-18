package cc.glaciyan.vpzod;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.ProjectManager;
import com.vp.plugin.model.IClass;
import com.vp.plugin.model.IModelElement;
import com.vp.plugin.model.factory.IModelElementFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ZodGenerator {
  private ZodGenerator() {}

  public static void generateZodSchema(File output) throws Exception {
    try {
      ApplicationManager applicationManager = ApplicationManager.instance();
      ProjectManager projectManager = applicationManager.getProjectManager();

      IModelElement[] models = projectManager.getProject().toModelElementArray(IModelElementFactory.MODEL_TYPE_CLASS);

      for (IModelElement model : models) {
        IClass vbClass = (IClass)model;
        ClassModel.process(vbClass);
      }

      Collection<ClassModel> sortedClasses = new Vector<>();
      ClassModel.Classes.forEach((name, classModel) -> {
        addToSorted(sortedClasses, classModel);
      });

      writeTemplate(applicationManager, sortedClasses, output);
    }
    finally {
      // clean up
      ClassModel.Classes = new HashMap<>();
    }
  }

  private static void addToSorted(Collection<ClassModel> sortedClasses, ClassModel classModel) {
    // skip elements already added through using
    if (sortedClasses.contains(classModel)) return;

    classModel.using.forEach(usingRelation -> {
      if (!sortedClasses.contains(usingRelation.model)) {
        addToSorted(sortedClasses, usingRelation.model);
      }
    });

    sortedClasses.add(classModel);
  }

  private static void writeTemplate(ApplicationManager applicationManager, Collection<ClassModel> sortedClasses, File file)
    throws Exception {
    //try {
      String pluginPath = applicationManager.getPluginInfo("cc.glaciyan.vpzod").getPluginDir().getAbsolutePath();

      Properties velocityProperties = new Properties();
      velocityProperties.setProperty("runtime.log", pluginPath + "/velocity.log");

      Velocity.init(velocityProperties);

      VelocityContext context = new VelocityContext();

      context.put("classes", sortedClasses);

      FileWriter writer = new FileWriter(file);
      FileReader reader = new FileReader(pluginPath + "/src/cc/glaciyan/vpzod/templates/zod.vm");

      Velocity.evaluate(context, writer, "zod", reader);
      reader.close();
      writer.close();
    //}
    //catch (ResourceNotFoundException rnfe) {
    //  // couldn't find the template
    //  System.err.println("couldn't find the template: " + rnfe.getMessage());
    //}
    //catch (ParseErrorException pe) {
    //  // syntax error: problem parsing the template
    //  System.err.println("syntax error: problem parsing the template: " + pe.getMessage());
    //}
    //catch (MethodInvocationException mie) {
    //  // something invoked in the template
    //  // threw an exception
    //  System.err.println("Method invocation exception: " + mie.getMessage());
    //}
    //catch (Exception e) {
    //  System.err.println("Unknown error: " + e.getMessage() + "\n" + Arrays.toString(e.getStackTrace()));
    //}
  }
}
