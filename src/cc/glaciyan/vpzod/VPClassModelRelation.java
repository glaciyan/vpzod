package cc.glaciyan.vpzod;

import cc.glaciyan.uml.ClassModelRelation;
import cc.glaciyan.uml.RelationshipDirection;
import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IRelationship;
import com.vp.plugin.model.IRelationshipEnd;

public class VPClassModelRelation {
  private VPClassModelRelation() { }

  public static ClassModelRelation valueOf(VPClassModel target, IRelationship relationship, RelationshipDirection direction) {
    if (relationship instanceof IAssociation) {
      IRelationshipEnd relationshipEnd = ((IAssociation)relationship).getToEnd();
      return new ClassModelRelation(target, direction, relationship.getModelType(), relationshipEnd.getName(),
                                    MultiplicityUtils.getMultiplicity(((IAssociationEnd)relationshipEnd).getMultiplicity()),
                                    VisibilityUtils.getVisibility(((IAssociationEnd)relationshipEnd).getVisibility()));
    } else {
      return new ClassModelRelation(target, direction, relationship.getModelType(), null, null, null);
    }
  }
}
