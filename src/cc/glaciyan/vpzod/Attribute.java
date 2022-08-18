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

  public String getName() {
    return name;
  }

  public Multiplicity getMultiplicity() {
    return multiplicity;
  }

  public Visibility getVisibility() {
    return visibility;
  }

  public String getType() {
    switch (type) {
      case "url":
        return "string().url()";
      case "email":
        return "string().email()";
      case "string":
      case "String":
        return "string()";
      case "date":
        return "date()";
      case "number":
      case "int":
      case "integer":
      case "double":
      case "float":
        return "number()";
      case "long":
        return "bigint()";
      case "boolean":
        return "boolean()";
      default:
        return type;
    }
  }

  public ClassModel getComplexType() {
    return complexType;
  }

  public String getTypeModifier() {
    return typeModifier;
  }
}
