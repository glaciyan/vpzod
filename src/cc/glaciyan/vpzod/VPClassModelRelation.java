package cc.glaciyan.vpzod;

import cc.glaciyan.uml.ClassModelRelation;
import cc.glaciyan.uml.RelationshipDirection;
import com.vp.plugin.model.IAssociation;
import com.vp.plugin.model.IAssociationEnd;
import com.vp.plugin.model.IRelationship;

public class VPClassModelRelation extends ClassModelRelation {

  public VPClassModelRelation(VPClassModel target, IRelationship relationship, RelationshipDirection direction) {
    super(target, direction, relationship.getModelType(), ((IAssociation)relationship).getToEnd().getName(),
          MultiplicityUtils.getMultiplicity(((IAssociationEnd)((IAssociation)relationship).getToEnd()).getMultiplicity()),
          VisibilityUtils.getVisibility(((IAssociationEnd)((IAssociation)relationship).getToEnd()).getVisibility()));
  }
}
