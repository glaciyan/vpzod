package cc.glaciyan.uml;


public class ClassModelRelation {
  public ClassModel target;
  public RelationshipDirection direction;
  public String type;

  private Attribute _attribute;

  public ClassModelRelation(ClassModel target,
                            RelationshipDirection direction,
                            String type,
                            String toName,
                            Multiplicity toMultiplicity,
                            Visibility toVisibility) {
    this.target = target;
    this.direction = direction;
    this.type = type;

    if (this.direction == RelationshipDirection.To) {
      _attribute = new Attribute(toName, toMultiplicity, toVisibility, target);
    }
  }

  public ClassModel getModel() {
    return target;
  }

  public RelationshipDirection getDirection() {
    return direction;
  }

  public boolean canBeAttribute() {
    return direction == RelationshipDirection.To;
  }

  public Attribute endAttribute() {
    if (direction == RelationshipDirection.From) throw new IllegalStateException("Relation has to be in TO direction to be an attribute");
    return _attribute;
  }
}
