package cc.glaciyan.vpzod;

import cc.glaciyan.uml.*;
import com.vp.plugin.model.*;

import java.util.Collection;
import java.util.Iterator;

import static cc.glaciyan.uml.RelationshipDirection.From;
import static cc.glaciyan.uml.RelationshipDirection.To;

public class VPClassModel extends ClassModel {

  //private final Collection<VPClassModelRelation> usages = new ArrayList<>();

  @SuppressWarnings("rawtypes")
  private VPClassModel(IClass vbClass) {
    super(vbClass.getName());
    if (Classes.containsKey(this.name)) throw new IllegalArgumentException(String.format("%s is already constructed", this.name));
    Classes.put(this.name, this);

    ////region Handle all the relations
    //Iterator simpleFrom = vbClass.fromRelationshipIterator();
    //buildRelations(vbClass, simpleFrom, usages, To);

    Iterator simpleTo = vbClass.toRelationshipIterator();
    buildRelations(vbClass, simpleTo, using, From);

    Iterator fromEnds = vbClass.fromRelationshipEndIterator();
    buildRelations(vbClass, fromEnds, using, To);

    //Iterator toEnds = vbClass.toRelationshipEndIterator();
    //buildRelations(vbClass, toEnds, usages, From);
    //endregion

    //region Set the superclass if present
    using.stream()
      .filter(classModelRelation -> classModelRelation.type
        .equals("Generalization"))
      .findFirst()
      .ifPresent(superClass -> this.extending = superClass.target);
    //endregion

    Iterator attributeIterator = vbClass.attributeIterator();
    while (attributeIterator.hasNext()) {
      IAttribute attribute = (IAttribute)attributeIterator.next();

      String name = attribute.getName();
      Multiplicity multiplicity = MultiplicityUtils.getMultiplicity(attribute.getMultiplicity());
      Visibility visibility = VisibilityUtils.getVisibility(attribute.getVisibility());
      String type = attribute.getTypeAsString();
      String typeModifier = attribute.getTypeModifier();
      // TODO description as doc comment

      attributes.add(new Attribute(name, multiplicity, visibility, type, typeModifier));
    }

    using.stream().filter(ClassModelRelation::canBeAttribute).forEach(modelRelation -> {
      attributes.add(modelRelation.endAttribute());
    });
  }


  public static VPClassModel process(IClass vbClass) {
    VPClassModel cached = (VPClassModel)Classes.get(vbClass.getName());
    if (cached != null) return cached;

    return new VPClassModel(vbClass);
  }

  @SuppressWarnings("rawtypes")
  private static void buildRelations(IClass root,
                                     Iterator iterator,
                                     Collection<ClassModelRelation> classModelRelations,
                                     RelationshipDirection direction
  ) {
    while (iterator.hasNext()) {
      Object next = iterator.next();

      IRelationship relation;
      boolean isEnd = false;
      if (next instanceof IRelationshipEnd) {
        IRelationshipEnd lRelationshipEnd = (IRelationshipEnd)next;
        relation = lRelationshipEnd.getEndRelationship();
        isEnd = true;
      }
      else {
        relation = (IRelationship)next;
      }

      IModelElement relatedElement = direction == From ?
                                     relation.getFrom() : relation.getTo();

      if (relatedElement instanceof IClass) {
        IClass relatedClass = (IClass)relatedElement;
        if (root.equals(relatedClass)) {
          return;
        }

        if (Classes.containsKey(relatedClass.getName())) {
          classModelRelations.add(new VPClassModelRelation(
            (VPClassModel)Classes.get(relatedClass.getName()), relation,
            direction));
        }
        else {
          classModelRelations.add(
            new VPClassModelRelation(process(relatedClass),
                                     relation, direction));
        }
      }
    }
  }
}
