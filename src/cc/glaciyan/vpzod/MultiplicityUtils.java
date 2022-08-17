package cc.glaciyan.vpzod;

public class MultiplicityUtils {
  private MultiplicityUtils() {
    throw new AssertionError("No MultiplicityUtils for you!");
  }

  public static Multiplicity getMultiplicity(String multiplicity) {
    switch (multiplicity) {
      case "0":
        return Multiplicity.None;
      case "0..1":
        return Multiplicity.NoneOrOne;
      case "0..*":
      case "*":
        return Multiplicity.Many;
      case "1":
        return Multiplicity.One;
      case "1..*":
        return Multiplicity.OneOrMany;
      default:
        return Multiplicity.Unspecified;
    }
  }
}
