package cc.glaciyan.uml;

import java.util.*;

public class ClassModel {
  public static Map<String, ClassModel> Classes = new HashMap<>();
  protected final String name;
  protected final Collection<Attribute> attributes = new ArrayList<>();
  protected ClassModel extending;
  protected final Collection<ClassModelRelation> using = new ArrayList<>();

  public ClassModel(String name) { this.name = name; }

  public static Collection<ClassModel> getSortedClasses() {
    Collection<ClassModel> sortedClasses = new Vector<>();
    Classes.forEach((name, classModel) -> {
      ClassModel.addToSorted(sortedClasses, classModel);
    });
    return sortedClasses;
  }

  private static void addToSorted(Collection<ClassModel> sortedClasses, ClassModel classModel) {
    // skip elements already added through using
    if (sortedClasses.contains(classModel)) return;

    classModel.using.forEach(usingRelation -> {
      if (!sortedClasses.contains(usingRelation.target)) {
        ClassModel.addToSorted(sortedClasses, usingRelation.target);
      }
    });

    sortedClasses.add(classModel);
  }

  public String getName() {
    return name;
  }

  public ClassModel getExtending() {
    return extending;
  }

  public Collection<Attribute> getAttributes() {
    return attributes;
  }

  public Collection<ClassModelRelation> getUsing() {
    return using;
  }
}
