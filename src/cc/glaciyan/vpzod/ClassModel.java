package cc.glaciyan.vpzod;

import com.vp.plugin.model.*;

import java.util.*;

import static cc.glaciyan.vpzod.RelationshipDirection.From;
import static cc.glaciyan.vpzod.RelationshipDirection.To;

public class ClassModel {
  public static Map<String, ClassModel> Classes = new HashMap<>();

  public String name;
  public ClassModel extending;
  public Collection<ClassModelRelation> usages = new ArrayList<>();
  public Collection<ClassModelRelation> using = new ArrayList<>();
  public Collection<Attribute> attributes = new ArrayList<>();

  @SuppressWarnings("rawtypes")
  private ClassModel(IClass vbClass) {
    this.name = vbClass.getName();
    Classes.put(this.name, this);

    //region Handle all the relations
    Iterator simpleFrom = vbClass.fromRelationshipIterator();
    buildRelations(vbClass, simpleFrom, usages, To);

    Iterator simpleTo = vbClass.toRelationshipIterator();
    buildRelations(vbClass, simpleTo, using, From);

    // Flipped "using" and "usages" because of how End relations are
    // handled (I think, it just works)
    Iterator fromEnds = vbClass.fromRelationshipEndIterator();
    buildRelations(vbClass, fromEnds, using, To);

    Iterator toEnds = vbClass.toRelationshipEndIterator();
    buildRelations(vbClass, toEnds, usages, From);
    //endregion

    //region Set the superclass if present
    using.stream()
      .filter(classModelRelation -> classModelRelation.relationship.getModelType()
        .equals("Generalization"))
      .findFirst()
      .ifPresent(superClass -> this.extending = superClass.model);
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

    using.stream().filter(modelRelation -> modelRelation.isEnd).forEach(modelRelation -> {
      if (modelRelation.relationship instanceof IAssociation) {
        IAssociationEnd relation = (IAssociationEnd)((IAssociation)modelRelation.relationship).getToEnd();

        attributes.add(
          new Attribute(relation.getName(), MultiplicityUtils.getMultiplicity(relation.getMultiplicity()), VisibilityUtils.getVisibility(relation.getVisibility()), modelRelation.model));
      }
    });
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
          classModelRelations.add(new ClassModelRelation(
            Classes.get(relatedClass.getName()), relation,
            direction, isEnd));
        }
        else {
          classModelRelations.add(
            new ClassModelRelation(valueOf(relatedClass),
                                   relation, direction, isEnd));
        }
      }
    }
  }

  public static ClassModel valueOf(IClass vbClass) {
    return new ClassModel(vbClass);
  }
}
