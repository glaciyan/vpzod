package cc.glaciyan.vpzod;

import cc.glaciyan.uml.Multiplicity;
import com.vp.plugin.model.IAttribute;

public class MultiplicityUtils {
  private MultiplicityUtils() {
    throw new AssertionError("No MultiplicityUtils for you!");
  }

  public static Multiplicity getMultiplicity(String multiplicity) {

    switch (multiplicity) {
      case IAttribute.MULTIPLICITY_ZERO:
        return Multiplicity.None;
      case IAttribute.MULTIPLICITY_ZERO_TO_ONE:
        return Multiplicity.NoneToOne;
      case IAttribute.MULTIPLICITY_ZERO_TO_MANY:
      case IAttribute.MULTIPLICITY_MANY:
        return Multiplicity.Many;
      case IAttribute.MULTIPLICITY_ONE:
        return Multiplicity.One;
      case IAttribute.MULTIPLICITY_ONE_TO_MANY:
        return Multiplicity.OneToMany;
      default:
        return Multiplicity.Unspecified;
    }
  }
}
