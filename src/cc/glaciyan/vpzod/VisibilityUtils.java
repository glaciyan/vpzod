package cc.glaciyan.vpzod;

import cc.glaciyan.uml.Visibility;
import com.vp.plugin.model.IAttribute;

public class VisibilityUtils {
  private VisibilityUtils() {
    throw new AssertionError("No VisibilityUtils for you!");
  }

  public static Visibility getVisibility(String visibility) {
    switch (visibility) {
      case IAttribute.VISIBILITY_PRIVATE:
        return Visibility.Private;
      case IAttribute.VISIBILITY_PROTECTED:
        return Visibility.Protected;
      case IAttribute.VISIBILITY_PACKAGE:
        return Visibility.Package;
      case IAttribute.VISIBILITY_PUBLIC:
        return Visibility.Public;
      default:
        return Visibility.Unspecified;
    }
  }
}
