package cc.glaciyan.vpzod;

import com.vp.plugin.model.IRelationship;

public class ClassModelRelation {
  public ClassModel model;
  public IRelationship relationship;
  public RelationshipDirection direction;
  public boolean isEnd;

  public ClassModelRelation(ClassModel model, IRelationship relationship, RelationshipDirection direction, boolean isEnd) {
    this.model = model;
    this.relationship = relationship;
    this.direction = direction;
    this.isEnd = isEnd;
  }
}
