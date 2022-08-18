package cc.glaciyan.vpzod;

public class Attribute {
  public String name;
  public Multiplicity multiplicity;
  public Visibility visibility;
  public String type;
  public ClassModel complexType;
  public String typeModifier;

  public Attribute(String name, Multiplicity multiplicity, Visibility visibility, String type, String typeModifier) {
    this.name = name;
    this.multiplicity = multiplicity;
    this.visibility = visibility;
    this.type = type;
    this.typeModifier = typeModifier;
  }

  public Attribute(String name, Multiplicity multiplicity, Visibility visibility, ClassModel complexType) {
    this.name = name;
    this.multiplicity = multiplicity;
    this.visibility = visibility;
    this.type = complexType.name;
    this.complexType = complexType;
  }
}
